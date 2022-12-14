package com.example.ec_mall.service;

import com.example.ec_mall.dao.MemberDao;
import com.example.ec_mall.dto.request.MemberRequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
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

    public void signUpMember(MemberRequestDTO.RequestDTO memberRequestDTO) {
        MemberDao member = MemberDao.builder()
                .email(memberRequestDTO.getEmail())
                .nickName(memberRequestDTO.getNickName())
                //SHA256 암호화
                .password(SHA256.encrypt(memberRequestDTO.getPassword()))
                .createdBy(memberRequestDTO.getEmail())
                .build();

        /**
         * Log 레벨
         * trace < debug < info < warn < error
         * 오른쪽으로 갈수록 심각한 오류
         *
         * 하위 레벨의 로그는 상위 레벨의 로그를 포함
         * ex) debug로 설정시 info, warn, error 로그를 포함하여 출력
         */
        //email 중복체크
        boolean dupCheckEmail = isDuplicatedEmail(member.getEmail());
        if(dupCheckEmail){
            log.error("DuplicatedEmail, {}", member.getEmail());
            throw new APIException(ErrorCode.ALREADY_SAVED_EMAIL);
        }

        int signUpCount = memberMapper.signUpMember(member);

        if(signUpCount != 1){
            log.error("registration ERROR! {}", member);
            throw new RuntimeException("회원가입 메소드 확인\n" + member);
        }
    }

    private boolean isDuplicatedEmail(String email){
        return memberMapper.emailCheck(email) == 1;
    }

    /**
     * 로그인 API
     * @param email 회원가입시 입력한 email
     * @param password 회원가입시 입력한 password
     * @return
     *
     * 아이디 혹은 비밀번호가 잘못되어 로그인 실패시 아이디가 틀렸는지 비밀번호가 틀렸는지 알려주지 않는다.
     * 그래서 sql이 실패하면 Null값으로 분기처리.
     */

    public void login(String email, String password){
        String encryptPassword = SHA256.encrypt(password);
        MemberDao memberInfo = memberMapper.findByEmailPassword(email,encryptPassword);
        if(memberInfo == null){
            log.error("Invalid Login");
            throw new APIException(ErrorCode.NOT_FOUND_ACCOUNT);
        }
    }
}
