package com.project.memberproject.controller;

import com.project.memberproject.dto.MemberDTO;
import com.project.memberproject.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String savePage() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        System.out.println("memberDTO.getMemberEmail() = " + memberDTO.getMemberEmail());
        System.out.println("memberDTO.getMemberName() = " + memberDTO.getMemberName());
        System.out.println("memberDTO.getMemberPassword() = " + memberDTO.getMemberPassword());
        memberService.save(memberDTO);
        return "login";
    }

    @GetMapping("/member/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession httpSession) {
        // HttpSession은 서버 측에서 클라이언트에 대한 정보를 저장하고 관리하는 객체.
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // 성공적으로 로그인한 후 해당 사용자의 이메일 정보를 HttpSession에 저장하는 역할.
            httpSession.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            return "login";
        }
    }
}
