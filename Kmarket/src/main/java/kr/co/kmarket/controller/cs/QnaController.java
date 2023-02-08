package kr.co.kmarket.controller.cs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QnaController {
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
