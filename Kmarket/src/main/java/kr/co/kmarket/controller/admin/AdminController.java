package kr.co.kmarket.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmarket.service.AdminService;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	// 상품 목록 페이지
	@GetMapping(value = { "admin", "admin/index"})
	public String index() {
		return "admin/index";
	}

	// 상품 목록 페이지
	@GetMapping("admin/list")
	public String list() {
		return "admin/list";
	}
	
	// 상품 등록 페이지
	@GetMapping("admin/register")
	public String register(Model model) {
		
		// 카테고리 분류
		List<CateVO> cate1 = service.selectCate1();
		model.addAttribute("cate", cate1);
		
		return "admin/register";
	}
	
	// 상품 등록
	@PostMapping("admin/register")
	public String register(ProductVO vo, HttpServletRequest req) {
		vo.setSeller("admin");
		vo.setIp(req.getRemoteAddr());
		
		service.register(vo);
		
		return "redirect:/admin/register";
	}
	
	
	// 카테고리 분류
	@ResponseBody
	@GetMapping("admin/register/selectCate2")
	public Map<String, List<CateVO>> selectCate2(@RequestParam String cate1) {
		List<CateVO> cate2 = service.selectCate2(cate1);
		
		Map<String, List<CateVO>> map = new HashMap<>();
		map.put("result", cate2);
		
		return map;
	};
}
