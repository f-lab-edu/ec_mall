package com.example.ec_mall.service;

import com.example.ec_mall.dto.MemberRequestDTO;
import com.example.ec_mall.error.DuplicateEmailException;
import com.example.ec_mall.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void signUpMember(MemberRequestDTO memberRequestDTO){

        //email 중복체크
        boolean dupCheckEmail = isDuplicatedEmail(memberRequestDTO.getEmail());
        if(dupCheckEmail){
            throw new DuplicateEmailException("중복된 Email입니다." + memberRequestDTO.getEmail());
        }

        int signUpCount = memberMapper.signUpMember(memberRequestDTO);

        System.out.println(signUpCount);

        if(signUpCount != 1){
            System.out.println("registration ERROR! { "+ memberRequestDTO.getNickName() +" }");
            throw new RuntimeException("회원가입 메소드 확인\n" + memberRequestDTO.getNickName());
        }
    }

    private boolean isDuplicatedEmail(String email){
        return memberMapper.emailCheck(email) == 1;
    }
}
