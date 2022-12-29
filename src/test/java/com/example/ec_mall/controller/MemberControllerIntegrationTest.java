package com.example.ec_mall.controller;

import com.example.ec_mall.dto.request.MemberRequestDTO.*;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    MemberService memberService;
    RequestDTO requestDTO;
    LoginDTO loginDTO;
    MockHttpSession session;

    @BeforeEach
    void init() {
        requestDTO = RequestDTO.builder()
                .email("test@test.com")
                .password("testPassword1!")
                .nickName("asdf")
                .build();
    }
    @Test
    @DisplayName("회원등록 성공")
    void signUpMember() throws Exception{
        mockMvc.perform(post("/member/signUp")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(requestDTO)))
               .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("검증-비밀번호 규칙 위반")
    void invalidPassword() throws Exception{
        requestDTO = RequestDTO.builder()
                .email("test@test.com")
                .password("1234")
                .nickName("test")
                .build();

        mockMvc.perform(post("/member/signUp")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(requestDTO)))
               .andDo(print());
    }

    @Test
    @DisplayName("검증-닉네임 빈 값이면 가입 실패")
    void blankNickname() throws Exception{
        requestDTO = RequestDTO.builder()
                .email("test@test.com")
                .password("1234")
                .nickName(null)
                .build();

        mockMvc.perform(post("/member/signUp")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(requestDTO)))
               .andDo(print());

    }
    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception{
        loginDTO = LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        session = new MockHttpSession();
        session.setAttribute("account", loginDTO.getEmail());

        mockMvc.perform(post("/member/login")
               .session(session)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(loginDTO)))
               .andExpect(result -> Assertions.assertEquals(session.getAttribute("account"), "test@naver.com"))
               .andExpect(status().isOk())
               .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패")
    void loginFail() throws Exception{
        loginDTO = LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        doThrow(new APIException(ErrorCode.NOT_FOUND_ACCOUNT)).when(memberService).login(loginDTO.getEmail(), loginDTO.getPassword());
        mockMvc.perform(post("/member/login")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsBytes(loginDTO)))
               .andExpect(result -> Assertions.assertThrows(APIException.class, () -> memberService.login(loginDTO.getEmail(), loginDTO.getPassword())))
               .andExpect(jsonPath("$.status").value(802))
               .andExpect(jsonPath("$.message").value("아이디 또는 비밀번호를 확인해주세요."))
               .andDo(print());
    }
}
