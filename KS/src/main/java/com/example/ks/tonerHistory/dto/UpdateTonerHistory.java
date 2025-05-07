package com.example.ks.tonerHistory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateTonerHistory(

        @NotNull
        Integer historyId,

        Integer historyReceived, // 수정할 입고수량

        Integer historyDelivery, // 수정할 출고수량

        @Size(max = 50)
        String historyText, // 수정할 히스토리 메모

        @Size(max = 1)
        String del, // 수정할 삭제 플래그 (Y 또는 N)

        Integer tonerId, // 토너 ID (외래 키)

        LocalDate historyDate, // 수정할 히스토리 날짜

        String departmentName
) {}
