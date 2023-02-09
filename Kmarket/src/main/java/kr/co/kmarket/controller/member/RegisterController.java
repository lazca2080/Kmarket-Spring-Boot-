package kr.co.kmarket.controller.member;

import kr.co.kmarket.service.MemberService;
import kr.co.kmarket.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegisterController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/member/register")
    public String insertMember(MemberVO memberVO){
        memberService.insertMember(memberVO);

        return "redirect:/member/login";
    }

}
