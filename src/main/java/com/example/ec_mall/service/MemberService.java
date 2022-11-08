package com.example.ec_mall.service;

import com.example.ec_mall.dao.MemberDao;
import com.example.ec_mall.dto.MemberRequestDTO;
import com.example.ec_mall.error.DuplicateEmailException;
import com.example.ec_mall.mapper.MemberMapper;
import com.example.ec_mall.util.SHA256;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberMapper memberMapper;

    public void signUpMember(MemberRequestDTO memberRequestDTO) {
        MemberDao member = MemberDao.builder()
                .email(memberRequestDTO.getEmail())
                .nickName(memberRequestDTO.getNickName())
                .password(memberRequestDTO.getPassword())
                .createdBy(memberRequestDTO.getEmail())
                .build();

        //email 중복체크
        boolean dupCheckEmail = isDuplicatedEmail(member.getEmail());
        if(dupCheckEmail){
            throw new DuplicateEmailException("중복된 Email입니다." + member.getEmail());
        }
        //Password SHA256 암호화
        member.setPassword(SHA256.encrypt(memberRequestDTO.getPassword()));

        int signUpCount = memberMapper.signUpMember(member);

        if(signUpCount != 1){
            log.error("registration ERROR! {}", member);
            throw new RuntimeException("회원가입 메소드 확인\n" + member.getNickName());
        }
    }

    private boolean isDuplicatedEmail(String email){
        return memberMapper.emailCheck(email) == 1;
    }
}
