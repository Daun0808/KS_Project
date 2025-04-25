package com.example.ks.printHistory.domain;

import com.example.ks.print.domain.Printer;
import com.example.ks.printHistory.dto.CreatePrinterHistory;
import com.example.ks.printHistory.dto.UpdatePrinterHistory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "print_locate_history")
@NoArgsConstructor
@Getter
public class PrinterHistory {
    @Id
    @Column(name = "print_locate_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int printerHistoryId;

    @Column(name = "department_before_name")
    private String departmentBeforeName;

    @Column(name = "department_new_name")
    private String departmentNewName;

    @Column(name = "print_after_date")
    private LocalDate printAfterDate;

    @Column(name = "print_text")
    private String printText;

    @Column(name = "print_repair")
    private String printRepair;

    @Column(name = "print_repair_date")
    private LocalDate printRepairDate;

    @Column(name = "history_tag")
    private String historyTag;

    @Column(name = "del")
    private String del;

    @ManyToOne
    @JoinColumn(name = "printer_id")
    private Printer printer;

    @Builder
    public PrinterHistory(
            Printer printer,
            String departmentBeforeName,
            String departmentNewName,
            LocalDate printAfterDate,
            String printText,
            String printRepair,
            LocalDate printRepairDate,
            String historyTag,
            String del
    ) {
        this.printer = printer;
        this.departmentBeforeName = departmentBeforeName;
        this.departmentNewName = departmentNewName;
        this.printAfterDate = printAfterDate;
        this.printText = printText;
        this.printRepair = printRepair;
        this.printRepairDate = printRepairDate;
        this.historyTag = historyTag;
        this.del = del;
    }

    public static PrinterHistory toEntity(CreatePrinterHistory dto, Printer printer) {
        return PrinterHistory.builder()
                .printer(printer)
                .departmentBeforeName(dto.departmentBeforeName())
                .departmentNewName(dto.departmentNewName())
                .printAfterDate(dto.printAfterDate())
                .printText(dto.printText())
                .printRepair(dto.printRepair())
                .printRepairDate(dto.printRepairDate())
                .historyTag(dto.historyTag())
                .del(dto.del())
                .build();
    }

    public void update(UpdatePrinterHistory dto) {
        this.departmentBeforeName = dto.departmentBeforeName();
        this.departmentNewName = dto.departmentNewName();
        this.printAfterDate = dto.printAfterDate();
        this.printText = dto.printText();
        this.printRepair = dto.printRepair();
        this.printRepairDate = dto.printRepairDate();
        this.historyTag = dto.historyTag();
        this.del = dto.del();
    }

    public void delete(String del) {
        this.del = del;
    }
}
