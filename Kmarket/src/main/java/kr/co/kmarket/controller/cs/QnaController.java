package kr.co.kmarket.controller.cs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket.service.cs.IndexService;

@Controller
public class QnaController {
	@Autowired
	private IndexService service;
	
	@GetMapping("/cs/qna/list")
	public String list() {
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
