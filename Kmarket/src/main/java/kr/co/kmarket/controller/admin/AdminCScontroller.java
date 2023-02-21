package kr.co.kmarket.controller.admin;

import kr.co.kmarket.service.AdminCSservice;
import kr.co.kmarket.service.AdminService;
import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
public class AdminCScontroller {
    @Autowired
    private AdminCSservice service;

    /** ============= 관리자 고객센터 ================= **/
    @GetMapping("admin/cs/list/{csType}")
    public String csList(@PathVariable("csType")String csType, String pg, Model model){

        // admin-aside에서 고객센터 타입 구분
        if("notice".equals(csType)){
            int total = service.selectAdminNoticeTotal();
            int currentPage = service.getCurrentPage(pg);
            int start = service.getLimitStart(currentPage);
            int lastPage = service.getLastPageNum(total);
            int pageStartNum = service.getPageStartNum(total, start);
            int groups[] = service.getPageGroup(currentPage, lastPage);

            List<CsVO> articles = service.selectAdminCSnotice(start);
            log.info("total : " + total);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("lastPage", lastPage);
            model.addAttribute("pageStartNum", pageStartNum);
            model.addAttribute("groups", groups);
            model.addAttribute("articles", articles);
            return "admin/cs/notice/list";

        }else if("qna".equals(csType)) {
            int total = service.selectAdminQnaTotal();
            int currentPage = service.getCurrentPage(pg);
            int start = service.getLimitStart(currentPage);
            int lastPage = service.getLastPageNum(total);
            int pageStartNum = service.getPageStartNum(total, start);
            int groups[] = service.getPageGroup(currentPage, lastPage);

            List<CsVO> articles = service.selectAdminCSqna(start);
            log.info("total : " + total);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("lastPage", lastPage);
            model.addAttribute("pageStartNum", pageStartNum);
            model.addAttribute("groups", groups);
            model.addAttribute("articles", articles);
            return "admin/cs/qna/list";
        }else{
            int total = service.selectAdminFaqTotal();
            int currentPage = service.getCurrentPage(pg);
            int start = service.getLimitStart(currentPage);
            int lastPage = service.getLastPageNum(total);
            int pageStartNum = service.getPageStartNum(total, start);
            int groups[] = service.getPageGroup(currentPage, lastPage);

            List<CsVO> articles = service.selectAdminCSfaq(start);
            log.info("total : " + total);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("lastPage", lastPage);
            model.addAttribute("pageStartNum", pageStartNum);
            model.addAttribute("groups", groups);
            model.addAttribute("articles", articles);
            return "admin/cs/faq/list";
        }
    }

    @GetMapping("admin/cs/view/{csType}/{no}")
    public String csView(@PathVariable("csType")String csType,
                         @PathVariable("no")int no,
                         Model m){

        if("notice".equals(csType)){
            CsVO cvo = service.noticeSelectOne(no);
            m.addAttribute("cvo", cvo);
            return "admin/cs/notice/view";

        }else if("faq".equals(csType)){
            CsVO cvo = service.faqSelectOne(no);
            m.addAttribute("cvo", cvo);
            return "admin/cs/faq/view";
        }else{
            CsVO cvo = service.qnaSelectOne(no);
            m.addAttribute("cvo", cvo);
            return "admin/cs/qna/view";
        }
    }

    @GetMapping("admin/cs/{writeType}")
    public String csWrite(@PathVariable("writeType")String writeType){
        if("noticeWrite".equals(writeType)){
            return "admin/cs/notice/write";
        }else{
            return "admin/cs/faq/write";
        }
    }

    @PostMapping("admin/cs/{writeType}")
    public String insertWrite(@PathVariable("writeType")String writeType, CsVO vo, HttpServletRequest req){
        vo.setRegip(req.getRemoteAddr());

        if("notice".equals(writeType)){
            service.insertArticleNotice(vo);
            return "redirect:/admin/cs/list/notice";
        }else{
            service.insertArticleFaq(vo);
        }
        return "redirect:/admin/cs/list/faq";
    }

    @GetMapping("admin/cs/modify/{csType}/{no}")
    public String modify(@PathVariable("csType")String csType,
                         @PathVariable("no")int no,
                         Model m){
        if("notice".equals(csType)){
            CsVO cvo = service.noticeSelectOne(no);
            m.addAttribute("cvo", cvo);
            return "admin/cs/notice/modify";
        }else{
            CsVO cvo = service.faqSelectOne(no);
            m.addAttribute("cvo", cvo);
            return "admin/cs/faq/modify";
        }
    }
}
