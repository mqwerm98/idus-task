package com.idus.demo.service;

import com.idus.demo.advice.exception.LoginFailedException;
import com.idus.demo.config.JwtTokenProvider;
import com.idus.demo.dto.request.ReqLoginDto;
import com.idus.demo.dto.request.ReqSignUpDto;
import com.idus.demo.entity.Member;
import com.idus.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(ReqSignUpDto dto) {
        Member member = new Member(dto.getName(), dto.getNickname(), passwordEncoder.encode(dto.getPassword()),
                dto.getPhone(), dto.getEmail(), dto.getGender());
        memberRepository.save(member);
    }

    public String login(ReqLoginDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(LoginFailedException::new);
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) throw new LoginFailedException();

        return jwtTokenProvider.createToken(member.getEmail(), "ROLE_USER");
    }

}
