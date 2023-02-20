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
		
     	String uid = myUser.getUser().getUid();
		List<OrderVO> order = service.selectMyHomeOrder(uid);
		List<PointVO> point = service.selectMyHomePoint(uid);
		List<ReviewVO> review = service.selectMyHomeReview(uid);
		List<CsVO> cs = service.selectMyHomeCs(uid);
		
		model.addAttribute("order", order);
		model.addAttribute("point", point);
		model.addAttribute("review", review);
		model.addAttribute("cs", cs);
		
		return "my/home";
	}
	
	@GetMapping("my/ordered")
	public String ordered(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "ordered");
		
		String uid = myUser.getUser().getUid();
		
		List<OrderVO> order = service.selectMyHomeOrder(uid);
		model.addAttribute("order", order);
		
		return "my/ordered";
	}
	
	@GetMapping("my/point")
	public String point(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "point");
		
		String uid = myUser.getUser().getUid();
		
		List<PointVO> point = service.selectMyHomePoint(uid);
		model.addAttribute("point", point);
		
		return "my/point";
	}
	
	@GetMapping("my/coupon")
	public String coupon(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "coupon");
		
		String uid = myUser.getUser().getUid();
		
		return "my/coupon";
	}
	
	@GetMapping("my/review")
	public String review(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "review");
		
		String uid = myUser.getUser().getUid();
		
		List<ReviewVO> review = service.selectMyHomeReview(uid);
		model.addAttribute("review", review);
		
		return "my/review";
	}
	
	@GetMapping("my/qna")
	public String qna(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "qna");
		
		String uid = myUser.getUser().getUid();
		
		List<CsVO> cs = service.selectMyHomeCs(uid);
		model.addAttribute("qna", cs);
		
		return "my/qna";
	}
	
	@GetMapping("my/info")
	public String info(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "info");
		
		String uid = myUser.getUser().getUid();
		
		return "my/info";
	}
}
