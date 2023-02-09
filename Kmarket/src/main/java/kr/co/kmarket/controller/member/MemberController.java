package kr.co.kmarket.controller.member;

import kr.co.kmarket.service.MemberService;
import kr.co.kmarket.vo.TermsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/member/join")
    public String join(){
        return "/member/join";
    }

    @GetMapping("/member/signup")
    public String signup(Model model){
        TermsVO tvo = memberService.selectTerms();
        model.addAttribute("tvo", tvo);
        return "/member/signup";
    }

    @GetMapping("/member/signupSeller")
    public String signupSeller(Model model){
        TermsVO tvo = memberService.selectTerms();
        model.addAttribute("tvo", tvo);
        return "/member/signupSeller";
    }

    @GetMapping("/member/register")
    public String register(){
        return "/member/register";
    }

    @GetMapping("/member/registerSeller")
    public String registerSeller(){
        return "/member/registerSeller";
    }

}
