package com.example.ec_mall.service;

import com.example.ec_mall.dao.MemberDao;
import com.example.ec_mall.dto.MemberRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.mapper.MemberMapper;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import com.example.ec_mall.util.SHA256;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    MemberService memberService;
    @InjectMocks
    MemberRequestDTO memberRequestDTO;
    @InjectMocks
    MemberDao memberDao;

    @Mock
    MemberMapper memberMapper;

    @BeforeEach
    void setUp(){
        memberRequestDTO = MemberRequestDTO.builder()
                .email("est@test.com")
                .nickName("test")
                .password(SHA256.encrypt("testPassword1!"))
                .build();
    }
    /**
     * when() = 원하는 값을 리턴하는 기능을 제공하는 메소드(Mock이 감싸고 있는 메소드가 호출되었을 때, Mock 객체의 메소드를 호출할 때 사용.
     * verify().method() = 원하는 메소드가 특정 조건으로 실행되었는지 검증.
     */
    @Test
    @DisplayName("회원가입 성공")
    void signUpTestSuccess(){
        when(memberMapper.signUpMember(memberDao)).thenReturn(1);
        assertThat(memberMapper.signUpMember(memberDao)).isEqualTo(1);
    }

    @Test
    @DisplayName("회원가입 실패 : 중복된 이메일")
    void signUpTestEmailCheck(){
        when(memberMapper.emailCheck(memberRequestDTO.getEmail())).thenReturn(1);
        Assertions.assertThrows(APIException.class, ()-> memberService.signUpMember(memberRequestDTO));

        verify(memberMapper, atLeastOnce()).emailCheck(memberRequestDTO.getEmail());
    }
}
