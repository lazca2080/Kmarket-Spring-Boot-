package kr.co.kmarket.controller.member;

import kr.co.kmarket.service.MemberService;
import kr.co.kmarket.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class RegisterController {

    @Autowired
    private MemberService memberService;


    @PostMapping("/register/{type}")
    public String insertMember(@PathVariable("type") String type, MemberVO memberVO){

        if("general".equals(type)){
            memberService.insertMember(memberVO);
        }else if("seller".equals(type)){
            memberService.insertMemberSeller(memberVO);
        }


        return "redirect:/member/login";
    }

    @GetMapping("/member/uidCheck")
    @ResponseBody
    public Map<String, Integer> checkUid(@RequestParam("uid") String uid){
        log.info(" uid : "+ uid);
        Integer result = memberService.selectCheckUid(uid);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        log.info("resultMap"+ resultMap);
        return resultMap;
    }

}
