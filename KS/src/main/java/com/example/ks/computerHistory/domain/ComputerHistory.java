package com.example.ks.computerHistory.domain;

import com.example.ks.computer.domain.Computer;
import com.example.ks.computerHistory.dto.CreateComputerHistory;
import com.example.ks.computerHistory.dto.UpdateComputerHistory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "computer_history")
@NoArgsConstructor
@Getter
public class ComputerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "computer_history_id")
    private int computerHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computer_id", nullable = false)
    private Computer computer;

    @Column(name = "department_before_name", length = 30)
    private String departmentBeforeName; // 이동 전 부서명 (최초 등록 시 null)

    @Column(name = "department_new_name", length = 30)
    private String departmentNewName; // 이동 후(현재) 부서명

    @Column(name = "place_date")
    private LocalDate placeDate; // 설치일 (이력이 기록된 날짜 = 오늘 날짜)

    @Column(name = "history_tag", length = 10)
    private String historyTag; // 등록 / 부서 변경 / 설치일 변경

    @Column(name = "history_text", length = 50)
    private String historyText; // 메모 (자동 기록 시 "자동이력생성" 고정 문구)

    @Column(name = "del", nullable = false, length = 1)
    private String del;

    @Builder
    public ComputerHistory(
            Computer computer,
            String departmentBeforeName,
            String departmentNewName,
            LocalDate placeDate,
            String historyTag,
            String historyText,
            String del
    ) {
        this.computer = computer;
        this.departmentBeforeName = departmentBeforeName;
        this.departmentNewName = departmentNewName;
        this.placeDate = placeDate;
        this.historyTag = historyTag;
        this.historyText = historyText;
        this.del = del;
    }

    public static ComputerHistory toEntity(CreateComputerHistory dto, Computer computer) {
        return ComputerHistory.builder()
                .computer(computer)
                .departmentBeforeName(dto.departmentBeforeName())
                .departmentNewName(dto.departmentNewName())
                .placeDate(dto.placeDate())
                .historyTag(dto.historyTag())
                .historyText(dto.historyText())
                .del(dto.del())
                .build();
    }

    public void update(UpdateComputerHistory dto) {
        this.departmentBeforeName = dto.departmentBeforeName();
        this.departmentNewName = dto.departmentNewName();
        this.placeDate = dto.placeDate();
        this.historyText = dto.historyText();
    }

    public void delete(String del) {
        this.del = del;
    }
}
