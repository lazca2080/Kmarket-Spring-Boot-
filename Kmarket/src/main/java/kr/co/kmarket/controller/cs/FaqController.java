package kr.co.kmarket.controller.cs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket.service.cs.IndexService;

@Controller
public class FaqController {
	@Autowired
	private IndexService service;
	
	@GetMapping("/cs/faq/list")
	public String list() {
		return "/cs/faq/list";
	}
	
	@GetMapping("/cs/faq/view")
	public String view() {
		return "/cs/faq/view";
	}
}
