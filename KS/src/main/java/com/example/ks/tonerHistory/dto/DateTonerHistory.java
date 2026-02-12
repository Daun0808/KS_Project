package com.example.ks.tonerHistory.dto;

import java.time.LocalDate;

public record DateTonerHistory(
        LocalDate historyDate,
        String departmentName,
        String tonerName,
        Integer historyDelivery
) {
}