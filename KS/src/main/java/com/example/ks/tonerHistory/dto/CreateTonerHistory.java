package com.example.ks.tonerHistory.dto;

import com.example.ks.department.domain.Department;
import com.example.ks.toner.domain.Toner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateTonerHistory(
        @NotNull
        Integer tonerId, // 토너 ID (외래 키)

        @NotNull
        LocalDate historyDate, // 히스토리 날짜

        Integer historyReceived, // 입고수량

        Integer historyDelivery, // 출고수량

        @Size(max = 50)
        String historyText, // 히스토리 메모

        @NotBlank @Size(max = 1)
        String del, // 삭제 플래그 (Y 또는 N)

        @NotNull
        String departmentName

) {}
