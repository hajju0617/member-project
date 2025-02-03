package com.project.memberproject.controller;

import com.project.memberproject.dto.MemberDTO;
import com.project.memberproject.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            // 성공적으로 로그인이 됐다면 해당 사용자의 이메일 정보를 HttpSession에 저장하는 역할.
            httpSession.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            return "login";
        }
    }

    @GetMapping("/member")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // View 템플릿에서 사용하기 위해서 Model 객체에 'memberList' 라는 이름으로 저장.
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        // DB에서 데이터를 조회한 후 그 값을 다시 View에 넘겨주기 위해서 Model 객체 필요함.
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession httpSession, Model model) {
        // 세션에 있는 유저의 이메일 값을 통해 해당 유저의 전체 정보를 조회하고 이걸 Model 객체에 담아서 View로 전달.
        String myEmail = (String) httpSession.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        // update 메서드가 끝나고 다시 다른 컨트롤러 메서드로 요청을 보내도록 하는 것 : 리다이렉트.
        // 리다이렉트 하는 이유 : 그냥 return "detail";하면 아무런 값을 전달하지않아서 뷰페이지에 값이 출력되지 않음.
        return "redirect:/member/" + memberDTO.getId();
    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);
//        return "list"; 하게 되면 데이터 없이 뷰템플릿 껍데기만 나옴.
        return "redirect:/member";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession httpSession) {
        // 세션 무효화.
        httpSession.invalidate();
        return "index";
    }

    @PostMapping("/member/email-check")
    @ResponseBody
    public String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        String checkResult = memberService.emailCheck(memberEmail);
        if (checkResult != null) {
            return "ok";
        } else {
            return "중복";
        }
    }
}
