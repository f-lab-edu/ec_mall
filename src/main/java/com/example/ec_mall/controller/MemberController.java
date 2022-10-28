package com.example.ec_mall.controller;

import com.example.ec_mall.dto.MemberDTO;
import com.example.ec_mall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/regMember")
    public String regMember(Model model){
        model.addAttribute("title", "회원가입");

        return "/";
    }

    @PostMapping("/regMember")
    public String regMember(MemberDTO memberDTO){

        memberService.regMember(memberDTO);
        return "redirect:/";

    }
}
