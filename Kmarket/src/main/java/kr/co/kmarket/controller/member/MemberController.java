package kr.co.kmarket.controller.member;

import kr.co.kmarket.service.MemberService;
import kr.co.kmarket.vo.TermsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/member/join")
    public String join(){
        return "/member/join";
    }

    @GetMapping("/signup/{type}")
    public String signup(@PathVariable String type, Model model){
        TermsVO tvo = memberService.selectTerms();
        model.addAttribute("tvo", tvo);

            if("general".equals(type)){
                return "/member/signup";
            }

        return "/member/signupSeller";
    }

    @GetMapping("/register/{type}")
    public String register(@PathVariable String type){

            if("general".equals(type)){
                return "/member/register";
            }

        return "/member/registerSeller";
    }



}
