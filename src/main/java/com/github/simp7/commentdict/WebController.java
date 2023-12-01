package com.github.simp7.commentdict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@SessionAttributes("uid")
@RequestMapping("/")
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    final DAO dao;

    private String redirectUrl(String keyword) {
        return "redirect:/keyword/" + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
    }

    @Autowired
    public WebController(DAO dao) {
        this.dao = dao;
    }
    @GetMapping("/index")
    public String index(@SessionAttribute(name="uid", required = false) Integer uid) {
        return "index";
    }
    @GetMapping("/keyword/{keyword}")
    public String keyword(@PathVariable String keyword, Model m, @SessionAttribute(name="uid", required = false) Integer uid) {
        logger.info("검색: " + keyword);
        if (keyword == null || keyword.isEmpty()) {
            return redirectUrl(keyword);
        }
        try {
            List<Comment> comments = dao.getComments(keyword, uid);
            m.addAttribute("comments", comments);
        }catch (Exception e){
            logger.info("댓글 로드 중 문제 발생");
            logger.error(e.getMessage());
        }
        return "keyword";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/keyword/{keyword}/add")
    public String addKeyword(@PathVariable String keyword, @ModelAttribute Comment comment, @SessionAttribute(name="uid", required = false) Integer uid){
        try {
            dao.addComment(keyword, comment, uid);
        } catch (Exception e) {
            logger.info("댓글 등록 중 문제 발생");
            logger.error(e.getMessage());
        }
        return redirectUrl(keyword);
    }

    @GetMapping("/keyword/{keyword}/delete/{id}")
    public String deleteComment(@PathVariable int id, @PathVariable String keyword, @SessionAttribute(name="uid", required = false) Integer uid) {
        try {
            dao.deleteComment(id);
        } catch (Exception e) {
            logger.info("댓글 삭제 중 문제 발생");
            logger.error(e.getMessage());
        }
        return redirectUrl(keyword);
    }

    @GetMapping("/keyword/{keyword}/like/{id}")
    public String likeComment(@PathVariable int id, @PathVariable String keyword, @SessionAttribute(name="uid", required = false) Integer uid) {
        try {
            dao.likeComment(id, uid);
        } catch (Exception e) {
            logger.info("댓글 추천 중 문제 발생");
            logger.error(e.getMessage());
        }
        return redirectUrl(keyword);
    }

    @GetMapping("/keyword/{keyword}/dislike/{id}")
    public String dislikeComment(@PathVariable int id, @PathVariable String keyword, @SessionAttribute(name="uid", required = false) Integer uid) {
        String url = "redirect:/keyword/";

        try {
            dao.dislikeComment(id, uid);
        } catch (Exception e) {
            logger.info("댓글 비추천 중 문제 발생");
            logger.error(e.getMessage());
        }
        return redirectUrl(keyword);
    }
}
