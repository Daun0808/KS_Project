package com.example.ks.printHistory.controller;

import com.example.ks.print.domain.Printer;
import com.example.ks.print.service.PrinterService;
import com.example.ks.printHistory.domain.PrinterHistory;
import com.example.ks.printHistory.dto.CreatePrinterHistory;
import com.example.ks.printHistory.dto.UpdatePrinterHistory;
import com.example.ks.printHistory.service.PrinterHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class PrinterHistoryController {
    private final PrinterHistoryService printerHistoryService;
    private final PrinterService printerService;

    @GetMapping("/printer/{printer_id}/history")
    public ModelAndView findAllByPrinter_PrinterId(@PathVariable("printer_id") int printerId) {
        ModelAndView modelAndView = new ModelAndView("printerHistory");

        // 프린터 기본 정보 조회
        Printer printer = printerService.findByPrinterId(printerId);

        // 수리 이력 필터링 (태그가 "수리")
        List<PrinterHistory> repairHistory = printerHistoryService.findByRepair_PrinterId(printerId);

        // 부서 변경 이력 필터링 (태그가 "부서변경")
        List<PrinterHistory> changeHistory = printerHistoryService.findByLocate_PrinterId(printerId);

        modelAndView.addObject("printer", printer);
        modelAndView.addObject("repairHistory", repairHistory);
        modelAndView.addObject("changeHistory", changeHistory);

        return modelAndView;
    }

    @GetMapping("/printer/{printer_id}/repair")
    public ModelAndView createRepairForm(@PathVariable("printer_id") Integer printerId) {
        ModelAndView modelAndView = new ModelAndView("repair");
        modelAndView.addObject("printer", printerService.findByPrinterId(printerId));
        return modelAndView;
    }

    @PostMapping("/printer/repair")
    public String createRepair(@Valid @ModelAttribute("repairHistory") CreatePrinterHistory dto) {
        printerHistoryService.CreatePrinterHistory(dto);
        return "redirect:/printer/" + dto.printerId()+"/history";
    }

    @GetMapping("/printer/history/edit/{historyId}")
    public ModelAndView editPrinterHistoryForm(@PathVariable("historyId") int historyId) {
        PrinterHistory history = printerHistoryService.printHistory(historyId);
        ModelAndView modelAndView = new ModelAndView("historyEdit");
        modelAndView.addObject("history", history);
        return modelAndView;
    }

    @PostMapping("/printer/history/update")
    public String updatePrinterHistory(@Valid @ModelAttribute("history") UpdatePrinterHistory dto) {
        printerHistoryService.UpdatePrinterHistory(dto);
        return "redirect:/printer/" + dto.printerId() +"/history";
    }

    @GetMapping("/printer/history/delete/{historyId}")
    public String delPrinterHistory(@PathVariable("historyId") int historyId) {
        PrinterHistory printerHistory = printerHistoryService.delPrinterHistory(historyId);
        return "redirect:/printer/" + printerHistory.getPrinter().getPrinterId() +"/history";
    }

}
