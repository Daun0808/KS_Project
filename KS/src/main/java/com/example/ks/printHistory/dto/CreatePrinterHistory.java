package com.example.ks.printHistory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreatePrinterHistory(
        @Size(max = 15)
        String departmentBeforeName,

        @Size(max = 15)
        String departmentNewName,

        LocalDate printAfterDate,

        @Size(max = 50)
        String printText,

        @Size(max = 30)
        String printRepair,

        LocalDate printRepairDate,

        @Size(max = 10)
        String historyTag,

        @NotBlank @Size(max = 1)
        String del,

        @NotNull
        Integer printerId
) {}
