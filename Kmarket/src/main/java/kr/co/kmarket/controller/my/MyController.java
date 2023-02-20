package kr.co.kmarket.controller.my;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket.security.MyUserDetails;
import kr.co.kmarket.service.MyService;
import kr.co.kmarket.vo.CsVO;
import kr.co.kmarket.vo.MyVO;
import kr.co.kmarket.vo.OrderVO;
import kr.co.kmarket.vo.PointVO;
import kr.co.kmarket.vo.ReviewVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyController {
	
	@Autowired
	private MyService service;
	
	@GetMapping("my/home")
	public String home(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "home");
		
		List<OrderVO> order = service.selectMyHomeOrder();
		List<PointVO> point = service.selectMyHomePoint();
		List<ReviewVO> review = service.selectMyHomeReview();
		List<CsVO> cs = service.selectMyHomeCs();
		
		model.addAttribute("order", order);
		model.addAttribute("point", point);
		model.addAttribute("review", review);
		model.addAttribute("cs", cs);
		
		return "my/home";
	}
	
	@GetMapping("my/ordered")
	public String ordered(Model model) {
		model.addAttribute("type", "ordered");
		
		List<OrderVO> order = service.selectMyHomeOrder();
		model.addAttribute("order", order);
		
		return "my/ordered";
	}
	
	@GetMapping("my/point")
	public String point(Model model) {
		model.addAttribute("type", "point");
		
		return "my/point";
	}
	
	@GetMapping("my/coupon")
	public String coupon(Model model) {
		model.addAttribute("type", "coupon");
		
		return "my/coupon";
	}
	
	@GetMapping("my/review")
	public String review(Model model) {
		model.addAttribute("type", "review");
		
		return "my/review";
	}
	
	@GetMapping("my/qna")
	public String qna(Model model) {
		model.addAttribute("type", "qna");
		
		return "my/qna";
	}
	
	@GetMapping("my/info")
	public String info(Model model) {
		model.addAttribute("type", "info");
		
		return "my/info";
	}
}
