package com.example.ks.monitor.service;

import com.example.ks.computer.domain.Computer;
import com.example.ks.department.domain.Department;
import com.example.ks.department.repository.DepartmentRepository;
import com.example.ks.monitor.domain.Monitor;
import com.example.ks.monitor.dto.CreateMonitor;
import com.example.ks.monitor.dto.UpdateMonitor;
import com.example.ks.monitor.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MonitorService {

    private final MonitorRepository monitorRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<Monitor> findAll() {
        return monitorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Monitor findByMonitorId(int monitorId) {
        return monitorRepository.findById(monitorId)
                .orElseThrow(() -> new RuntimeException("모니터를 찾을 수 없습니다."));
    }

    public Monitor create(CreateMonitor createMonitor) {
        Department department = departmentRepository.findById(Integer.valueOf(createMonitor.departmentId()))
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));
        return monitorRepository.save(Monitor.toEntity(createMonitor, department));
    }

    public void update(UpdateMonitor updateMonitor, Integer monitorId) {
        Department department = departmentRepository.findById(Integer.valueOf(updateMonitor.departmentId()))
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));

        Monitor monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new RuntimeException("모니터를 찾을 수 없습니다."));

        monitor.update(updateMonitor, department);
        monitorRepository.save(monitor);
    }

    public void delete(int monitorId) {
        Monitor monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new RuntimeException("모니터를 찾을 수 없습니다."));
        monitor.delete("Y");
        monitorRepository.save(monitor);
    }

    @Transactional(readOnly = true)
    public List<Monitor> findByComputerId(int computerId) {
        return monitorRepository.findByComputer_ComputerId(computerId);
    }

    @Transactional(readOnly = true)
    public List<Monitor> findUnlinked() {
        return monitorRepository.findByComputerIsNull().stream()
                .filter(monitor -> "N".equals(monitor.getDel()))
                .toList();
    }

    public void linkToComputer(int monitorId, Computer computer) {
        Monitor monitor = findByMonitorId(monitorId);
        monitor.linkComputer(computer);
        monitorRepository.save(monitor);
    }

    public void unlinkFromComputer(int monitorId) {
        Monitor monitor = findByMonitorId(monitorId);
        monitor.unlinkComputer();
        monitorRepository.save(monitor);
    }

    // [6단계] 자동수집 결과 이 모니터가 더 이상 이 컴퓨터에 없다고 판단될 때 호출.
    // 실제 DELETE가 아니라 "폐기" 상태로 바꾸고 연결을 끊는 것 — 자산 이력은 그대로 남는다.
    public void disposeAndUnlink(int monitorId, String reason) {
        Monitor monitor = findByMonitorId(monitorId);
        monitor.dispose(reason);
        monitorRepository.save(monitor);
    }

    // [6단계] 자동수집으로 새로 감지된 모니터 1대를 완전히 새로운 자산으로 등록하고 해당 컴퓨터에 바로 연결.
    // 부서/설치장소 컬럼은 필수값이지만 스크립트에서는 알 수 없는 정보라,
    // 물리적으로 같은 자리에 있을 이 컴퓨터의 부서·설치장소를 그대로 가져다 쓴다.
    public Monitor registerAndLink(Computer computer, String manufacturer, String size, LocalDate manufactureDate) {
        Monitor monitor = Monitor.builder()
                .department(computer.getDepartment())
                .monitorPlace(computer.getComputerPlace())
                .monitorManufacturer(manufacturer)
                .monitorSaleDate(manufactureDate) // EDID 제조년월 근사치를 구매일 자리에 임시로 채움
                .monitorSize(size)
                .monitorText("정보 자동수집으로 등록됨") // 나중에 봤을 때 수동 등록과 구분되도록 표시
                .monitorDel("N")
                .del("N")
                .build();
        monitor.linkComputer(computer);
        return monitorRepository.save(monitor);
    }
}
