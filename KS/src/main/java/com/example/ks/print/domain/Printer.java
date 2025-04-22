package com.example.ks.print.domain;

import com.example.ks.department.domain.Department;
import com.example.ks.print.dto.CreatePrinter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "printer")
@NoArgsConstructor
@Getter
public class Printer {
    @Id
    @Column(name = "printer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int printerId;

    @Column(name = "print_place")
    private String printPlace;

    @Column(name = "print_place_date")
    private LocalDate printPlaceDate;

    @Column(name = "print_name")
    private String printName;

    @Column(name = "print_code")
    private String printCode;

    @Column(name = "print_color")
    private String printColor;

    @Column(name = "print_product_date")
    private LocalDate printProductDate;

    @Column(name = "print_buy_date")
    private LocalDate printBuyDate;

    @Column(name = "print_serial_number")
    private String printSerialNumber;

    @Column(name = "print_unique_number")
    private String printUniqueNumber;

    @Column(name = "print_ip")
    private String printIp;

    @Column(name = "print_text")
    private String printText;

    @Column(name = "print_del")
    private String printDel;

    @Column(name = "print_del_date")
    private LocalDate printDelDate;

    @Column(name = "print_del_text")
    private String printDelText;

    @Column(name = "del")
    private String del;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Builder
    public Printer(
            int printerId,
            String printPlace,
            LocalDate printPlaceDate,
            String printName,
            String printCode,
            String printColor,
            LocalDate printProductDate,
            LocalDate printBuyDate,
            String printSerialNumber,
            String printUniqueNumber,
            String printIp,
            String printText,
            String printDel,
            LocalDate printDelDate,
            String printDelText,
            String del,
            Department department
    ) {
        this.printerId = printerId;
        this.printPlace = printPlace;
        this.printPlaceDate = printPlaceDate;
        this.printName = printName;
        this.printCode = printCode;
        this.printColor = printColor;
        this.printProductDate = printProductDate;
        this.printBuyDate = printBuyDate;
        this.printSerialNumber = printSerialNumber;
        this.printUniqueNumber = printUniqueNumber;
        this.printIp = printIp;
        this.printText = printText;
        this.printDel = printDel;
        this.printDelDate = printDelDate;
        this.printDelText = printDelText;
        this.del = del;
        this.department = department;
    }

    public static Printer toEntity(CreatePrinter createPrinter, Department department) {
        return Printer.builder()
                .printPlace(createPrinter.printPlace())
                .printPlaceDate(createPrinter.printPlaceDate())
                .printName(createPrinter.printName())
                .printCode(createPrinter.printCode())
                .printColor(createPrinter.printColor())
                .printProductDate(createPrinter.printProductDate())
                .printBuyDate(createPrinter.printBuyDate())
                .printSerialNumber(createPrinter.printSerialNumber())
                .printUniqueNumber(createPrinter.printUniqueNumber())
                .printIp(createPrinter.printIp())
                .printText(createPrinter.printText())
                .printDel(createPrinter.printDel())
                .printDelDate(createPrinter.printDelDate())
                .printDelText(createPrinter.printDelText())
                .del(createPrinter.del())
                .department(department)
                .build();
    }
}
