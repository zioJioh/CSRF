package com.CSRF.test;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean login(String account, String password) {
        return memberRepository.findByAccount(account)
                .map(member -> member.getPassword().equals(password))
                .orElse(false);
    }

    @Transactional
    public boolean changePassword(HttpServletRequest request, String newPassword, String confirmPassword) {
        //새 pw, pw 확인
        Cookie[] cookies = request.getCookies();
        String account = (cookies == null) ? null : Arrays.stream(cookies)
                .filter(cookie -> "USER_SESSION".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (account != null && newPassword.equals(confirmPassword)) {
            return memberRepository.findByAccount(account)
                    .map(member -> {
                        member.setPassword(newPassword);
                        memberRepository.save(member);
                        return true;
                    }).orElse(false);
        }

        return false;
    }
}
