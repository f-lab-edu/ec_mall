package com.example.ec_mall.controller;

import com.example.ec_mall.dto.MemberRequestDTO;
import com.example.ec_mall.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MemberService memberService;
    private MemberRequestDTO memberRequestDTO;

    @BeforeEach
    void init() {
        memberRequestDTO = MemberRequestDTO.builder()
                .email("test@test.com")
                .password("testPassword1!")
                .nickName("asdf")
                .build();
    }
    @Test
    @DisplayName("회원등록 성공")
    void signUpMember() throws Exception{
        mockMvc.perform(post("/member/signUp").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(memberRequestDTO))).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("검증-비밀번호 규칙 위반")
    void invalidPassword() throws Exception{

        memberRequestDTO = MemberRequestDTO.builder()
                .email("test@test.com")
                .password("1234")
                .nickName("test")
                .build();

        mockMvc.perform(post("/member/signUp").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(memberRequestDTO))).andDo(print());

    }

    @Test
    @DisplayName("검증-닉네임 빈 값이면 가입 실패")
    void blankNickname() throws Exception{

        memberRequestDTO = MemberRequestDTO.builder()
                .email("test@test.com")
                .password("1234")
                .nickName(null)
                .build();

        mockMvc.perform(post("/member/signUp").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(memberRequestDTO))).andDo(print());

    }
}
