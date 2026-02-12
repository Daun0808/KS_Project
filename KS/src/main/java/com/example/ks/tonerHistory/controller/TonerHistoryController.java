package com.example.ks.tonerHistory.controller;

import com.example.ks.department.service.DepartmentService;
import com.example.ks.toner.domain.Toner;
import com.example.ks.toner.service.TonerService;
import com.example.ks.tonerHistory.domain.TonerHistory;
import com.example.ks.tonerHistory.dto.CreateTonerHistory;
import com.example.ks.tonerHistory.dto.UpdateTonerHistory;
import com.example.ks.tonerHistory.service.TonerHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/toner")
@RequiredArgsConstructor
public class TonerHistoryController {
    private final TonerHistoryService tonerHistoryService;
    private final TonerService tonerService;
    private final DepartmentService departmentService;

    // 특정 토너의 히스토리 조회
    @GetMapping("/{toner_id}/history")
    public ModelAndView findAllByToner_TonerId(@PathVariable("toner_id") int tonerId) {
        ModelAndView modelAndView = new ModelAndView("tonerHistory");

        // 토너 기본 정보 조회
        Toner toner = tonerService.findByTonerId(tonerId);

        // 해당 토너의 모든 히스토리 조회
        List<TonerHistory> tonerHistory = tonerHistoryService.findByToner_TonerId(tonerId).stream()
                .filter(tonerHistory1 -> "N".equals(tonerHistory1.getDel())).toList();

        modelAndView.addObject("toner", toner);
        modelAndView.addObject("history", tonerHistory);

        return modelAndView;
    }

    @GetMapping("/month/{toner_name}/history")
    public ModelAndView findAllByToner_TonerName(@PathVariable("toner_name") String tonerName) {
        ModelAndView modelAndView = new ModelAndView("tonerHistory");

        // 토너 기본 정보 조회
        Toner toner = tonerService.findByTonerName(tonerName);

        // 해당 토너의 모든 히스토리 조회
        List<TonerHistory> tonerHistory = tonerHistoryService.findByToner_TonerId(toner.getTonerId()).stream()
                .filter(tonerHistory1 -> "N".equals(tonerHistory1.getDel())).toList();

        modelAndView.addObject("toner", toner);
        modelAndView.addObject("history", tonerHistory);

        return modelAndView;
    }

    // 새로운 토너 히스토리 생성 폼
    @GetMapping("/{toner_id}/history/create")
    public ModelAndView createTonerHistoryForm(@PathVariable("toner_id") Integer tonerId) {
        ModelAndView modelAndView = new ModelAndView("tonerHistoryCreate");
        modelAndView.addObject("toner", tonerService.findByTonerId(tonerId));
        modelAndView.addObject("departmentList",departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList());
        return modelAndView;
    }

    // 토너 히스토리 생성
    @PostMapping("/{toner_id}/history/create")
    public String createTonerHistory(@Valid @ModelAttribute("createTonerHistory") CreateTonerHistory dto,
                                     @PathVariable("toner_id") int tonerId) {
        tonerHistoryService.createTonerHistory(dto);
        return "redirect:/toner/" + tonerId + "/history";
    }

    // 토너 히스토리 수정 폼
    @GetMapping("/history/edit/{historyId}")
    public ModelAndView editTonerHistoryForm(@PathVariable("historyId") int historyId) {
        TonerHistory tonerHistory = tonerHistoryService.getTonerHistory(historyId);
        ModelAndView modelAndView = new ModelAndView("tonerHistoryEdit");
        modelAndView.addObject("tonerHistory", tonerHistory);
        modelAndView.addObject("departmentList",departmentService.findAll()
                .stream()
                .filter(dept -> "N".equals(dept.getDelete()))
                .toList());
        return modelAndView;
    }

    // 토너 히스토리 수정
    @PostMapping("/history/update")
    public String updateTonerHistory(@Valid @ModelAttribute("tonerHistory") UpdateTonerHistory dto) {
        tonerHistoryService.updateTonerHistory(dto);
        return "redirect:/toner/" + dto.tonerId() + "/history";
    }

    // 토너 히스토리 삭제
    @GetMapping("/history/delete/{historyId}")
    public String deleteTonerHistory(@PathVariable("historyId") int historyId) {
        TonerHistory tonerHistory = tonerHistoryService.deleteTonerHistory(historyId);
        return "redirect:/toner/" + tonerHistory.getToner().getTonerId() + "/history";
    }

    @GetMapping("/history/data")
    public ModelAndView dataTonerHistory() {
        List<TonerHistory> tonerHistoryList = tonerHistoryService.getTonerHistoryAll();
        ModelAndView modelAndView = new ModelAndView("tonerHistoryAll");
        modelAndView.addObject("tonerHistoryList", tonerHistoryList);
        return modelAndView;
    }
}
