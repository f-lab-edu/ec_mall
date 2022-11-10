package com.example.ec_mall.controller;

import com.example.ec_mall.dto.MemberRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MemberRequestDTO memberRequestDTO;

    @BeforeEach
    void init() {
        memberRequestDTO = MemberRequestDTO.builder()
                .email("23r23@23r.com")
                .password("testPassword1!")
                .nickName("asdf")
                .build();
    }
    @Test
    void signUpMember() throws Exception{
        mockMvc.perform(post("/member/signUp").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(memberRequestDTO))).andExpect(status().isCreated());
    }
}
