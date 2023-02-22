package kr.co.kmarket.controller.my;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.kmarket.security.MyUserDetails;
import kr.co.kmarket.service.MyService;
import kr.co.kmarket.vo.CsVO;
import kr.co.kmarket.vo.MemberVO;
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
		List<OrderVO> order = service.selectMyHomeOrder(uid, 0, "none");
		List<PointVO> point = service.selectMyHomePoint(uid, 0);
		List<ReviewVO> review = service.selectMyHomeReview(uid, 0);
		List<CsVO> cs = service.selectMyHomeCs(uid, 0);
		
		model.addAttribute("order", order);
		model.addAttribute("point", point);
		model.addAttribute("review", review);
		model.addAttribute("cs", cs);
		
		return "my/home";
	}
	
	@GetMapping("my/ordered")
	public String ordered(Model model, @AuthenticationPrincipal MyUserDetails myUser, String pg, String date) {
		model.addAttribute("type", "ordered");
		String uid = myUser.getUser().getUid();
		LocalDate now = LocalDate.now();
		// 현재 달
		int month = now.getMonthValue();
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalOrderCount(uid);
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		if(date == null) { date = "none"; }
		List<OrderVO> order = service.selectMyHomeOrder(uid, start, date);
		model.addAttribute("order", order);
		
		model.addAttribute("month", month);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
		
		return "my/ordered";
	}
	
	@GetMapping("my/point")
	public String point(Model model, @AuthenticationPrincipal MyUserDetails myUser, String pg) {
		model.addAttribute("type", "point");
		String uid = myUser.getUser().getUid();
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalPointCount(uid);
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		List<PointVO> point = service.selectMyHomePoint(uid, start);
		model.addAttribute("point", point);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
		
		return "my/point";
	}
	
	@GetMapping("my/coupon")
	public String coupon(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "coupon");
		
		String uid = myUser.getUser().getUid();
		
		return "my/coupon";
	}
	
	@GetMapping("my/review")
	public String review(Model model, @AuthenticationPrincipal MyUserDetails myUser, String pg) {
		model.addAttribute("type", "review");
		String uid = myUser.getUser().getUid();
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalReviewCount(uid);
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		List<ReviewVO> review = service.selectMyHomeReview(uid, start);
		model.addAttribute("review", review);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
		
		return "my/review";
	}
	
	@GetMapping("my/qna")
	public String qna(Model model, @AuthenticationPrincipal MyUserDetails myUser, String pg) {
		model.addAttribute("type", "qna");
		String uid = myUser.getUser().getUid();
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalCsCount(uid);
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		List<CsVO> cs = service.selectMyHomeCs(uid, start);
		
		model.addAttribute("qna", cs);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
		
		return "my/qna";
	}
	
	@GetMapping("my/info")
	public String info(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "info");
		
		String uid = myUser.getUser().getUid();
		
		return "my/info";
	}
	
	// 판매자 정보
	@ResponseBody
	@PostMapping("my/company")
	public Map<String, MemberVO> selectCompany(@RequestParam String company) {
		
		MemberVO vo = service.selectCompany(company);
		
		Map<String, MemberVO> map = new HashMap<>();
		map.put("company", vo);
		
		return map;
	}
	
	// 주문 상세
	@ResponseBody
	@PostMapping("my/order")
	public Map<String, MyVO> selectOrder(@RequestParam int ordNo) {
		
		MyVO vo = service.selectOrder(ordNo);
		
		Map<String, MyVO> map = new HashMap<>();
		map.put("vo", vo);
		
		return map;
	}
	
	// 리뷰 작성
	@ResponseBody
	@PostMapping("my/review")
	public Map<String, Integer> insertReview(@RequestBody ReviewVO vo, @AuthenticationPrincipal MyUserDetails myUser, HttpServletRequest req) {
		vo.setUid(myUser.getUser().getUid());
		vo.setRegip(req.getRemoteAddr());
		int result = 0;
		
		int revStatus = service.selectRevStatus(myUser.getUser().getUid(), vo.getProdNo());
		
		if(revStatus == 1) {
			result = 0;
		}else if(revStatus == 0) {
			result = service.insertReview(vo);
			service.updateRevStatus(myUser.getUser().getUid(), vo.getProdNo());
		}
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	// 전체주문내역 날짜 검색
	@ResponseBody
	@PostMapping("my/searchDate")
	public void searchDateOrdered(@RequestBody Map<String, String> map, @AuthenticationPrincipal MyUserDetails myUser, String pg, Model model) {
		map.put("uid", myUser.getUser().getUid());
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalOrderCount(myUser.getUser().getUid());
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		List<OrderVO> order = service.searchDateOrdered(map);
		model.addAttribute("order", order);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
	}
	
	// 포인트내역 날짜 검색
	@ResponseBody
	@PostMapping("my/searchDatePoint")
	public void searchDatePoint(@RequestBody Map<String, String> map, @AuthenticationPrincipal MyUserDetails myUser) {
		map.put("uid", myUser.getUser().getUid());
		
		service.searchDateOrdered(map);
	}
	
}
