package com.example.ks.print.controller;

import com.example.ks.department.domain.Department;
import com.example.ks.department.service.DepartmentService;
import com.example.ks.print.domain.Printer;
import com.example.ks.print.dto.CreatePrinter;
import com.example.ks.print.service.PrinterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class PrinterController {
    private final DepartmentService departmentService;
    private final PrinterService printerService;

    @GetMapping("/printer")
    public ModelAndView printer() {
        ModelAndView modelAndView = new ModelAndView("printer");
        List<Printer> printers = printerService.findAll();
        modelAndView.addObject("printers", printers);
        return modelAndView;
    }

    @GetMapping("/printer/create")
    public ModelAndView createPrinterForm() {
        ModelAndView modelAndView = new ModelAndView("printerCreate");
        CreatePrinter createPrinter = CreatePrinter.builder().build();
        List<Department> departments = departmentService.findAll(); // 부서 리스트 가져오기

        modelAndView.addObject("printer", createPrinter);
        modelAndView.addObject("departments", departments); // 부서 선택용 리스트 전달

        return modelAndView;
    }

    @PostMapping("/printer/create")
    public String createPrinter(@Valid @ModelAttribute CreatePrinter createPrinter) {
        printerService.create(createPrinter); // 프린터 생성 로직 실행
        return "redirect:/printer";
    }

}
