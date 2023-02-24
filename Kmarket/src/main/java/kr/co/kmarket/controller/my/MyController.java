package kr.co.kmarket.controller.my;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
		
		Map<String, String> dateRange = new HashMap<>();
		
     	String uid = myUser.getUser().getUid();
		List<OrderVO> order = service.selectMyHomeOrder(uid, 0, null, null, dateRange);
		List<PointVO> point = service.selectMyHomePoint(uid, 0, null, null, dateRange);
		List<ReviewVO> review = service.selectMyHomeReview(uid, 0);
		List<CsVO> cs = service.selectMyHomeCs(uid, 0);
		
		
		long csCount = service.getTotalCsCount(uid);
		int orderCount = service.selectOrderTotal(uid);
		model.addAttribute("ordCount", orderCount);
		model.addAttribute("csCount", csCount);
		model.addAttribute("order", order);
		model.addAttribute("point", point);
		model.addAttribute("review", review);
		model.addAttribute("cs", cs);
		
		return "my/home";
	}
	
	@GetMapping("my/ordered")
	public String ordered(Model model, @AuthenticationPrincipal MyUserDetails myUser, String pg, String date, String rangeMonth, String rangeBegin, String rangeEnd, String types) {
		model.addAttribute("type", "ordered");
		String uid = myUser.getUser().getUid();
		int orderCount = service.selectOrderTotal(uid);
		model.addAttribute("ordCount", orderCount);
		
		// 날짜 구하기 ---------------------------------------------------------------------------
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		
		LocalDate before7 = now.minusDays(7);
		LocalDate before15 = now.minusDays(15);
		LocalDate before1 = now.minusMonths(1);
		model.addAttribute("week", before7.toString());
		model.addAttribute("weeks", before15.toString());
		model.addAttribute("months", before1.toString());
		
		Map<String, String> dateRange = new HashMap<>();
		if(rangeBegin != null && rangeEnd != null) {
			dateRange.put("begin", rangeBegin);
			dateRange.put("end", rangeEnd);
		}
		model.addAttribute("rangeBegin", rangeBegin);
		model.addAttribute("rangeEnd", rangeEnd);
		// 날짜 구하기 ---------------------------------------------------------------------------
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalOrderCount(uid, date, rangeMonth, dateRange);
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		List<OrderVO> order = service.selectMyHomeOrder(uid, start, date, rangeMonth, dateRange);
		model.addAttribute("order", order);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
		model.addAttribute("rangeMonth", rangeMonth);
		model.addAttribute("date", date);
		model.addAttribute("types", types);
		
		long csCount = service.getTotalCsCount(uid);
		model.addAttribute("csCount", csCount);
		
		return "my/ordered";
	}
	
	@GetMapping("my/point")
	public String point(Model model, @AuthenticationPrincipal MyUserDetails myUser, String pg, String date, String rangeMonth, String rangeBegin, String rangeEnd, String types) {
		model.addAttribute("type", "point");
		String uid = myUser.getUser().getUid();
		int orderCount = service.selectOrderTotal(uid);
		model.addAttribute("ordCount", orderCount);
		
		// 날짜 구하기 ---------------------------------------------------------------------------
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		
		LocalDate before7 = now.minusDays(7);
		LocalDate before15 = now.minusDays(15);
		LocalDate before1 = now.minusMonths(1);
		model.addAttribute("week", before7.toString());
		model.addAttribute("weeks", before15.toString());
		model.addAttribute("months", before1.toString());
		
		Map<String, String> dateRange = new HashMap<>();
		if(rangeBegin != null && rangeEnd != null) {
			dateRange.put("begin", rangeBegin);
			dateRange.put("end", rangeEnd);
		}
		model.addAttribute("rangeBegin", rangeBegin);
		model.addAttribute("rangeEnd", rangeEnd);
		// 날짜 구하기 ---------------------------------------------------------------------------
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalPointCount(uid, date, rangeMonth, dateRange);
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		List<PointVO> point = service.selectMyHomePoint(uid, start, date, rangeMonth, dateRange);
		model.addAttribute("point", point);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
		model.addAttribute("rangeMonth", rangeMonth);
		model.addAttribute("date", date);
		model.addAttribute("types", types);
		
		long csCount = service.getTotalCsCount(uid);
		model.addAttribute("csCount", csCount);
		
		return "my/point";
	}
	
	@GetMapping("my/coupon")
	public String coupon(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "coupon");
		int orderCount = service.selectOrderTotal(myUser.getUser().getUid());
		model.addAttribute("ordCount", orderCount);
		
		String uid = myUser.getUser().getUid();
		
		long csCount = service.getTotalCsCount(uid);
		model.addAttribute("csCount", csCount);
		
		return "my/coupon";
	}
	
	@GetMapping("my/review")
	public String review(Model model, @AuthenticationPrincipal MyUserDetails myUser, String pg) {
		model.addAttribute("type", "review");
		String uid = myUser.getUser().getUid();
		int orderCount = service.selectOrderTotal(uid);
		model.addAttribute("ordCount", orderCount);
		
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
		
		long csCount = service.getTotalCsCount(uid);
		model.addAttribute("csCount", csCount);
		
		return "my/review";
	}
	
	@GetMapping("my/qna")
	public String qna(Model model, @AuthenticationPrincipal MyUserDetails myUser, String pg) {
		model.addAttribute("type", "qna");
		String uid = myUser.getUser().getUid();
		int orderCount = service.selectOrderTotal(uid);
		model.addAttribute("ordCount", orderCount);
		
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
		
		long csCount = service.getTotalCsCount(uid);
		model.addAttribute("csCount", csCount);
		
		return "my/qna";
	}
	
	@GetMapping("my/info")
	public String info(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		model.addAttribute("type", "info");
		int orderCount = service.selectOrderTotal(myUser.getUser().getUid());
		model.addAttribute("ordCount", orderCount);
		
		String uid = myUser.getUser().getUid();
		
		long csCount = service.getTotalCsCount(uid);
		model.addAttribute("csCount", csCount);
		
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
	public Map<String, MyVO> selectOrder(@RequestBody OrderVO vo) {
		
		int ordNo = vo.getOrdNo();
		String prodNo = vo.getProdNo();
		
		MyVO my = service.selectOrder(ordNo, prodNo);
		
		Map<String, MyVO> map = new HashMap<>();
		map.put("vo", my);
		
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
	
	// 포인트 확정
	@ResponseBody
	@PostMapping("my/pointConfirm")
	public Map<String, Integer> pointConfirm(@RequestBody PointVO vo, @AuthenticationPrincipal MyUserDetails myUser) {
		vo.setUid(myUser.getUser().getUid());
		int pointConfResult = service.selectPurConfStatus(vo.getUid(), vo.getOrdNo(), vo.getProdNo());
		Map<String, Integer> map = new HashMap<>();
		
		if(pointConfResult == 1) {
			map.put("result", 2);
		}else if(pointConfResult == 0) {
			int result = service.pointConfirm(vo);
			map.put("result", result);
		}
		return map;
	}
}
