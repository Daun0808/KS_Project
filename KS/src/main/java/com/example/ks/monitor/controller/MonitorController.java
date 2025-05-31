package com.example.ks.monitor.controller;

import com.example.ks.computer.domain.Computer;
import com.example.ks.computer.service.ComputerService;
import com.example.ks.department.domain.Department;
import com.example.ks.department.service.DepartmentService;
import com.example.ks.monitor.domain.Monitor;
import com.example.ks.monitor.dto.CreateMonitor;
import com.example.ks.monitor.dto.UpdateMonitor;
import com.example.ks.monitor.service.MonitorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MonitorController {

    private final MonitorService monitorService;
    private final DepartmentService departmentService;
    private final ComputerService computerService;

    @GetMapping("/monitor")
    public ModelAndView monitorList() {
        ModelAndView mav = new ModelAndView("monitor");
        List<Monitor> monitors = monitorService.findAll()
                .stream()
                .filter(monitor -> !"Y".equals(monitor.getDel()))
                .sorted(Comparator
                        .comparing((Monitor c) -> c.getDepartment().getDepartmentFloor(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(c -> c.getDepartment().getDepartmentName(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Monitor::getMonitorPlace, Comparator.nullsLast(Comparator.naturalOrder()))
                )
                .toList();
        mav.addObject("monitors", monitors);
        return mav;
    }

    @GetMapping("/monitor/create")
    public ModelAndView createForm() {
        ModelAndView mav = new ModelAndView("monitorCreate");
        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();
        mav.addObject("departments", departments);
        return mav;
    }

    @PostMapping("/monitor/create")
    public String createMonitor(@Valid @ModelAttribute CreateMonitor createMonitor) {
        monitorService.create(createMonitor);
        return "redirect:/monitor";
    }

    @GetMapping("/monitor/{monitor_id}")
    public ModelAndView monitorDetail(@PathVariable("monitor_id") int monitorId) {
        ModelAndView mav = new ModelAndView("monitorDetail");
        List<Computer> computers = computerService.findAll()
                .stream()
                .filter(computer -> !"Y".equals(computer.getDel()))
                .toList();
        Monitor monitor = monitorService.findByMonitorId(monitorId);
        mav.addObject("monitor", monitor);
        mav.addObject("computers", computers);
        return mav;
    }

    @GetMapping("/monitor/update/{monitor_id}")
    public ModelAndView updateForm(@PathVariable("monitor_id") int monitorId) {
        ModelAndView mav = new ModelAndView("monitorUpdate");
        Monitor monitor = monitorService.findByMonitorId(monitorId);
        List<Department> departments = departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList();
        mav.addObject("monitor", monitor);
        mav.addObject("departments", departments);
        return mav;
    }

    @PostMapping("/monitor/update/{monitor_id}")
    public String updateMonitor(@Valid @ModelAttribute UpdateMonitor updateMonitor, @PathVariable("monitor_id") int monitorId) {
        monitorService.update(updateMonitor, monitorId);
        return "redirect:/monitor";
    }

    @GetMapping("/monitor/delete/{monitor_id}")
    public String deleteMonitor(@PathVariable("monitor_id") int monitorId) {
        monitorService.delete(monitorId);
        return "redirect:/monitor";
    }
}
