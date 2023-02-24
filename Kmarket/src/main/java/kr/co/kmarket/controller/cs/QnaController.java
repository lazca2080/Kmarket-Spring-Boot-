package kr.co.kmarket.controller.cs;

import java.lang.ProcessHandle.Info;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String list(@AuthenticationPrincipal MyUserDetails myUser, Model model, String cateType1, String pg) {
		//UserEntity user = myUser.getUser();
		
		int  currentPage = service.getCurrentPage(pg);
		int start        = service.getLimitStart(currentPage);
		int total       = service.getTotalCount(cateType1);
		int  lastPage    = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int[] groups     = service.getPageGroup(currentPage, lastPage);
		
		List<CsVO> qna = service.selectQnaArticles(cateType1, start);
		
		
		
		//model.addAttribute("user", user);
		model.addAttribute("qna",qna);
		model.addAttribute("cateType1",cateType1);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		
		//log.info("zxcv : "+ qna);
		//log.info("cateType1 : "+ cateType1);
		//log.info("zxcv : "+ currentPage);
		//log.info("zxcv : "+ lastPage);
		//log.info("zxcv : "+ pageStartNum);
		//log.info("zxcv : "+ groups[0]);
		
		
		return "cs/qna/list";
	}
	
	@GetMapping("cs/qna/view")
	public String view(@RequestParam("no") Integer no, Model model, Integer pg, String cateType1) {
		CsVO qna = service.selectQCs(no);
		
		model.addAttribute("qna",qna);
		model.addAttribute("cateType1",cateType1);
		model.addAttribute("pg",pg);
		//log.info("adad : "+qna);
		return "cs/qna/view";
	}
	
	@GetMapping("cs/qna/write")
	public String write(Model model, CsVO vo) {
		model.addAttribute("vo", vo);
		return "cs/qna/write";
	}
	
	@PostMapping("cs/qna/write")
	public String write(CsVO vo, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myUser) {
		
		vo.setRegip(req.getRemoteAddr());
		
		service.QinsertArticle(vo);
		
		
		log.info("aasdf :" +vo);
		return "redirect:/cs/qna/list";
	}
	
}
