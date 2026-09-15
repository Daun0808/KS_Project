package com.example.ks.monitor.domain;

import com.example.ks.computer.domain.Computer;
import com.example.ks.department.domain.Department;
import com.example.ks.monitor.dto.CreateMonitor;
import com.example.ks.monitor.dto.UpdateMonitor;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "monitor")
@NoArgsConstructor
@Getter
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monitor_id")
    private Integer monitorId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computer_id")
    private Computer computer;

    @Column(name = "monitor_place", nullable = false, length = 30)
    private String monitorPlace;

    @Column(name = "monitor_unique", length = 30)
    private String monitorUnique;

    @Column(name = "monitor_manufacturer", length = 30)
    private String monitorManufacturer;

    @Column(name = "monitor_sale_date")
    private LocalDate monitorSaleDate;

    @Column(name = "monitor_size", length = 10)
    private String monitorSize;

    @Column(name = "monitor_text", length = 30)
    private String monitorText;

    @Column(name = "monitor_del", length = 1)
    private String monitorDel;

    @Column(name = "monitor_del_text", length = 50)
    private String monitorDelText;

    @Column(name = "del", nullable = false, length = 1)
    private String del;

    @Column(name = "monitor_del_date")
    private LocalDate monitorDelDate;

    @Builder
    public Monitor(
            Department department,
            String monitorPlace,
            String monitorUnique,
            String monitorManufacturer,
            LocalDate monitorSaleDate,
            String monitorSize,
            String monitorText,
            String monitorDel,
            String monitorDelText,
            String del,
            LocalDate monitorDelDate
    ) {
        this.department = department;
        this.monitorPlace = monitorPlace;
        this.monitorUnique = monitorUnique;
        this.monitorManufacturer = monitorManufacturer;
        this.monitorSaleDate = monitorSaleDate;
        this.monitorSize = monitorSize;
        this.monitorText = monitorText;
        this.monitorDel = monitorDel;
        this.monitorDelText = monitorDelText;
        this.del = del;
        this.monitorDelDate = monitorDelDate;
    }

    public static Monitor toEntity(CreateMonitor dto, Department department) {
        return Monitor.builder()
                .department(department)
                .monitorPlace(dto.monitorPlace())
                .monitorUnique(dto.monitorUnique())
                .monitorManufacturer(dto.monitorManufacturer())
                .monitorSaleDate(dto.monitorSaleDate())
                .monitorSize(dto.monitorSize())
                .monitorText(dto.monitorText())
                .monitorDel(dto.monitorDel())
                .monitorDelText(dto.monitorDelText())
                .del(dto.del())
                .monitorDelDate(null)
                .build();
    }

    public void update(UpdateMonitor dto, Department department) {
        this.department = department;
        this.monitorPlace = dto.monitorPlace();
        this.monitorUnique = dto.monitorUnique();
        this.monitorManufacturer = dto.monitorManufacturer();
        this.monitorSaleDate = dto.monitorSaleDate();
        this.monitorSize = dto.monitorSize();
        this.monitorText = dto.monitorText();
        this.monitorDel = dto.monitorDel();
        this.monitorDelText = dto.monitorDelText();
        this.monitorDelDate = dto.monitorDelDate();
    }

    public void delete(String del) {
        this.del = del;
    }

    public void linkComputer(Computer computer) {
        this.computer = computer;
    }

    public void unlinkComputer() {
        this.computer = null;
    }

    // [6단계] "폐기" 처리: 실제 행을 지우는 게 아니라 폐기 여부/날짜/사유를 남기고 컴퓨터와의 연결만 끊는다.
    // del(전체 삭제 플래그)은 건드리지 않으므로 목록에는 계속 보이고, "폐기 상태" 컬럼에만 표시된다.
    public void dispose(String reason) {
        this.monitorDel = "Y";
        this.monitorDelDate = LocalDate.now();
        this.monitorDelText = reason;
        this.computer = null;
    }
}
