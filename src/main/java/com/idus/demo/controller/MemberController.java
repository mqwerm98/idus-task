package com.idus.demo.controller;

import com.idus.demo.dto.request.PageRequest;
import com.idus.demo.dto.request.ReqLoginDto;
import com.idus.demo.dto.request.ReqSignUpDto;
import com.idus.demo.dto.response.ResMemberDetailDto;
import com.idus.demo.dto.response.ResMemberListDto;
import com.idus.demo.dto.response.Response;
import com.idus.demo.dto.response.inner.ResMemberDto;
import com.idus.demo.dto.response.inner.ResOrderDto;
import com.idus.demo.entity.Member;
import com.idus.demo.repository.MemberRepository;
import com.idus.demo.service.MemberService;
import com.idus.demo.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Api(tags = {"1. Member"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final OrderService orderService;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/sign-up")
    public Response signUp(@Validated @RequestBody ReqSignUpDto dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            return Response.BAD_REQUEST_ERROR("이미 가입된 유저입니다.");
        }

        memberService.signUp(dto);
        return Response.OK();
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public Response<String> login(@Valid @RequestBody ReqLoginDto dto) {
        String token = memberService.login(dto);
        return Response.OK(token);
    }

    @ApiOperation(value = "단일 회원 상세 정보 조회")
    @GetMapping("/member")
    public Response<ResMemberDetailDto> getMemberDetail(@AuthenticationPrincipal Member member) {
        ResMemberDetailDto dto = new ResMemberDetailDto(member);
        return Response.OK(dto);
    }

    @ApiOperation(value = "여러 회원 목록 조회")
    @GetMapping("/member/list")
    public Response getMemberList(@AuthenticationPrincipal Member member,
                                  @ApiParam(value = "페이지", required = true) @RequestParam("page") int page,
                                  @ApiParam(value = "한 페이지 사이즈", required = true) @RequestParam("size") int size,
                                  @ApiParam(value = "검색 옵션 명") @RequestParam(value = "option", required = false, defaultValue = "") String option,
                                  @ApiParam(value = "검색 옵션 값") @RequestParam(value = "value", required = false, defaultValue = "") String value) {

        // 관리자만 허용
        if (!member.getEmail().equals("hjpark@test.com")) {
            return Response.ERROR(HttpStatus.FORBIDDEN);
        }

        PageRequest pr = new PageRequest(page, size, Sort.Direction.DESC);
        List<Member> list = new ArrayList<>();

        if (option.equals("name")) {
            list = memberRepository.findAllByNameContains(value, pr.of()).getContent();
        } else if (option.equals("email")) {
            list = memberRepository.findAllByEmailContains(value, pr.of()).getContent();
        } else {
            list = memberRepository.findAll(pr.of()).getContent();
        }

        ResMemberListDto dto = new ResMemberListDto();
        for (Member m : list) {
            ResOrderDto lastOrder = orderService.getLastOrderByMember(m);
            dto.getMemberList().add(new ResMemberDto(m, lastOrder));
        }
        return Response.OK(dto);
    }

}
