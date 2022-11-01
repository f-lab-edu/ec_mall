package com.example.ec_mall.service;

import com.example.ec_mall.dto.MemberDTO;
import com.example.ec_mall.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;



import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberMapper memberMapper;


    public MemberDTO addMember() {
        MemberDTO member = new MemberDTO();

        member.setId(1L);
        member.setNickName("Test");
        member.setEmail("test@test.com");
        member.setPassword("1234");
        member.setCreatedDate(LocalDateTime.now());

        return member;
    }

    @Test
    public void addMember_성공(){
        MemberDTO member = addMember();
        given(memberMapper.regMember(member)).willReturn(1);
        memberService.regMember(member);
    }

    @Test
    public void addMember_실패(){
        MemberDTO member = addMember();
        given(memberMapper.regMember(member)).willReturn(0);
        memberService.regMember(member);
    }

    @Test
    public void addMember_EmailCheck(){
        MemberDTO member = addMember();
        given(memberMapper.emailCheck(member.getEmail())).willReturn(1);
        memberService.regMember(member);
    }

}
