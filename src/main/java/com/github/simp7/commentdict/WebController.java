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
    public String keyword(@PathVariable String keyword, Model m, @SessionAttribute(name="uid", required = false) int uid) {
        m.addAttribute("keyword", keyword);
        m.addAttribute("uid", uid);
        return "keyword";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
