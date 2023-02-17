package kr.co.kmarket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.kmarket.service.ProductService;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping(value = {"", "index"})
	public String index(Model model) {
		model.addAttribute("category", "main");
		
		// 인덱스 상품 분류
		Map<String, List<ProductVO>> index = service.selectIndex();
		model.addAttribute("index", index);
		
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		return "index";
	}
}
