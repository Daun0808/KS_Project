package com.example.ks.tonerMonth.controller;

import com.example.ks.toner.domain.Toner;
import com.example.ks.toner.service.TonerService;
import com.example.ks.tonerMonth.domain.TonerMonth;
import com.example.ks.tonerMonth.dto.CreateTonerMonthList;
import com.example.ks.tonerMonth.service.TonerMonthService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TonerMonthController {

    private final TonerMonthService tonerMonthService;
    private final TonerService tonerService;

    @GetMapping("/toner/month")
    public ModelAndView viewTonerMonth(@RequestParam(value = "date", required = false) String dateString) {
        LocalDate selectedDate;

        if (dateString != null && !dateString.isEmpty()) {
            // yyyy-MM 형식을 LocalDate의 첫날로 파싱
            selectedDate = LocalDate.parse(dateString + "-01");
        } else {
            selectedDate = LocalDate.now();
        }

        List<TonerMonth> tonerMonthList = tonerMonthService.findByTonerMonthDate(selectedDate);
        ArrayList<Integer> tonerQuantity = new ArrayList<>();
        if (!tonerMonthList.isEmpty()) {
            for (TonerMonth tonerMonth : tonerMonthList) {
                tonerQuantity.add(tonerService.findByTonerName(tonerMonth.getTonerName()).getTonerQuantity());
;            }
        }
        ModelAndView mav = new ModelAndView("tonerMonth");
        mav.addObject("tonerMonthList", tonerMonthList);
        mav.addObject("selectedDate", selectedDate);
        mav.addObject("tonerQuantity", tonerQuantity);
        return mav;
    }


    @GetMapping("/toner/month/create")
    public ModelAndView createTonerMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        List<TonerMonth> tonerMonthList = tonerMonthService.findByTonerMonthDate(lastMonth);

        ModelAndView mav = new ModelAndView("createTonerMonth");

        if (tonerMonthList.isEmpty()) {
            // 삭제되지 않은 토너만 조회
            List<Toner> tonerList = tonerService.findAll();
            List<String> tonerNames = tonerList.stream()
                    .filter(t -> t.getDel().equals("N"))
                    .map(Toner::getTonerName)
                    .toList();
            mav.addObject("tonerNames", tonerNames);
        } else {
            mav.addObject("itemList", tonerMonthList);
        }

        return mav;
    }

    @PostMapping("/toner/month/create")
    public String saveTonerMonth(@ModelAttribute CreateTonerMonthList dto) {
        tonerMonthService.createTonerMonth(dto);  // 리스트 저장 처리 로직
        return "redirect:/toner/month"; // 저장 후 목록 페이지로 이동
    }
}