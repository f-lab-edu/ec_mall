package com.example.ec_mall.controller;

import com.example.ec_mall.dto.request.MemberRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.jwt.config.SecurityConfig;
import com.example.ec_mall.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MemberController.class,
excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MemberService memberService;
    private MemberRequestDTO.RequestDTO requestDTO;
    private MemberRequestDTO.LoginDTO loginDTO;

    @BeforeEach
    void init() {
        requestDTO = MemberRequestDTO.RequestDTO.builder()
                .email("test@test.com")
                .password("testPassword1!")
                .nickName("asdf")
                .roles("USER")
                .build();
    }
    @Test
    @WithMockUser
    @DisplayName("회원등록 성공")
    void signUpMember() throws Exception{
        mockMvc.perform(post("/member/signUp").contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .content(mapper.writeValueAsBytes(requestDTO))).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("검증-비밀번호 규칙 위반")
    void invalidPassword() throws Exception{

        requestDTO = MemberRequestDTO.RequestDTO.builder()
                .email("test@test.com")
                .password("1234")
                .nickName("test")
                .build();

        mockMvc.perform(post("/member/signUp").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(requestDTO))).andDo(print());

    }

    @Test
    @DisplayName("검증-닉네임 빈 값이면 가입 실패")
    void blankNickname() throws Exception{

        requestDTO = MemberRequestDTO.RequestDTO.builder()
                .email("test@test.com")
                .password("1234")
                .nickName(null)
                .build();

        mockMvc.perform(post("/member/signUp").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(requestDTO))).andDo(print());

    }
//    @Test
//    @DisplayName("로그인 성공")
//    void loginSuccess() throws Exception{
//        loginDTO = MemberRequestDTO.LoginDTO.builder()
//                .email("test@naver.com")
//                .password("Test1234!@#$")
//                .build();
//
//        session = new MockHttpSession();
//        session.setAttribute("account", loginDTO.getEmail());
//
//        doNothing().when(memberService).login(loginDTO);
//        mockMvc.perform(post("/member/login").session(session).contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsBytes(loginDTO)))
//                .andExpect(status().isOk()).andDo(print());
//    }

    @Test
    @DisplayName("로그인 실패")
    void loginFail() throws Exception{
        loginDTO = MemberRequestDTO.LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        doThrow(new APIException(ErrorCode.NOT_FOUND_ACCOUNT)).when(memberService).login(loginDTO);
        mockMvc.perform(post("/member/login").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(loginDTO)))
                .andExpect(result -> Assertions.assertThrows(APIException.class, () -> memberService.login(loginDTO)))
                .andExpect(jsonPath("$.status").value(802))
                .andExpect(jsonPath("$.message").value("아이디 또는 비밀번호를 확인해주세요."))
                .andDo(print());
    }
//    @Test
//    @DisplayName("로그아웃 테스트, 세션 삭제")
//    void logoutWithSession() throws Exception{
//        loginDTO = MemberRequestDTO.LoginDTO.builder()
//                .email("test@naver.com")
//                .password("Test1234!@#$")
//                .build();
//        mockMvc.perform(get("/member/logout").contentType(MediaType.APPLICATION_JSON)
//                .session(session)).andExpect(result -> Assertions.assertNull(session.getAttribute("account")))
//                .andDo(print());
//
//    }
}
