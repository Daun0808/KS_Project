package com.example.ks.printHistory.repository;

import com.example.ks.printHistory.domain.PrinterHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrinterHistoryRepository extends JpaRepository<PrinterHistory, Integer> {
    List<PrinterHistory> findAllByPrinter_PrinterId(int printerId);
    List<PrinterHistory> findAllByPrinter_PrinterIdOrderByPrintRepairDateDesc(int printerId);
    List<PrinterHistory> findAllByPrinter_PrinterIdOrderByPrintAfterDateDesc(int printerId);
    PrinterHistory findByPrinterHistoryId(int printHistoryId);
}
