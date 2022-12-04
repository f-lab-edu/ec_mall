package com.example.ec_mall.util;

import com.example.ec_mall.dto.request.MemberRequestDTO;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class UtilTest {

    MemberRequestDTO memberRequestDTO;

    @BeforeEach
    void setUp(){
        memberRequestDTO = MemberRequestDTO.builder()
                .email("est@test.com")
                .nickName("test")
                .password(SHA256.encrypt("asdf"))
                .build();
    }

    @Test
    @DisplayName("SHA256 암호화 테스트")
    void passwordEqual(){
        assertThat(memberRequestDTO.getPassword()).isEqualTo("f0e4c2f76c58916ec258f246851bea091d14d4247a2fc3e18694461b1816e13b");
    }
}
