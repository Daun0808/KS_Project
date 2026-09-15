package com.example.ks.computer.dto;

import java.time.LocalDate;

public record MonitorReportItem(
        String manufacturer,
        String size,
        LocalDate manufactureDate
) {}
