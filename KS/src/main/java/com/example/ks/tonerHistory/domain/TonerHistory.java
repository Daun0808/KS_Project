package com.example.ks.tonerHistory.domain;

import com.example.ks.department.domain.Department;
import com.example.ks.toner.domain.Toner;
import com.example.ks.tonerHistory.dto.CreateTonerHistory;
import com.example.ks.tonerHistory.dto.UpdateTonerHistory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "toner_history")
@NoArgsConstructor
@Getter
public class TonerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private int historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toner_id", nullable = false)
    private Toner toner;  // Foreign Key: Toner 엔티티와 연관 관계를 맺기 위해 사용

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @Column(name = "history_date", nullable = false)
    private LocalDate historyDate;

    @Column(name = "history_received")
    private Integer historyReceived; // 입고수량

    @Column(name = "history_delivery")
    private Integer historyDelivery; // 출고수량

    @Column(name = "history_text", length = 20)
    private String historyText; // 메모

    @Column(name = "del", nullable = false, length = 1)
    private String del; // 삭제 플래그 값 (Y or N)

    @Builder
    public TonerHistory(Toner toner, String departmentName ,LocalDate historyDate, Integer historyReceived,
                        Integer historyDelivery, String historyText, String del) {
        this.toner = toner;
        this.departmentName = departmentName;
        this.historyDate = historyDate;
        this.historyReceived = historyReceived;
        this.historyDelivery = historyDelivery;
        this.historyText = historyText;
        this.del = del;
    }

    public static TonerHistory toEntity(CreateTonerHistory dto, Toner toner) {
        return TonerHistory.builder()
                .toner(toner)
                .departmentName(dto.departmentName())
                .historyDate(dto.historyDate())
                .historyReceived(dto.historyReceived())
                .historyDelivery(dto.historyDelivery())
                .historyText(dto.historyText())
                .del(dto.del())
                .build();
    }

    public void update(UpdateTonerHistory dto) {
        this.historyDate = dto.historyDate();
        this.historyReceived = dto.historyReceived();
        this.historyDelivery = dto.historyDelivery();
        this.historyText = dto.historyText();
        this.departmentName = dto.departmentName();
        this.del = dto.del();
    }

    public void delete(String del) {
        this.del = del;
    }
}
