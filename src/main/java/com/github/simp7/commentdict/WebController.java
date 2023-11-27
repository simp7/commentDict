package com.github.simp7.commentdict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    final DAO dao;

    @Autowired
    public WebController(DAO dao) {
        this.dao = dao;
    }
    @GetMapping("/index")
    public String index(@RequestParam String keyword, Model m, @SessionAttribute(name="uid", required = false) Integer uid) {
        m.addAttribute("keyword");
        return "index";
    }
    @GetMapping("/keyword/{keyword}")
    public String keyword(@PathVariable String keyword, Model m, @SessionAttribute(name="uid", required = false) Integer uid) {
        m.addAttribute("keyword", keyword);
        try {
            List<Comment> comments = dao.getComments(uid, keyword);
            m.addAttribute("comments", comments);
        }catch (Exception e){
            logger.info("댓글 가져오는 중 문제 발생");
        }
        return "keyword";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
