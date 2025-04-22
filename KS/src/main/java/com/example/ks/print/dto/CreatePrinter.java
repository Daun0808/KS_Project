package com.example.ks.print.dto;

import com.example.ks.department.domain.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreatePrinter(
        @NotBlank @Size(max = 20)
        String printPlace,

        @NotNull
        LocalDate printPlaceDate,

        @NotBlank @Size(max = 20)
        String printName,

        @NotBlank @Size(max = 20)
        String printCode,

        @NotBlank @Size(max = 1)
        String printColor,

        @NotNull
        LocalDate printProductDate,

        @NotNull
        LocalDate printBuyDate,

        @Size(max = 30)
        String printSerialNumber,

        @NotBlank @Size(max = 20)
        String printUniqueNumber,

        @Size(max = 15)
        String printIp,

        @Size(max = 20)
        String printText,

        @NotBlank @Size(max = 1)
        String printDel,

        LocalDate printDelDate,

        @Size(max = 50)
        String printDelText,

        @NotBlank @Size(max = 1)
        String del,

        @NotNull
        String departmentId
) {
}