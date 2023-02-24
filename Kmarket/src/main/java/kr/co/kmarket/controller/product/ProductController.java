package kr.co.kmarket.controller.product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import kr.co.kmarket.security.MyUserDetails;
import kr.co.kmarket.service.ProductService;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.MemberVO;
import kr.co.kmarket.vo.OrderVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	// 상품 목록
	@GetMapping("product/list")
	public String list(Model model, String prodCate1, String prodCate2, String sort, String pg) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalCount(prodCate1, prodCate2);
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		List<ProductVO> products = service.selectProducts(prodCate1, prodCate2, sort, start);
		model.addAttribute("prods", products);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
		
		model.addAttribute("prodCate1", prodCate1);
		model.addAttribute("prodCate2", prodCate2);
		model.addAttribute("sort", sort);
		
		return "product/list";
	}

	// 상품
	@GetMapping("product/view")
	public String view(Model model, String prodNo) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		// 상품번호에 맞는 상품 불러오기
		ProductVO product = service.selectProduct(prodNo);
		model.addAttribute("prod", product);
		
		return "product/view";
	}
	
	// 상품 장바구니
	@GetMapping("product/cart")
	public String cart(Model model, @AuthenticationPrincipal MyUserDetails myUser) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		// 임시용 admin security 설정 완료후 myuser사용하기
		List<ProductVO> vo = service.selectCarts(myUser.getUser().getUid());
		
		model.addAttribute("prod", vo);
		
		return "product/cart";
	}
	
	// 장바구니 등록
	@ResponseBody
	@PostMapping("product/cart")
	public Map<String, Integer> insertCart(@RequestBody ProductVO vo, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myuser) {
		HttpSession session = req.getSession();
		vo.setSeller(myuser.getUser().getUid());
		vo.setIp(req.getRemoteAddr());
		
		int result = 0;
		
		result = service.insertCart(vo);
		session.setAttribute("type", "cart");
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	@ResponseBody
	@PostMapping("product/purchase")
	public Map<String, Integer> insertPurchase(@RequestBody ProductVO vo, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myuser) {
		HttpSession session = req.getSession();
		vo.setSeller(myuser.getUser().getUid());
		vo.setIp(req.getRemoteAddr());
		
		int result = 0;
		
		result = service.insertCart(vo);
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		session.setAttribute("order", vo);
		session.setAttribute("type", "order");
		
		return map;
	}
	
	// 장바구니 전체 선택 가격
	@ResponseBody
	@PostMapping("product/cart/total")
	public Map<String, ProductVO> selectCartTotal(@AuthenticationPrincipal MyUserDetails myuser) {
		String uid = myuser.getUser().getUid();
		
		ProductVO total = service.selectCartTotal(uid);
		
		Map<String, ProductVO> map = new HashMap<>();
		map.put("result", total);
		
		return map;
	}
	
	// 장바구니 선택 삭제
	@ResponseBody
	@PostMapping("product/checkCart")
	public Map<String, Integer> deleteCart(@RequestParam(value="checkList[]") List<String> checkList, @AuthenticationPrincipal MyUserDetails myuser) {

		// 테스트용 uid
		String uid = myuser.getUser().getUid();
		
		int result = service.deleteCart(checkList, uid);
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	// 상품 주문
	@GetMapping("product/order")
	public String order(Model model, HttpServletRequest req, HttpServletResponse response, @AuthenticationPrincipal MyUserDetails myuser) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		// 전달 받은 상품 정보
		String uid = myuser.getUser().getUid(); // 임시
		
		// 장바구니, 구매하기에서 넘어온 session 처리
		HttpSession session = req.getSession();
		String type = (String) session.getAttribute("type");
		
		if(type == "order") {
			// 구매하기에서 넘어 왔을 때
			ProductVO order = (ProductVO) session.getAttribute("order");
			ProductVO prod  = service.selectProduct(order.getProdNo());
			order.setProdName(prod.getProdName());
			order.setDescript(prod.getDescript());
			order.setProdCate1(prod.getProdCate1());
			order.setProdCate2(prod.getProdCate2());
			order.setThumb2(prod.getThumb2());
			
			List<String> checkList = new ArrayList<>();
			checkList.add(order.getProdNo());
			session.setAttribute("cartCheckList", checkList);
			
			List<ProductVO> product = service.selectCartOrder(checkList, uid);
			session.setAttribute("complete", product);
			
			model.addAttribute("prod", order);
			model.addAttribute("total", order);
			model.addAttribute("user", myuser.getUser());
			
		}else if(type == "cart") {
			// 장바구니에서 넘어 왔을 때
			@SuppressWarnings("unchecked")
			List<String> checkList = (List<String>) session.getAttribute("cartCheckList");
			
			// 장바구니가 비워져있는 경우 cart로 이동
			if(checkList == null) { return "product/cart"; }
			
			// 선택된 상품 조회
			List<ProductVO> prod = service.selectCartOrder(checkList, uid);
			session.setAttribute("complete", prod);
			
			// 상품 총합 계산
			ProductVO total = new ProductVO();
			total.setCount(prod.size());
			for(ProductVO vo:prod) {
				total.setPrice(total.getPrice() + vo.getPrice());
				total.setDelivery(total.getDelivery() + vo.getDelivery());
				total.setPoint(total.getPoint() + vo.getPoint());
				total.setTotal(total.getTotal() + vo.getTotal());
			}
			
			model.addAttribute("prod", prod);
			model.addAttribute("total", total);
			model.addAttribute("user", myuser.getUser());
		}
		
		
		return "product/order";
	}
	
	// 장바구니 상품 주문
	@ResponseBody
	@PostMapping("product/order")
	public Map<String, Integer> selectCartOrder(@RequestParam(value="checkList[]") List<String> checkList, HttpServletRequest req) {
		// 주문하기 버튼과 동시에 주문 상품번호 배열을 세션에 저장
		HttpSession session = req.getSession();
		session.setAttribute("cartCheckList", checkList);
		session.setAttribute("type", "cart");
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", 1);
		return map;
	}
	
	// 상품 주문 완료
	@GetMapping("product/complete")
	public String complete(Model model, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myuser) {
		HttpSession session = req.getSession();
		
		String type = (String) session.getAttribute("type");
		
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		OrderVO order = (OrderVO) session.getAttribute("order");
		@SuppressWarnings("unchecked")
		List<ProductVO> complete = (List<ProductVO>) session.getAttribute("complete");
		if(complete == null) { return "product/cart"; }
		
		model.addAttribute("complete", complete);
		model.addAttribute("order", order);
		
		session.removeAttribute("complete");
		session.removeAttribute("order");
		session.removeAttribute("type");
		session.removeAttribute("cartCheckList");
		
		return "product/complete";
	}
	
	@ResponseBody
	@Transactional
	@PostMapping("product/complete")
	public Map<String, Integer> insertComplete(@RequestBody OrderVO vo, @AuthenticationPrincipal MyUserDetails myuser, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String uid = myuser.getUser().getUid();
		int point = myuser.getUser().getPoint();
		
		vo.setOrdUid(uid);
		
		// 고유번호 ------------ 수정 필요 -------------
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
		String now = sdf.format(new Date());
		
		Random random = new Random(1000000000);
		int rand = random.nextInt();
		int randOrdNo = rand + Integer.parseInt(now);
		// 고유번호 ------------ 수정 필요 -------------
		
		vo.setOrdNo(randOrdNo);
		
		session.setAttribute("order", vo);
		
		// point 검증 -> input value값은 개발자 도구에서 수정이 가능하고 수정 된 상태로 넘겨짐
		// 따라서 서버에서 한번 더 검증을 거쳐야함
		if(vo.getUsedPoint() < 5000) {
			// 사용한 포인트가 5000원 미만일 때
			// usedPoint 값은 적용을 눌러야만 차감된 상태로 넘어옴
			// js에서 비정상 포인트일 때 작동을 막아놓음
			// vo.setOrdTotPrice(vo.getOrdTotPrice()+vo.getUsedPoint());
			vo.setUsedPoint(0);
		}else if(vo.getUsedPoint() > point) {
			// vo.setOrdTotPrice(vo.getOrdTotPrice()+vo.getUsedPoint());
			vo.setUsedPoint(0);
		}
		
		// 이 밑으로 많이 지저분함 수정 필수@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		// km_product_order insert
		int result = service.insertComplete(vo);
		
		// km_member_point insert
		// 구매 확정 시 포인트 적립하는걸로 변경
		// service.insertCompletePoint(randOrdNo, uid, vo.getSavePoint());
		
		// km_product_order_item insert
		@SuppressWarnings("unchecked")
		List<String> checkList = (List<String>) session.getAttribute("cartCheckList");
		log.info(""+checkList);
		service.insertCompleteItem(randOrdNo, uid, checkList);
		
		// km_product_cart delete
		service.deleteCompleteCart(uid, checkList);
		
		// 이 밑으로 많이 지저분함 수정 필수@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	@GetMapping("product/search")
	public String search(Model model, String keyWord, String sort, String pg) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		model.addAttribute("keyWord", keyWord);
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalSearchCount(keyWord, sort);
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		List<ProductVO> search = service.searchProduct(keyWord, sort, start);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
		
		model.addAttribute("prods", search);
		model.addAttribute("sort", sort);
		model.addAttribute("size", total);
		
		return "product/search";
	}
	
	@GetMapping("product/secondSearch")
	public String secondSearch(String min, String max, String chk, String search, String firstSearch, String pg, Model model, String sort, RedirectAttributes re) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		model.addAttribute("keyWord", search);
		
		if(chk == null) {
			re.addAttribute("keyWord", firstSearch);
			re.addAttribute("error", 101);
			return "redirect:/product/search";
		}
		
		int   currentPage  = service.getCurrentPage(pg);
		int   start        = service.getLimitStart(currentPage);
		long  total        = service.getTotalSecondSearchCount(min, max, chk, search, firstSearch, sort);
		int   lastPage     = service.getLastPageNum(total);
		int   pageStartNum = service.getPageStartNum(total, start);
		int[] groups       = service.getPageGroup(currentPage, lastPage);
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageStartNum", pageStartNum+1);
		model.addAttribute("groups", groups);
		
		List<ProductVO> secondSearch = service.secondSearch(min, max, chk, search, firstSearch, sort, start);
		
		model.addAttribute("prods", secondSearch);
		model.addAttribute("sort", sort);
		model.addAttribute("size", total);
		
		return "product/search";
	}
	
}
