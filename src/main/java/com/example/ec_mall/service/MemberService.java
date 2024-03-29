package com.example.ec_mall.service;

import com.example.ec_mall.dao.MemberDao.UserDao;
import com.example.ec_mall.dao.MemberDao.RoleDao;
import com.example.ec_mall.dto.response.SignInResponseDto;
import com.example.ec_mall.dto.request.MemberRequestDTO.LoginDTO;
import com.example.ec_mall.dto.request.MemberRequestDTO.RequestDTO;
import com.example.ec_mall.exception.APIException;
import com.example.ec_mall.exception.ErrorCode;
import security.jwt.JwtTokenProvider;
import com.example.ec_mall.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public void signUpMember(RequestDTO memberRequestDTO) {
        UserDao member = UserDao.builder()
                .email(memberRequestDTO.getEmail())
                .nickName(memberRequestDTO.getNickName())
                .password(bCryptPasswordEncoder.encode(memberRequestDTO.getPassword()))
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
        RoleDao memberRole = RoleDao.builder()
                .accountId(member.getAccountId())
                .roles(memberRequestDTO.getRoles())
                .build();
        if(Objects.equals(memberRequestDTO.getRoles(), "SELLER")) {
            memberMapper.signUpRole(memberRole);
            memberRole.setRoles("USER");
            memberMapper.addRole(memberRole);
        }
        else memberMapper.signUpRole(memberRole);
    }

    private boolean isDuplicatedEmail(String email){
        return memberMapper.emailCheck(email) == 1;
    }

    /**
     * 로그인 API
     * @param loginDTO 회원가입시 입력한 email, password
     * @return
     *
     * 원본 문자열이 같더라도 매번 인코딩할 때 마다 결과 값이 달라지므로 기존 구현된 것과 달리 PasswordEncoder의 matches 메소드를 사용. (Jwt 적용)
     */

    public SignInResponseDto login(LoginDTO loginDTO){
        UserDao memberInfo = memberMapper.findByEmail(loginDTO.getEmail());

        if(!bCryptPasswordEncoder.matches(loginDTO.getPassword(), memberInfo.getPassword())){
            log.error("Invalid Login");
            throw new APIException(ErrorCode.NOT_FOUND_ACCOUNT);
        }
        /**
         * Authentication에 정보를 저장하고 다른 로직에서 불러서 쓰는 마치 Session과 같은 밑의 방식은
         * Spring Security에서 권장하는 정석적인 방법이다.
         *
         * 그러나 밑의 코드는 없어도 정상적으로 동작은 한다.
         * JWT는 기본적으로 Stateless하다. 즉, 요청이 들어올 때마다 필터에서 확인해주는 방식이기 때문이다.
         */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        return jwtTokenProvider.generateToken(authentication);
    }
}
