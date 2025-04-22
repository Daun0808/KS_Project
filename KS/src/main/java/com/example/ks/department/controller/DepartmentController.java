package com.example.ks.department.controller;

import com.example.ks.department.domain.Department;
import com.example.ks.department.dto.request.CreateDepartment;
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
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/department")
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView("department");
        List<Department> departments = departmentService.findAll();
        modelAndView.addObject("departments", departments);
        return modelAndView;
    }

    @GetMapping("/department/{department_id}")
    public ModelAndView findById(@PathVariable("department_id") int department_id) {
        ModelAndView modelAndView = new ModelAndView("departmentDetail");
        Department department = departmentService.findById(department_id);
        modelAndView.addObject("department", department);
        modelAndView.addObject("department_id", department_id);
        return modelAndView;
    }

    @GetMapping("/department/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("departmentCreate");
        Department department = new Department();
        modelAndView.addObject("department", department);
        return modelAndView;
    }

    @PostMapping("/department/create")
    public String create(@Valid @ModelAttribute CreateDepartment createDepartment) {
        departmentService.create(createDepartment);
        return "redirect:/department";
    }

    @GetMapping("/department/update/{department_id}")
    public ModelAndView update(@PathVariable("department_id") int department_id) {
        ModelAndView modelAndView = new ModelAndView("departmentUpdate");
        Department department = departmentService.findById(department_id);
        modelAndView.addObject("department", department);
        modelAndView.addObject("department_id", department_id);
        return modelAndView;
    }

    @PostMapping("/department/update/{department_id}")
    public String update(@Valid @ModelAttribute CreateDepartment createDepartment,
                         @PathVariable("department_id") int department_id) {
        departmentService.update(createDepartment, department_id);
        return "redirect:/department/" + department_id;
    }
}
