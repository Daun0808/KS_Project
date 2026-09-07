package com.example.ks.computerHistory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateComputerHistory(
        @NotNull
        Integer computerId,

        @Size(max = 30)
        String departmentBeforeName, // 이동 전 부서명 (최초 등록 시 null)

        @NotBlank @Size(max = 30)
        String departmentNewName, // 이동 후(현재) 부서명

        @NotNull
        LocalDate placeDate, // 설치일

        @Size(max = 10)
        String historyTag, // 등록 / 부서 변경 / 설치일 변경

        @Size(max = 50)
        String historyText, // 메모

        @NotBlank @Size(max = 1)
        String del
) {}
