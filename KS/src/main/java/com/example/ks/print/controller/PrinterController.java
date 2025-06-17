package com.example.ks.print.controller;

import com.example.ks.department.domain.Department;
import com.example.ks.department.service.DepartmentService;
import com.example.ks.print.domain.Printer;
import com.example.ks.print.dto.CreatePrinter;
import com.example.ks.print.dto.UpdatePrinter;
import com.example.ks.print.service.PrinterService;
import com.example.ks.printHistory.dto.CreatePrinterHistory;
import com.example.ks.printHistory.service.PrinterHistoryService;
import com.example.ks.printerToner.service.PrinterTonerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class PrinterController {
    private final DepartmentService departmentService;
    private final PrinterService printerService;
    private final PrinterHistoryService printerHistoryService;
    private final PrinterTonerService printerTonerService;

    @GetMapping("/printer")
    public ModelAndView printer() {
        ModelAndView modelAndView = new ModelAndView("printer");
        List<Printer> printers = printerService.findAll()
                .stream()
                .filter(printer -> !"Y".equals(printer.getDel())) // del이 "Y"인 것 제외
                .sorted(Comparator
                        .comparing((Printer c) -> c.getDepartment().getDepartmentFloor(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(c -> c.getDepartment().getDepartmentName(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Printer::getPrintPlace, Comparator.nullsLast(Comparator.naturalOrder()))
                )
                .toList(); // 필터링된 프린터 리스트
        ArrayList<String> tonerName = new ArrayList<>();
        for (Printer printer : printers) {
            tonerName.add(printerTonerService.printToner(printer.getPrintCode()));

        }
        modelAndView.addObject("printers", printers);
        modelAndView.addObject("tonerName", tonerName);
        return modelAndView;
    }

    @GetMapping("/printer/create")
    public ModelAndView createPrinterForm() {
        ModelAndView modelAndView = new ModelAndView("printerCreate");
        CreatePrinter createPrinter = CreatePrinter.builder().build();
        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();


        modelAndView.addObject("printer", createPrinter);
        modelAndView.addObject("departments", departments); // 부서 선택용 리스트 전달

        return modelAndView;
    }

    @PostMapping("/printer/create")
    public String createPrinter(@Valid @ModelAttribute CreatePrinter createPrinter) {
        printerService.create(createPrinter); // 프린터 생성 로직 실행
        return "redirect:/printer";
    }


    @GetMapping("/printer/{printer_id}")
    public ModelAndView findPrinterById(@PathVariable("printer_id") int printerId) {
        ModelAndView modelAndView = new ModelAndView("printerDetail");

        Printer printer = printerService.findByPrinterId(printerId);

        modelAndView.addObject("printer", printer);
        modelAndView.addObject("printer_id", printerId);
        return modelAndView;
    }

    @GetMapping("/printer/update/{printer_id}")
    public ModelAndView updatePrinterForm(@PathVariable("printer_id") int printerId) {
        ModelAndView modelAndView = new ModelAndView("printerUpdate");

        Printer printer = printerService.findByPrinterId(printerId);

        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();

        modelAndView.addObject("printer", printer); // form의 th:object
        modelAndView.addObject("departments", departments); // select 용 부서 리스트
        return modelAndView;
    }

    @PostMapping("/printer/update")
    public String updatePrinter(@Valid @ModelAttribute UpdatePrinter updatePrinter) {
        // 기존 프린터 정보
        Printer existingPrinter = printerService.findByPrinterId(updatePrinter.printerId());
        String currentDepartmentId = String.valueOf(existingPrinter.getDepartment().getDepartmentId());

        // 부서가 변경된 경우에만 히스토리 기록
        if (!currentDepartmentId.equals(updatePrinter.departmentId())) {
            // 부서 이름 조회 (departmentService를 통해)
            Department beforeDept = existingPrinter.getDepartment();
            Department afterDept = departmentService.findById(Integer.parseInt(updatePrinter.departmentId()));

            CreatePrinterHistory historyDto = CreatePrinterHistory.builder()
                    .departmentBeforeName(beforeDept.getDepartmentName())
                    .departmentNewName(afterDept.getDepartmentName())
                    .printAfterDate(LocalDate.now())
                    .printText("부서 변경에 따른 이력 자동 생성")
                    .printRepair(null)  // 수리 내역은 없음
                    .printRepairDate(null)
                    .historyTag("부서 변경")
                    .del("N")
                    .printerId(updatePrinter.printerId())
                    .build();

            printerHistoryService.CreatePrinterHistory(historyDto);
        }
        printerService.update(updatePrinter);
        return "redirect:/printer/" + updatePrinter.printerId(); // 수정 후 상세 페이지로 이동
    }


}
