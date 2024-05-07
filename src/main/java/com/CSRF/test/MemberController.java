package com.CSRF.test;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static java.rmi.server.LogStream.log;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @PostMapping("/login")
    public String login(String account, String password, HttpServletResponse response) {
        if (memberService.login(account, password)) {
            Cookie cookie = new Cookie("USER_SESSION", account);
            response.addCookie(cookie);
            log("로그인 성공");
            return "redirect:/change-password";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/change-password")
    public String changePasswordPage() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(HttpServletRequest request, String new_pw, String confirm_pw) {
        boolean success = memberService.changePassword(request, new_pw, confirm_pw);
        if (success) {
            return "redirect:/home";
        }
        return "redirect:/change-password?error";
    }
}
