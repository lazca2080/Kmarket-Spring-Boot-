package kr.co.kmarket.controller.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kmarket.entity.UserEntity;
import kr.co.kmarket.security.MyUserDetails;
import kr.co.kmarket.service.cs.IndexService;
import kr.co.kmarket.service.cs.NoticeService;
import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NoticeController {
	@Autowired
	private NoticeService service;
	

	@GetMapping("cs/notice/list")
	public String list(@AuthenticationPrincipal MyUserDetails myUser, Model model, String cateType1, String pg) {
		//UserEntity user = myUser.getUser();
		
		int  currentPage = service.getCurrentPage(pg);
		int start        = service.getLimitStart(currentPage);
		int total       = service.getTotalCount(cateType1);
		int  lastPage    = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int[] groups     = service.getPageGroup(currentPage, lastPage);
		
		//if(cateType1 == null) { cateType="notice"; }
		
		List<CsVO> notice = service.selectNoticeArticles(cateType1,start);
		
		//model.addAttribute("user", user);
		model.addAttribute("notice",notice);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum);
		model.addAttribute("groups", groups);
		
		log.info("sdf : "+ notice);
		log.info("sdf : "+ currentPage);
		log.info("sdf : "+ start);
		log.info("sdf : "+ total);
		log.info("sdf : "+ lastPage);
		log.info("sdf : "+ pageStartNum);
		log.info("sdf : "+ groups[0]);
		log.info("sdf : "+ groups[1]);
		
		
		return "cs/notice/list";
	}
	
	// 글 보기
	@GetMapping("cs/notice/view")
	public String view(@RequestParam("no") int no, Model model, Integer pg) {
		CsVO notice = service.selectNCs(no);
		
		model.addAttribute("notice",notice);
		model.addAttribute("pg", pg);
		//log.info("sdf : "+pg);
		return "cs/notice/view";
	}
}
