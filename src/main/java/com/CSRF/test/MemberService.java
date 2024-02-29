package com.CSRF.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean login(String account, String password) {
        Member member = memberRepository.findByAccount(account);
        return member != null && member.getPassword().equals(password);
    }
}
