package kr.co.kmarket.controller.member;

import kr.co.kmarket.entity.UserEntity;
import kr.co.kmarket.security.MyUserDetails;
import kr.co.kmarket.service.MemberService;
import kr.co.kmarket.vo.TermsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("member/join")
    public String join(){
        return "member/join";
    }

    @GetMapping("signup/{type}")
    public String signup(@PathVariable String type, Model model){
        TermsVO tvo = memberService.selectTerms();
        model.addAttribute("tvo", tvo);

            if("general".equals(type)){
                return "member/signup";
            }

        return "member/signupSeller";
    }

    @GetMapping("register/{type}")
    public String register(@PathVariable String type){

            if("general".equals(type)){
                return "member/register";
            }

        return "member/registerSeller";
    }

    @GetMapping("member/login")
    public String login(Principal principal){

        if(principal != null){
            return "redirect:/";
        }else{
            return "member/login";
        }


    }


    /* ========= Security Test... ========== */

    @GetMapping("member/loginInfo")
    public String loginInfo(@AuthenticationPrincipal MyUserDetails myUser, Model m){
        UserEntity user =  myUser.getUser();
        log.info("user : "+ user);
        m.addAttribute("user", user);
        return "member/loginInfo";
    }
}
