package com.example.ks.toner.controller;

import com.example.ks.toner.domain.Toner;
import com.example.ks.toner.dto.CreateToner;
import com.example.ks.toner.dto.UpdateToner;
import com.example.ks.toner.service.TonerService;
import com.example.ks.tonerMonth.domain.TonerMonth;
import com.example.ks.tonerMonth.service.TonerMonthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class TonerController {

    private final TonerService tonerService;
    private final TonerMonthService tonerMonthService;

    // 토너 목록 조회
    @GetMapping("/toner")
    public ModelAndView listToner() {
        List<Toner> tonerList = tonerService.findAll();
        List<TonerMonth> tonerMonth = tonerMonthService.findByTonerMonthDate(LocalDate.now());
        ModelAndView modelAndView = new ModelAndView("toner");
        modelAndView.addObject("tonerMonth", tonerMonth);
        modelAndView.addObject("tonerList", tonerList);
        return modelAndView;
    }

    // 토너 추가 폼
    @GetMapping("/toner/create")
    public ModelAndView createTonerForm() {
        return new ModelAndView("tonerCreate"); // 나중에 create.html 만들 거야
    }

    // 토너 추가 처리
    @PostMapping("/toner/create")
    public String createToner(@Valid @ModelAttribute("createToner") CreateToner createToner) {
        tonerService.createToner(createToner);
        return "redirect:/toner";
    }

    // 토너 수정 폼
    @GetMapping("/toner/edit/{tonerId}")
    public ModelAndView editTonerForm(@PathVariable("tonerId") Integer tonerId) {
        Toner toner = tonerService.findByTonerId(tonerId);
        ModelAndView modelAndView = new ModelAndView("tonerEdit"); // edit.html
        modelAndView.addObject("toner", toner);
        return modelAndView;
    }

    // 토너 수정 처리
    @PostMapping("/toner/update")
    public String updateToner(@Valid @ModelAttribute("toner") UpdateToner updateToner) {
        tonerService.updateToner(updateToner);
        return "redirect:/toner";
    }
}
