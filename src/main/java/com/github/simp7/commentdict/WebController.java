package com.github.simp7.commentdict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
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
    public String index() {
        return "index";
    }
    @GetMapping("/keyword/{keyword}")
    public String keyword(@PathVariable String keyword, Model m, @SessionAttribute(name="user", required = false) User user) {
        logger.info("검색: " + keyword);
        if (keyword == null || keyword.isEmpty()) {
            return redirectUrl(keyword);
        }
        try {
            Integer uid = user == null ? null : user.getUid();
            List<Comment> comments = dao.getComments(keyword, uid);
            m.addAttribute("comments", comments);
        } catch (Exception e){
            logger.info("댓글 로드 중 문제 발생");
            logger.error(e.getMessage());
        }
        return "keyword";
    }

    @PostMapping("/registering")
    public String register(@ModelAttribute User user) {
        try {
            dao.addUser(user.getId(), user.getPasswd());
        } catch(Exception e) {
            logger.info("회원가입 중 문제 발생");
            logger.error(e.getMessage());
        }
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String passwd, HttpServletRequest request) {
        try {
            logger.info("로그인 시도");
            User user = dao.getUser(id, passwd);
            if (user == null) {
                throw new Exception("로그인 정보가 일치하지 않습니다.");
            }
            request.getSession().setAttribute("user", user);
        } catch(Exception e) {
            logger.info("로그인 중 문제 발생");
            logger.error(e.getMessage());
        }
        return "redirect:index";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        logger.info("로그아웃 시도");
        if (session.getAttribute("user") == null) {
            throw new Exception("이미 로그아웃된 상태입니다.");
        }
        try {
            session.invalidate();
        } catch(Exception e) {
            logger.info("로그아웃 중 문제 발생");
            logger.error(e.getMessage());
        }
        return "redirect:index";
    }

    @PostMapping("/keyword/{keyword}/add")
    public String addKeyword(@PathVariable String keyword, @ModelAttribute Comment comment, @SessionAttribute(name="user", required = false) User user){
        try {
            if (user == null) {
                throw new Exception("회원 가입 후 댓글을 등록하실 수 있습니다.");
            }
            dao.addComment(keyword, comment, user.getUid());
        } catch (Exception e) {
            logger.info("댓글 등록 중 문제 발생");
            logger.error(e.getMessage());
        }
        return redirectUrl(keyword);
    }

    @GetMapping("/keyword/{keyword}/delete/{id}")
    public String deleteComment(@PathVariable int id, @PathVariable String keyword, @SessionAttribute(name="user", required = false) User user) {
        try {
            dao.deleteComment(id);
        } catch (Exception e) {
            logger.info("댓글 삭제 중 문제 발생");
            logger.error(e.getMessage());
        }
        return redirectUrl(keyword);
    }

    @GetMapping("/keyword/{keyword}/like/{id}")
    public String likeComment(@PathVariable int id, @PathVariable String keyword, @SessionAttribute(name="user", required = false) User user) {
        try {
            Integer uid = user == null ? null : user.getUid();
            dao.likeComment(id, uid);
        } catch (Exception e) {
            logger.info("댓글 추천 중 문제 발생");
            logger.error(e.getMessage());
        }
        return redirectUrl(keyword);
    }

    @GetMapping("/keyword/{keyword}/dislike/{id}")
    public String dislikeComment(@PathVariable int id, @PathVariable String keyword, @SessionAttribute(name="user", required = false) User user) {
        try {
            Integer uid = user == null ? null : user.getUid();
            dao.dislikeComment(id, uid);
        } catch (Exception e) {
            logger.info("댓글 비추천 중 문제 발생");
            logger.error(e.getMessage());
        }
        return redirectUrl(keyword);
    }
}
