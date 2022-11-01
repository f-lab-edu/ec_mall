package com.example.ec_mall.service;

import com.example.ec_mall.dto.MemberDTO;
import com.example.ec_mall.error.DuplicateEmailException;
import com.example.ec_mall.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void regMember(MemberDTO memberDTO){

        //email 중복체크
        boolean dupCheckEmail = isDuplicatedEmail(memberDTO.getEmail());
        if(dupCheckEmail){
            throw new DuplicateEmailException("중복된 Email입니다." + memberDTO.getEmail());
        }

        int regCount = memberMapper.regMember(memberDTO);

        //System.out.println(regCount);

        if(regCount != 1){
            System.out.println("registration ERROR! { "+ memberDTO.getNickName() +" }");
            throw new RuntimeException("회원가입 메소드 확인\n" + memberDTO.getNickName());
        }
    }

    public boolean isDuplicatedEmail(String email){
        return memberMapper.emailCheck(email) == 1;
    }
}
