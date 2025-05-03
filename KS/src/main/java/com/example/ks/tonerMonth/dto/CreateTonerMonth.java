// CreateTonerMonth.java
package com.example.ks.tonerMonth.dto;

import java.time.LocalDate;

public record CreateTonerMonth(
        String tonerName,
        LocalDate tonerMonthDate,
        int tonerPreviousMonth,
        int tonerCurrentMonth,
        int tonerMonthReceived,
        int tonerMonthDelivery
) {}
