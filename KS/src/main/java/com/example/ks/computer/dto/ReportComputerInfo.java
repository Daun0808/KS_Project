package com.example.ks.computer.dto;

import java.time.LocalDate;
import java.util.List;

public record ReportComputerInfo(
        String ip,
        String cpu,
        String model,
        String memory,
        String os,
        LocalDate productDate,
        List<MonitorReportItem> monitors
) {}
