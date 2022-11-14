package com.example.ec_mall.controller;

import com.example.ec_mall.dto.MemberRequestDTO;
import com.example.ec_mall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
   @RestController
    -> @Controller + @ResponseBody

   @Controller의 역할은 Model객체를 만들어 데이터를 담고 View를 찾는다.
   @RestController는 단순히 객체만을 반환하고 객체 데이터는 JSON 또는 XML 형식으로 HTTP응답에 담아서 전송.
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member",  consumes = "application/json")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<Object> signUpMember(@RequestBody @Valid MemberRequestDTO memberRequestDTO) {
        memberService.signUpMember(memberRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
