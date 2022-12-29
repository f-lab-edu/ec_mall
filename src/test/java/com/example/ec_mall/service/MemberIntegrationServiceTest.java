package com.example.ec_mall.service;

import com.example.ec_mall.dto.request.MemberRequestDTO.*;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.util.SHA256;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
public class MemberIntegrationServiceTest {
    @Autowired
    MemberService memberService;
    RequestDTO requestDTO;
    LoginDTO loginDTO;

    @Test
    @DisplayName("회원가입 성공")
    void signUpTestSuccess(){
        requestDTO = RequestDTO.builder()
                .email("est@test.com")
                .nickName("test")
                .password(SHA256.encrypt("testPassword1!"))
                .build();
        memberService.signUpMember(requestDTO);
    }
    @Test
    @DisplayName("회원가입 실패 : 중복된 이메일")
    void signUpTestEmailCheck(){
        requestDTO = RequestDTO.builder()
                .email("est@test.com")
                .nickName("test12")
                .password(SHA256.encrypt("passwordTest12"))
                .build();

        APIException exception = assertThrows(APIException.class, () -> memberService.signUpMember(requestDTO));
        assertEquals(ErrorCode.ALREADY_SAVED_EMAIL, exception.getErrorCode());
    }
    @Test
    @DisplayName("로그인 성공")
    void loginSuccess(){
        loginDTO = LoginDTO.builder()
                .email("est@test.com")
                .password(SHA256.encrypt("testPassword1!"))
                .build();
        memberService.login(loginDTO.getEmail(), loginDTO.getPassword());
        assertThat(loginDTO.getEmail()).isEqualTo("est@test.com");
    }
    @Test
    @DisplayName("로그인 실패 : password가 다를 경우 Exception(NOT_FOUNT_ACCOUNT) 발생")
    void loginFail(){
        loginDTO = LoginDTO.builder()
                .email("est@test.com")
                .password(SHA256.encrypt("Test1234!@#$"))
                .build();

        APIException exception = assertThrows(APIException.class, () -> memberService.login(loginDTO.getEmail(), loginDTO.getPassword()));
        assertEquals(ErrorCode.NOT_FOUND_ACCOUNT, exception.getErrorCode());
    }
}