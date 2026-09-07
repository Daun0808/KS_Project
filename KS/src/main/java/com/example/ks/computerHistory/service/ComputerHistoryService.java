package com.example.ks.computerHistory.service;

import com.example.ks.computer.domain.Computer;
import com.example.ks.computerHistory.domain.ComputerHistory;
import com.example.ks.computerHistory.dto.CreateComputerHistory;
import com.example.ks.computerHistory.dto.UpdateComputerHistory;
import com.example.ks.computerHistory.repository.ComputerHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ComputerHistoryService {

    private final ComputerHistoryRepository computerHistoryRepository;

    @Transactional(readOnly = true)
    public List<ComputerHistory> findByComputer_ComputerId(int computerId) {
        return computerHistoryRepository.findAllByComputer_ComputerIdOrderByPlaceDateDesc(computerId);
    }

    // 컴퓨터 최초 등록 시 이력 기록
    public void recordCreate(Computer computer) {
        CreateComputerHistory dto = CreateComputerHistory.builder()
                .computerId(computer.getComputerId())
                .departmentBeforeName(null)
                .departmentNewName(computer.getDepartment().getDepartmentName())
                .placeDate(LocalDate.now())
                .historyTag("등록")
                .historyText("자동이력생성")
                .del("N")
                .build();
        computerHistoryRepository.save(ComputerHistory.toEntity(dto, computer));
    }

    // 수정 시 부서 또는 설치일이 바뀐 경우에만 이력 기록
    public void recordChangeIfNeeded(Computer computer, String beforeDepartmentName, LocalDate beforePlaceDate) {
        String afterDepartmentName = computer.getDepartment().getDepartmentName();
        LocalDate afterPlaceDate = computer.getComputerPlaceDate();

        boolean departmentChanged = !Objects.equals(beforeDepartmentName, afterDepartmentName);
        boolean placeDateChanged = !Objects.equals(beforePlaceDate, afterPlaceDate);

        if (!departmentChanged && !placeDateChanged) {
            return;
        }

        String historyTag;
        if (departmentChanged && placeDateChanged) {
            historyTag = "부서/설치일 변경";
        } else if (departmentChanged) {
            historyTag = "부서 변경";
        } else {
            historyTag = "설치일 변경";
        }

        CreateComputerHistory dto = CreateComputerHistory.builder()
                .computerId(computer.getComputerId())
                .departmentBeforeName(beforeDepartmentName)
                .departmentNewName(afterDepartmentName)
                .placeDate(LocalDate.now())
                .historyTag(historyTag)
                .historyText("자동이력생성")
                .del("N")
                .build();
        computerHistoryRepository.save(ComputerHistory.toEntity(dto, computer));
    }

    @Transactional(readOnly = true)
    public ComputerHistory getComputerHistory(int computerHistoryId) {
        return computerHistoryRepository.findById(computerHistoryId)
                .orElseThrow(() -> new RuntimeException("컴퓨터 히스토리를 찾을 수 없습니다."));
    }

    public void updateComputerHistory(UpdateComputerHistory updateComputerHistory) {
        ComputerHistory computerHistory = computerHistoryRepository.findById(updateComputerHistory.computerHistoryId())
                .orElseThrow(() -> new RuntimeException("컴퓨터 히스토리를 찾을 수 없습니다."));
        computerHistory.update(updateComputerHistory);
        computerHistoryRepository.save(computerHistory);
    }

    public ComputerHistory deleteComputerHistory(int computerHistoryId) {
        ComputerHistory computerHistory = computerHistoryRepository.findById(computerHistoryId)
                .orElseThrow(() -> new RuntimeException("컴퓨터 히스토리를 찾을 수 없습니다."));
        computerHistory.delete("Y");
        return computerHistoryRepository.save(computerHistory);
    }
}
