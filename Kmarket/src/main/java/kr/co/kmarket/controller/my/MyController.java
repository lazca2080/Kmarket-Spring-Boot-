package kr.co.kmarket.controller.my;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
	
	@GetMapping("my/home")
	public String home(Model model) {
		model.addAttribute("type", "home");
		return "layouts/myLayout";
	}
	
	@GetMapping("my/ordered")
	public String ordered(Model model) {
		model.addAttribute("type", "ordered");
		
		return "layouts/myLayout";
	}
	
	@GetMapping("my/point")
	public String point(Model model) {
		model.addAttribute("type", "point");
		
		return "layouts/myLayout";
	}
	
	@GetMapping("my/coupon")
	public String coupon(Model model) {
		model.addAttribute("type", "coupon");
		
		return "my/coupon";
	}
	
	@GetMapping("my/review")
	public String review(Model model) {
		model.addAttribute("type", "review");
		
		return "layouts/myLayout";
	}
	
	@GetMapping("my/qna")
	public String qna(Model model) {
		model.addAttribute("type", "qna");
		
		return "layouts/myLayout";
	}
	
	@GetMapping("my/info")
	public String info(Model model) {
		model.addAttribute("type", "info");
		
		return "layouts/myLayout";
	}
}
