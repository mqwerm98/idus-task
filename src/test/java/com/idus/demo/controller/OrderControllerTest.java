package com.idus.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.demo.TestService;
import com.idus.demo.dto.request.ReqLoginDto;
import com.idus.demo.dto.request.ReqOrderDto;
import com.idus.demo.dto.response.ResMemberOrderListDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderControllerTest {

    private final String URL = "/api/v1/order";
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

    @Test
    @DisplayName("주문 성공")
    void createOrder_success() throws Exception {
        ReqOrderDto dto = new ReqOrderDto("밥");
        String json = objectMapper.writeValueAsString(dto);
        String token = login();

        MvcResult result = this.mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .header(TOKEN, token)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));
    }

    @Test
    @DisplayName("단일 회원의 주문 목록 조회 성공")
    void getMemberOrderList_success() throws Exception {
        String token = login();

        MvcResult result = this.mockMvc.perform(get(URL + "/list")
                .header(TOKEN, token))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));

        ResMemberOrderListDto response = modelMapper.map(testService.getData(result), ResMemberOrderListDto.class);
        assertEquals(response.getOrderList().get(0).getName(), "test order");
    }

    private String login() throws Exception {
        ReqLoginDto dto = new ReqLoginDto(email, password);
        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = this.mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(testService.isSuccess(result));
        assertFalse(testService.getData(result).toString().isBlank());

        return testService.getData(result).toString();
    }
}