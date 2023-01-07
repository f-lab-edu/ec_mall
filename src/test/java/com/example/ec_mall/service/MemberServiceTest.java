package com.example.ec_mall.service;

import com.example.ec_mall.dao.MemberDao;
import com.example.ec_mall.dao.MemberDao.UserDao;
import com.example.ec_mall.dto.request.MemberRequestDTO;
import com.example.ec_mall.dto.request.MemberRequestDTO.RequestDTO;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.mapper.MemberMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.ec_mall.util.SHA256;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;


@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    /**
    @Mock @MockBean @InjectMock

        @Mock
     -> Mockito.mock() 메서드의 줄임말
     -> MockitoJUnitRunner 를 사용하여 테스트 실행해야한다.
     -> @InjectMock을 사용하면 @Mock으로 만들어진 인스턴스들을 자동으로 주입해준다.
     -> 실제 인스턴스가 없는 가상의 Mock 인스턴스를 만들어 반환

        @MockBean
     -> Mock 객체를 스프링 컨텍스트에 등록하게되고, @Autowired로 스프링 컨텍스트에 등록된
        mock객체들을 주입받아서 의존성 처리를 해준다.
     -> 주석을 달아준 해당 객체의 Bean이 컨테이너에 존재하고 있어야한다면 @MockBean을 사용한다.
        ex) @WebMvcTest 일때 Controller까지는 로드가 되지만 MemberService는 로드가 되지 않는다.
        그래서 실제로 controller에 요청을 보낼 때, memberService가 빈 컨테이너에 생성되어 있지 않다면 NullPointerException을 발생시킨다.
        주로 @SpringbootTest @WebMVCTest 와 함께 사용

        @InjectMock
      -> @Mock이 붙은 객체들을 주입시킨다.
        ex) MemberMapper에서 @Mock 어노테이션을 선언하지 않으면 NullPointerException 발생하면서 memberMapper가 null이라고 오류를 발생시킨다.
        memberService는 memberMapper를 주입 받아야하는데 받지 못했기 때문이다.
     */
    @InjectMocks
    MemberService memberService;
    RequestDTO requestDTO;
    UserDao memberDao;
    LoginDTO loginDTO;

    @Mock
    MemberMapper memberMapper;

    @BeforeEach
    void setUp(){
        requestDTO = RequestDTO.builder()
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
    @DisplayName("회원가입 실패 : SQL Exception")
    void signUpTestFail(){
        when(memberMapper.signUpMember(memberDao)).thenReturn(0);
        assertThat(memberMapper.signUpMember(memberDao)).isEqualTo(0);
        Assertions.assertThrows(SQLException.class, ()-> memberService.signUpMember(requestDTO));
    }

    @Test
    @DisplayName("회원가입 실패 : 중복된 이메일")
    void signUpTestEmailCheck(){
        when(memberMapper.emailCheck(requestDTO.getEmail())).thenReturn(1);
        Assertions.assertThrows(APIException.class, ()-> memberService.signUpMember(requestDTO));

        verify(memberMapper, atLeastOnce()).emailCheck(requestDTO.getEmail());
    }
    @Test
    @DisplayName("로그인 시도시 Mapper.findByEmail 호출 검증")
    void loginSuccess(){
        loginDTO = LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        memberDao = UserDao.builder()
                .accountId(1L)
                .email(loginDTO.getEmail())
                .password(SHA256.encrypt(loginDTO.getPassword()))
                .build();

        when(memberMapper.findByEmail(loginDTO.getEmail())).thenReturn(memberDao);
        memberService.login(loginDTO);
        verify(memberMapper, times(1)).findByEmail(memberDao.getEmail());
    }

    @Test
    @DisplayName("로그인 실패시 Exception(NOT_FOUNT_ACCOUNT) 발생")
    void loginFail(){
        loginDTO = MemberRequestDTO.LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!@#$")
                .build();

        when(memberMapper.findByEmailPassword(loginDTO.getEmail(), SHA256.encrypt(loginDTO.getPassword()))).thenThrow(new APIException(ErrorCode.NOT_FOUND_ACCOUNT));
        assertThrows(APIException.class, () -> memberMapper.findByEmailPassword(loginDTO.getEmail(), SHA256.encrypt(loginDTO.getPassword())));
    }
}
