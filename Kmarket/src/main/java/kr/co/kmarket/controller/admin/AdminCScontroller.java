package kr.co.kmarket.controller.admin;

import kr.co.kmarket.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminCScontroller {
    @Autowired
    private AdminService service;

    /** ============= 관리자 고객센터 ================= **/
    @GetMapping("admin/cs/list/{csType}")
    public String csList(@PathVariable("csType")String csType){

        // admin-aside에서 고객센터 타입 구분
        if("notice".equals(csType)){

            return "admin/cs/notice/list";

        }else if("qna".equals(csType)) {

            return "admin/cs/qna/list";
        }

        return "admin/cs/faq/list";
    }
}
