package com.example.ks.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateMonitor(
        @NotBlank @Size(max = 30) String monitorPlace,
        @Size(max = 30) String monitorUnique,
        @Size(max = 30) String monitorManufacturer,
        LocalDate monitorSaleDate,
        @Size(max = 10) String monitorSize,
        @Size(max = 30) String monitorText,
        @Size(max = 1) String monitorDel,
        @Size(max = 50) String monitorDelText,
        @NotNull String departmentId
) {}
