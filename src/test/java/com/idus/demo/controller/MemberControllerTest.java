package com.idus.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.demo.TestService;
import com.idus.demo.dto.request.ReqLoginDto;
import com.idus.demo.dto.request.ReqSignUpDto;
import com.idus.demo.dto.response.ResMemberDetailDto;
import com.idus.demo.dto.response.ResMemberListDto;
import com.idus.demo.entity.Gender;
import com.idus.demo.entity.Member;
import com.idus.demo.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MemberControllerTest {

    private final String URL = "/api/v1";
    private final String TOKEN = "X-AUTH-TOKEN";
    private final String email = "hjpark@test.com";
    private final String password = "Test1234!!!!";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TestService testService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 성공")
    void signUp_success() throws Exception {
        ReqSignUpDto dto = new ReqSignUpDto("테스트", "test", "Test1234!!!!!!!", "01012341234", "test@test.com", Gender.M);
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = this.mockMvc.perform(post(URL + "/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));

        Optional<Member> member = memberRepository.findByEmail("test@test.com");
        assertEquals(member.get().getNickname(), "test");
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        login(email, password);
    }

    @Test
    @DisplayName("단일 회원 상세 정보 조회 성공")
    void getMemberDetail_success() throws Exception {
        String token = login(email, password);

        MvcResult result = this.mockMvc.perform(get(URL + "/member")
                .header(TOKEN, token))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));

        ResMemberDetailDto response = modelMapper.map(testService.getData(result), ResMemberDetailDto.class);
        assertEquals(response.getEmail(), email);
    }

    @Test
    @DisplayName("여러 회원 목록 조회 성공")
    void getMemberList_success() throws Exception {
        String token = login(email, password);

        MvcResult result = this.mockMvc.perform(get(URL + "/member/list")
                .header(TOKEN, token)
                .param("page", "1")
                .param("size", "10")
                .param("option", "email")
                .param("value", email))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));
        ResMemberListDto response = modelMapper.map(testService.getData(result), ResMemberListDto.class);
        assertEquals(response.getMemberList().get(0).getEmail(), email);
    }

    private String login(String email, String password) throws Exception {
        ReqLoginDto dto = new ReqLoginDto(email, password);
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = this.mockMvc.perform(post(URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));
        assertFalse(testService.getData(result).toString().isBlank());

        return testService.getData(result).toString();
    }
}