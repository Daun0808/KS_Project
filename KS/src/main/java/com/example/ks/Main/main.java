package com.example.ks.Main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class main {
    @GetMapping("/")
    public String MainPage() {
        return "index";
    }

    @GetMapping("/fileRename")
    public ModelAndView fileRename() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fileRename");
        return modelAndView;
    }
}
