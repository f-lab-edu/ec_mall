package com.example.ec_mall.service;

import com.example.ec_mall.dao.MemberDao;
import com.example.ec_mall.dto.jwt.TokenDto;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.dto.request.MemberRequestDTO.RequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import com.example.ec_mall.jwt.JwtTokenProvider;
import com.example.ec_mall.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public void signUpMember(RequestDTO memberRequestDTO) {
        MemberDao member = MemberDao.builder()
                .email(memberRequestDTO.getEmail())
                .nickName(memberRequestDTO.getNickName())
                .password(bCryptPasswordEncoder.encode(memberRequestDTO.getPassword()))
                .createdBy(memberRequestDTO.getEmail())
                //처음 가입시에는 USER 권한으로 가입, 추후에 상품등록, 수정 할 수 있는 권한 추가
                .roles("USER")
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
     * @param loginDTO 회원가입시 입력한 email, password
     * @return
     *
     * 아이디 혹은 비밀번호가 잘못되어 로그인 실패시 아이디가 틀렸는지 비밀번호가 틀렸는지 알려주지 않는다.
     * 그래서 sql이 실패하면 Null값으로 분기처리.
     */

    public TokenDto login(LoginDTO loginDTO){
//        String encryptPassword = bCryptPasswordEncoder.encode(loginDTO.getPassword());
//        MemberDao memberInfo = memberMapper.findByEmailPassword(loginDTO.getEmail(),encryptPassword);
//        if(memberInfo == null){
//            log.error("Invalid Login");
//            throw new APIException(ErrorCode.NOT_FOUND_ACCOUNT);
//        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);

        return tokenDto;
    }
}
