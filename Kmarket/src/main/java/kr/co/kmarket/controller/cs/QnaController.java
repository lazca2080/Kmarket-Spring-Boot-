package kr.co.kmarket.controller.cs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kmarket.security.MyUserDetails;
import kr.co.kmarket.service.cs.IndexService;
import kr.co.kmarket.service.cs.QnaService;
import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class QnaController {
	@Autowired
	private QnaService service;
	
	@GetMapping("cs/qna/list")
	public String list(String cateType1, String pg, Model model) {
		//UserEntity user = myUser.getUser();
		
		int  currentPage = service.getCurrentPage(pg);
		int start        = service.getLimitStart(currentPage);
		int total       = service.getTotalCount(cateType1);
		int  lastPage    = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int[] groups     = service.getPageGroup(currentPage, lastPage);
		
		List<CsVO> qna = service.selectArticles(cateType1, start);
		
		//model.addAttribute("user", user);
		model.addAttribute("qna",qna);
		//model.addAttribute("cate",cate);
		model.addAttribute("cateType1",cateType1);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		
		log.info("dafdfa: "+qna);
		return "cs/qna/list";
	}
	
	@GetMapping("cs/qna/view")
	public String view(@RequestParam("no") int no, Model model, int pg) {
		CsVO qna = service.selectCs(no);
		
		model.addAttribute("qna",qna);
		model.addAttribute("pg",pg);
		log.info("adad : "+pg);
		return "cs/qna/view";
	}
	
	@GetMapping("cs/qna/write")
	public String write() {
		return "cs/qna/write";
	}
	
	@PostMapping("cs/qna/write")
	public String write(CsVO vo, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myUser) {
		vo.setUid("testTest");
		vo.setRegip(req.getRemoteAddr());
		
		int result = service.insertArticle(vo);
		
		return "redirect:/cs/qna/list";
	}
	
}
