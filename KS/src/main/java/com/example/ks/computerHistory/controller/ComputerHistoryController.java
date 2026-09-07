package com.example.ks.computerHistory.controller;

import com.example.ks.computer.domain.Computer;
import com.example.ks.computer.service.ComputerService;
import com.example.ks.computerHistory.domain.ComputerHistory;
import com.example.ks.computerHistory.dto.UpdateComputerHistory;
import com.example.ks.computerHistory.service.ComputerHistoryService;
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
public class ComputerHistoryController {

    private final ComputerHistoryService computerHistoryService;
    private final ComputerService computerService;
    private final DepartmentService departmentService;

    // 특정 컴퓨터의 이력 조회 (설치일 / 부서이동)
    @GetMapping("/computer/{computer_id}/history")
    public ModelAndView findAllByComputer_ComputerId(@PathVariable("computer_id") int computerId) {
        ModelAndView modelAndView = new ModelAndView("computerHistory");

        Computer computer = computerService.findByComputerId(computerId);

        List<ComputerHistory> history = computerHistoryService.findByComputer_ComputerId(computerId).stream()
                .filter(h -> "N".equals(h.getDel()))
                .toList();

        modelAndView.addObject("computer", computer);
        modelAndView.addObject("history", history);

        return modelAndView;
    }

    // 컴퓨터 이력 수정 폼
    @GetMapping("/computer/history/edit/{historyId}")
    public ModelAndView editComputerHistoryForm(@PathVariable("historyId") int historyId) {
        ComputerHistory history = computerHistoryService.getComputerHistory(historyId);
        ModelAndView modelAndView = new ModelAndView("computerHistoryEdit");
        modelAndView.addObject("history", history);
        modelAndView.addObject("departments", departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList());
        return modelAndView;
    }

    // 컴퓨터 이력 수정
    @PostMapping("/computer/history/update")
    public String updateComputerHistory(@Valid @ModelAttribute("history") UpdateComputerHistory dto) {
        computerHistoryService.updateComputerHistory(dto);
        ComputerHistory history = computerHistoryService.getComputerHistory(dto.computerHistoryId());
        return "redirect:/computer/" + history.getComputer().getComputerId() + "/history";
    }

    // 잘못 기록된 이력 삭제
    @GetMapping("/computer/history/delete/{historyId}")
    public String deleteComputerHistory(@PathVariable("historyId") int historyId) {
        ComputerHistory computerHistory = computerHistoryService.deleteComputerHistory(historyId);
        return "redirect:/computer/" + computerHistory.getComputer().getComputerId() + "/history";
    }
}
