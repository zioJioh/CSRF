package com.CSRF.test;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String account, String password, HttpServletResponse response) {
        if (memberService.login(account, password)) {
            Cookie cookie = new Cookie("USER_SESSION", account);
            response.addCookie(cookie);
            return "redirect:/home";
        }
        return "redirect:/login?error";
    }
}
