package com.example.ks.computer.controller;

import com.example.ks.computer.domain.Computer;
import com.example.ks.computer.dto.CreateComputer;
import com.example.ks.computer.dto.UpdateComputer;
import com.example.ks.computer.service.ComputerService;
import com.example.ks.department.domain.Department;
import com.example.ks.department.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ComputerController {

    private final ComputerService computerService;
    private final DepartmentService departmentService;

    @GetMapping("/computer")
    public ModelAndView computerList() {
        ModelAndView mav = new ModelAndView("computer");
        List<Computer> computers = computerService.findAll()
                .stream()
                .filter(computer -> !"Y".equals(computer.getDel()))
                .toList();
        mav.addObject("computers", computers);
        return mav;
    }

    @GetMapping("/computer/create")
    public ModelAndView createForm() {
        ModelAndView mav = new ModelAndView("computerCreate");
        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();
        mav.addObject("departments", departments);
        return mav;
    }

    @PostMapping("/computer/create")
    public String createComputer(@Valid @ModelAttribute CreateComputer createComputer) {
        computerService.create(createComputer);
        return "redirect:/computer";
    }

    @GetMapping("/computer/{computer_id}")
    public ModelAndView computerDetail(@PathVariable("computer_id") int computerId) {
        ModelAndView mav = new ModelAndView("computerDetail");
        Computer computer = computerService.findByComputerId(computerId);
        mav.addObject("computer", computer);
        return mav;
    }

    @GetMapping("/computer/update/{computer_id}")
    public ModelAndView updateForm(@PathVariable("computer_id") int computerId) {
        ModelAndView mav = new ModelAndView("computerUpdate");
        Computer computer = computerService.findByComputerId(computerId);
        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();
        mav.addObject("computer", computer);
        mav.addObject("departments", departments);
        return mav;
    }

    @PostMapping("/computer/update/{computer_id}")
    public String updateComputer(@Valid @ModelAttribute UpdateComputer updateComputer, @PathVariable("computer_id") int computerId) {
        computerService.update(updateComputer,computerId);
        return "redirect:/computer";
    }

    @GetMapping("/computer/delete/{computer_id}")
    public String deleteComputer(@PathVariable("computer_id") int computerId) {
        computerService.delete(computerId);
        return "redirect:/computer";
    }
}
