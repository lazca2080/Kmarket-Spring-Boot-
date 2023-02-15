package kr.co.kmarket.controller.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket.service.cs.IndexService;
import kr.co.kmarket.service.cs.QnaService;
import kr.co.kmarket.vo.CsVO;

@Controller
public class QnaController {
	@Autowired
	private QnaService service;
	
	@GetMapping("/cs/qna/list")
	public String list(String cateType1, String cateType2, String pg, Model model) {
		//List<CsVO> qna = service.selectArticles(cateType1, cateType2);
		
		return "/cs/qna/list";
	}
	
	@GetMapping("/cs/qna/view")
	public String view() {
		return "/cs/qna/view";
	}
	
	@GetMapping("/cs/qna/write")
	public String write() {
		return "/cs/qna/write";
	}
	
}
