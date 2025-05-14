package com.example.ks.computer.domain;

import com.example.ks.computer.dto.CreateComputer;
import com.example.ks.computer.dto.UpdateComputer;
import com.example.ks.department.domain.Department;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "computer")
@NoArgsConstructor
@Getter
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "computer_id")
    private Integer computerId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "computer_place", nullable = false, length = 30)
    private String computerPlace;

    @Column(name = "computer_os", length = 30)
    private String computerOs;

    @Column(name = "computer_ip", length = 30)
    private String computerIp;

    @Column(name = "computer_hwp", length = 30)
    private String computerHwp;

    @Column(name = "computer_office", length = 30)
    private String computerOffice;

    @Column(name = "computer_s1", length = 20)
    private String computerS1;

    @Column(name = "computer_alyac", length = 20)
    private String computerAlyac;

    @Column(name = "computer_use", length = 10)
    private String computerUse;

    @Column(name = "computer_model", length = 40)
    private String computerModel;

    @Column(name = "computer_product_date")
    private LocalDate computerProductDate;

    @Column(name = "computer_chipset", length = 30)
    private String computerChipset;

    @Column(name = "computer_cpu", length = 50)
    private String computerCpu;

    @Column(name = "computer_memory", length = 20)
    private String computerMemory;

    @Column(name = "computer_manufacturer", length = 30)
    private String computerManufacturer;

    @Column(name = "computer_sale_date")
    private LocalDate computerSaleDate;

    @Column(name = "computer_unique", length = 30)
    private String computerUniqueCode;

    @Column(name = "computer_text", length = 50)
    private String computerText;

    @Column(name = "computer_del", length = 1)
    private String computerDel;

    @Column(name = "computer_del_text", length = 50)
    private String computerDelText;

    @Column(name = "del", nullable = false, length = 1)
    private String del;

    @Builder
    public Computer(
            Department department,
            String computerPlace,
            String computerOs,
            String computerIp,
            String computerHwp,
            String computerOffice,
            String computerS1,
            String computerAlyac,
            String computerUse,
            String computerModel,
            LocalDate computerProductDate,
            String computerChipset,
            String computerCpu,
            String computerMemory,
            String computerManufacturer,
            LocalDate computerSaleDate,
            String computerUniqueCode,
            String computerText,
            String computerDel,
            String computerDelText,
            String del
    ) {
        this.department = department;
        this.computerPlace = computerPlace;
        this.computerOs = computerOs;
        this.computerIp = computerIp;
        this.computerHwp = computerHwp;
        this.computerOffice = computerOffice;
        this.computerS1 = computerS1;
        this.computerAlyac = computerAlyac;
        this.computerUse = computerUse;
        this.computerModel = computerModel;
        this.computerProductDate = computerProductDate;
        this.computerChipset = computerChipset;
        this.computerCpu = computerCpu;
        this.computerMemory = computerMemory;
        this.computerManufacturer = computerManufacturer;
        this.computerSaleDate = computerSaleDate;
        this.computerUniqueCode = computerUniqueCode;
        this.computerText = computerText;
        this.computerDel = computerDel;
        this.computerDelText = computerDelText;
        this.del = del;
    }

    public static Computer toEntity(CreateComputer dto, Department department) {
        return Computer.builder()
                .department(department)
                .computerPlace(dto.computerPlace())
                .computerOs(dto.computerOs())
                .computerIp(dto.computerIp())
                .computerHwp(dto.computerHwp())
                .computerOffice(dto.computerOffice())
                .computerS1(dto.computerS1())
                .computerAlyac(dto.computerAlyac())
                .computerUse(dto.computerUse())
                .computerModel(dto.computerModel())
                .computerProductDate(dto.computerProductDate())
                .computerChipset(dto.computerChipset())
                .computerCpu(dto.computerCpu())
                .computerMemory(dto.computerMemory())
                .computerManufacturer(dto.computerManufacturer())
                .computerSaleDate(dto.computerSaleDate())
                .computerUniqueCode(dto.computerUniqueCode())
                .computerText(dto.computerText())
                .computerDel(dto.computerDel())
                .computerDelText(dto.computerDelText())
                .del(dto.del())
                .build();
    }

    public void update(UpdateComputer dto, Department department) {
        this.department = department;
        this.computerPlace = dto.computerPlace();
        this.computerOs = dto.computerOs();
        this.computerIp = dto.computerIp();
        this.computerHwp = dto.computerHwp();
        this.computerOffice = dto.computerOffice();
        this.computerS1 = dto.computerS1();
        this.computerAlyac = dto.computerAlyac();
        this.computerUse = dto.computerUse();
        this.computerModel = dto.computerModel();
        this.computerProductDate = dto.computerProductDate();
        this.computerChipset = dto.computerChipset();
        this.computerCpu = dto.computerCpu();
        this.computerMemory = dto.computerMemory();
        this.computerManufacturer = dto.computerManufacturer();
        this.computerSaleDate = dto.computerSaleDate();
        this.computerUniqueCode = dto.computerUniqueCode();
        this.computerText = dto.computerText();
        this.computerDel = dto.computerDel();
        this.computerDelText = dto.computerDelText();
    }

    public void delete(String del){
        this.del = del;
    }
}
