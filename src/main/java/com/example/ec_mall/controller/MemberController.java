package com.example.ec_mall.controller;

import com.example.ec_mall.dao.MemberDao;
import com.example.ec_mall.dto.request.MemberRequestDTO;
import com.example.ec_mall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
   @RestController
    -> @Controller + @ResponseBody

   @Controller의 역할은 Model객체를 만들어 데이터를 담고 View를 찾는다.
   @RestController는 단순히 객체만을 반환하고 객체 데이터는 JSON 또는 XML 형식으로 HTTP응답에 담아서 전송.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member",  consumes = "application/json")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<MemberRequestDTO> signUpMember(@RequestBody @Valid MemberRequestDTO.RequestDTO memberRequestDTO) {
        memberService.signUpMember(memberRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 로그인 (Email, Password)
     * @param memberLoginDTO : Email, Password
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<MemberDao> login(@RequestBody @Valid MemberRequestDTO.LoginDTO memberLoginDTO, HttpSession session){
        memberService.login(memberLoginDTO.getEmail(), memberLoginDTO.getPassword());
        session.setAttribute("account", memberLoginDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session){
        session.removeAttribute("account");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}