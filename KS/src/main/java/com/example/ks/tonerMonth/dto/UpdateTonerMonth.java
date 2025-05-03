// UpdateTonerMonth.java
package com.example.ks.tonerMonth.dto;

import java.time.LocalDate;

public record UpdateTonerMonth(
        int tonerCurrentMonth,
        int tonerMonthReceived,
        int tonerMonthDelivery
) {}
