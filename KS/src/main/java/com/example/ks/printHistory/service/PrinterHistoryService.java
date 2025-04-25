package com.example.ks.printHistory.service;

import com.example.ks.print.domain.Printer;
import com.example.ks.print.repository.PrinterRepository;
import com.example.ks.printHistory.domain.PrinterHistory;
import com.example.ks.printHistory.dto.CreatePrinterHistory;
import com.example.ks.printHistory.dto.UpdatePrinterHistory;
import com.example.ks.printHistory.repository.PrinterHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrinterHistoryService {
    private final PrinterHistoryRepository printerHistoryRepository;
    private final PrinterRepository printerRepository;

    @Transactional(readOnly = true)
    public List<PrinterHistory> findByPrinter_PrinterId(int printerId) {
        return printerHistoryRepository.findAllByPrinter_PrinterId(printerId);
    }

    @Transactional(readOnly = true)
    public List<PrinterHistory> findByRepair_PrinterId(int printerId) {
        List<PrinterHistory> historyList = printerHistoryRepository.findAllByPrinter_PrinterIdOrderByPrintRepairDateDesc(printerId);

        return historyList.stream()
                .filter(history -> "수리".equals(history.getHistoryTag()))
                .filter(history -> "N".equals(history.getDel()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrinterHistory> findByLocate_PrinterId(int printerId) {
        List<PrinterHistory> historyList = printerHistoryRepository.findAllByPrinter_PrinterIdOrderByPrintRepairDateDesc(printerId);

        return historyList.stream()
                .filter(history -> "부서 변경".equals(history.getHistoryTag()))
                .filter(history -> "N".equals(history.getDel()))
                .toList();
    }

    public void CreatePrinterHistory(CreatePrinterHistory createPrinterHistory) {
        Printer printer = printerRepository.findByPrinterId(createPrinterHistory.printerId());
        printerHistoryRepository.save(PrinterHistory.toEntity(createPrinterHistory, printer));
    }

    public void UpdatePrinterHistory(UpdatePrinterHistory updatePrinterHistory) {
        PrinterHistory printerHistory = printerHistoryRepository.findById(updatePrinterHistory.printerHistoryId())
                .orElseThrow(() -> new RuntimeException("프린터 히스토리를 찾을 수 없습니다."));
        printerHistory.update(updatePrinterHistory);
        printerHistoryRepository.save(printerHistory);
    }

    public PrinterHistory delPrinterHistory(int printerHistoryId) {
        PrinterHistory printerHistory = printerHistoryRepository.findById(printerHistoryId)
                .orElseThrow(() -> new RuntimeException("프린터 히스토리를 찾을 수 없습니다."));
        printerHistory.delete("Y");
        return printerHistoryRepository.save(printerHistory);
    }

    @Transactional(readOnly = true)
    public PrinterHistory printHistory(int printerHistoryId) {
        return printerHistoryRepository.findByPrinterHistoryId(printerHistoryId);
    }
}
