package com.github.simp7.commentdict;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class WebController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("/keyword/{keyword}")
    public String keyword(@PathVariable String keyword, @SessionAttribute(name = "uid", required = false) Model m) {
        m.addAttribute("keyword", keyword);
        return "keyword";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
