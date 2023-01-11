package com.example.ec_mall.service;

import com.example.ec_mall.dao.MemberDao.UserDao;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.jwt.JwtTokenProvider;
import com.example.ec_mall.mapper.MemberMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtMemberServiceTest {
    @InjectMocks
    MemberService memberService;
    @Mock
    MemberMapper memberMapper;
    @Spy
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Spy
    AuthenticationManager authenticationManager;
    @Spy
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("로그인시 패스워드 확인")
    void matchesPassword(){
        LoginDTO loginDTO = LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!")
                .build();

        UserDao userDao = UserDao.builder()
                .email("test@naver.com")
                .password(bCryptPasswordEncoder.encode("Test1234!"))
                .build();
        Assertions.assertTrue(bCryptPasswordEncoder.matches(loginDTO.getPassword(), userDao.getPassword()));
    }
    @Test
    @DisplayName("로그인시 토큰 발행")
    void token(){
        LoginDTO loginDTO = LoginDTO.builder()
                .email("test@naver.com")
                .password("Test1234!")
                .build();

        UserDao userDao = UserDao.builder()
                .email("test@naver.com")
                .password(bCryptPasswordEncoder.encode("Test1234!"))
                .build();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        when(memberMapper.findByEmail(loginDTO.getEmail())).thenReturn(userDao);
        memberService.login(loginDTO);
        verify(jwtTokenProvider, times(1)).generateToken(authentication);
    }
}
