package com.example.ks.computer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateComputer(
        @NotBlank @Size(max = 30) String computerPlace,
        @Size(max = 30) String computerOs,
        @Size(max = 30) String computerIp,
        @Size(max = 30) String computerHwp,
        @Size(max = 30) String computerOffice,
        @Size(max = 20) String computerS1,
        @Size(max = 20) String computerAlyac,
        @Size(max = 10) String computerUse,
        @Size(max = 40) String computerModel,
        LocalDate computerProductDate,
        @Size(max = 30) String computerChipset,
        @Size(max = 50) String computerCpu,
        @Size(max = 20) String computerMemory,
        @Size(max = 30) String computerUniqueCode,
        @Size(max = 50) String computerText,
        @Size(max = 1) String computerDel,
        @Size(max = 50) String computerDelText,
        @NotBlank @Size(max = 1) String del,
        @NotNull String departmentId,
        @Size(max = 30) String computerOsKey
) {}
