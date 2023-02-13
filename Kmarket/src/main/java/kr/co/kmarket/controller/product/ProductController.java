package kr.co.kmarket.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import kr.co.kmarket.security.MyUserDetails;
import kr.co.kmarket.service.ProductService;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	// 상품 목록
	@GetMapping("product/list")
	public String list(Model model, String prodCate1, String prodCate2) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		// 상품 카테고리에 맞는 상품 리스트
		List<ProductVO> products = service.selectProducts(prodCate1, prodCate2);
		model.addAttribute("prods", products);
		
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
		List<ProductVO> vo = service.selectCarts("admin");
		model.addAttribute("prod", vo);
		
		return "product/cart";
	}
	
	// 장바구니 등록
	@ResponseBody
	@PostMapping("product/cart")
	public Map<String, Integer> insertCart(@RequestParam String prod, HttpServletRequest req) {
		Gson gson = new Gson();
		ProductVO vo = gson.fromJson(prod, ProductVO.class);
		
		// 테스트용 uid
		vo.setSeller("admin");
		vo.setIp(req.getRemoteAddr());
				
		int result = service.insertCart(vo);
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	// 장바구니 전체 선택 가격
	@ResponseBody
	@PostMapping("product/cart/total")
	public Map<String, ProductVO> selectCartTotal(@AuthenticationPrincipal MyUserDetails myuser) {
		// 임시용 uid 추후에 myuser.get 사용하여야함
		String uid = "admin";
		
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
		String uid = "admin";
		
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
		String uid = "admin"; // 임시
		
		// 장바구니에서 넘어온 session 처리
		HttpSession session = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<String> checkList = (List<String>) session.getAttribute("order");
		
		// 장바구니가 비워져있는 경우 cart로 이동
		if(checkList == null) { return "product/cart?success=101"; }
		
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
		
		return "product/order";
	}
	
	// 장바구니 상품 주문
	@ResponseBody
	@PostMapping("product/order")
	public Map<String, Integer> selectCartOrder(@RequestParam(value="checkList[]") List<String> checkList, HttpServletRequest req) {
		// 주문하기 버튼과 동시에 배열을 세션에 저장
		HttpSession session = req.getSession();
		session.setAttribute("order", checkList);
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", 1);
		return map;
	}
	
	// 상품 주문 완료
	@GetMapping("product/complete")
	public String complete(Model model, HttpServletRequest req) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		HttpSession session = req.getSession();
		@SuppressWarnings("unchecked")
		List<ProductVO> complete = (List<ProductVO>) session.getAttribute("complete");
		if(complete == null) { return "product/cart"; }
		
		int totalPrice = 0;
		for(ProductVO total:complete) {
			totalPrice = totalPrice + total.getTotal(); 
		}
		
		model.addAttribute("complete", complete);
		model.addAttribute("total", totalPrice);
		
		return "product/complete";
	}	

}
