package com.example.ks.computerHistory.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateComputerHistory(
        @NotNull
        Integer computerHistoryId,

        @Size(max = 30)
        String departmentBeforeName,

        @Size(max = 30)
        String departmentNewName,

        LocalDate placeDate,

        @Size(max = 10)
        String historyTag,

        @Size(max = 50)
        String historyText
) {}
