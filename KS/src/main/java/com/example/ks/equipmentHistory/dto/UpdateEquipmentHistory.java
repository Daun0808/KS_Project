package com.example.ks.equipmentHistory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateEquipmentHistory(
        @NotNull
        Integer equipmentHistoryId,

        @Size(max = 15)
        String departmentBeforeName,

        @Size(max = 15)
        String departmentNewName,

        LocalDate equipmentAfterDate,

        @Size(max = 50)
        String equipmentText,

        @Size(max = 30)
        String equipmentRepair,

        LocalDate equipmentRepairDate,

        @Size(max = 10)
        String historyTag,

        @NotBlank @Size(max = 1)
        String del,

        @NotNull
        Integer equipmentId
) {}
