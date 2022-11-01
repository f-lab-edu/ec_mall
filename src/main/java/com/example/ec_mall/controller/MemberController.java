package com.example.ec_mall.controller;

import com.example.ec_mall.dto.MemberDTO;
import com.example.ec_mall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "member", method= RequestMethod.POST, consumes = "application/json")
public class MemberController {

    private final MemberService memberService;
/*
    @GetMapping("/regMember")
    public String regMember(Model model){
        model.addAttribute("title", "회원가입");

        return "/";
    }
*/
    @PostMapping("signUp")
    public String regMember(@RequestBody @Valid MemberDTO memberDTO){

        memberService.regMember(memberDTO);
        return "redirect:/";

    }
}
