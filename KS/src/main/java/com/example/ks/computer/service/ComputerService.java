package com.example.ks.computer.service;

import com.example.ks.computer.domain.Computer;
import com.example.ks.computer.dto.CreateComputer;
import com.example.ks.computer.dto.ReportComputerInfo;
import com.example.ks.computer.dto.UpdateComputer;
import com.example.ks.computer.repository.ComputerRepository;
import com.example.ks.computerHistory.service.ComputerHistoryService;
import com.example.ks.department.domain.Department;
import com.example.ks.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ComputerService {

    private final ComputerRepository computerRepository;
    private final DepartmentRepository departmentRepository;
    private final ComputerHistoryService computerHistoryService;

    @Transactional(readOnly = true)
    public List<Computer> findAll() {
        return computerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Computer findByComputerId(int computerId) {
        return computerRepository.findById(computerId)
                .orElseThrow(() -> new RuntimeException("컴퓨터를 찾을 수 없습니다."));
    }

    public Computer create(CreateComputer createComputer) {
        Department department = departmentRepository.findById(Integer.valueOf(createComputer.departmentId()))
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));
        Computer computer = computerRepository.save(Computer.toEntity(createComputer, department));
        computerHistoryService.recordCreate(computer);
        return computer;
    }

    public void update(UpdateComputer updateComputer, Integer computerId) {
        Department department = departmentRepository.findById(Integer.valueOf(updateComputer.departmentId()))
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));

        Computer computer = computerRepository.findById(computerId)
                .orElseThrow(() -> new RuntimeException("컴퓨터를 찾을 수 없습니다."));

        String beforeDepartmentName = computer.getDepartment().getDepartmentName();
        LocalDate beforePlaceDate = computer.getComputerPlaceDate();

        computer.update(updateComputer, department);
        computerRepository.save(computer);

        computerHistoryService.recordChangeIfNeeded(computer, beforeDepartmentName, beforePlaceDate);
    }

    public void delete(int computerId) {
        Computer computer = computerRepository.findById(computerId)
                .orElseThrow(() -> new RuntimeException("컴퓨터를 찾을 수 없습니다."));
        computer.delete("Y");
        computerRepository.save(computer);
    }

    // [6단계] 컴퓨터 ID를 이미 알고 있는 경우(특정 ID용 스크립트) — 바로 그 행을 찾아서 반영
    public Computer applyReport(int computerId, ReportComputerInfo report) {
        Computer computer = findByComputerId(computerId);
        computer.applyReport(report); // 실제 필드 덮어쓰기 규칙은 Computer 엔티티에 있음
        computerRepository.save(computer);
        return computer;
    }

    // [6단계] IP만 갖고 있는 경우(범용 스크립트) — computer_ip 컬럼과 대조해서 컴퓨터를 먼저 특정한 뒤 반영
    public Computer applyReportByIp(ReportComputerInfo report) {
        if (report.ip() == null || report.ip().isBlank()) {
            throw new RuntimeException("이 PC의 IP를 확인할 수 없습니다.");
        }

        // computer_ip는 전체 IP("10.7.200.30")로 등록된 경우도 있고,
        // 대부분은 3번째+4번째 자리만 축약한 코드("50.50.2.4" -> "2004")로 등록돼 있음.
        // 그래서 실제 IP 그대로 + 축약 코드로 변환한 값, 둘 다 후보로 놓고 한 번에 검색한다.
        List<String> candidates = new ArrayList<>();
        candidates.add(report.ip());
        String shortCode = toShortIpCode(report.ip());
        if (shortCode != null) {
            candidates.add(shortCode);
        }

        List<Computer> matches = computerRepository.findByComputerIpIn(candidates).stream()
                .filter(c -> !"Y".equals(c.getDel()))
                .toList();

        // 정확히 1대가 나와야만 안전하게 반영 가능. 0대면 등록 누락, 2대 이상이면 중복 등록 문제라
        // 잘못된 컴퓨터에 덮어쓰지 않도록 여기서 막고 에러 메시지로 원인을 알려준다.
        if (matches.isEmpty()) {
            throw new RuntimeException("IP(" + report.ip() + ")로 등록된 컴퓨터를 찾을 수 없습니다. 컴퓨터 정보에 IP가 등록되어 있는지 확인해주세요.");
        }
        if (matches.size() > 1) {
            throw new RuntimeException("IP(" + report.ip() + ")로 등록된 컴퓨터가 여러 대입니다. 관리자에게 문의해주세요.");
        }

        Computer computer = matches.get(0);
        computer.applyReport(report);
        computerRepository.save(computer);
        return computer;
    }

    // "50.50.2.4" -> "2004" (3번째 자리 + 4번째 자리를 3자리 0채움)
    private String toShortIpCode(String fullIp) {
        String[] parts = fullIp.split("\\.");
        if (parts.length != 4) {
            return null;
        }
        try {
            int octet4 = Integer.parseInt(parts[3].trim());
            return parts[2].trim() + String.format("%03d", octet4);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
