package kr.co.kmarket.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
		
		ProductVO totalProd = new ProductVO();
		totalProd.setCount(vo.size());
		for(ProductVO prod : vo) {
			totalProd.setPrice(totalProd.getPrice() + prod.getPrice());
			totalProd.setDelivery(totalProd.getDelivery() + prod.getDelivery());
			totalProd.setPoint(totalProd.getPoint() + prod.getPoint());
			totalProd.setTotal(totalProd.getTotal() + prod.getTotal());
		}
		model.addAttribute("totalProd", totalProd);
		
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
	
	// 장바구니 선택 상품
	@ResponseBody
	@PostMapping("product/checkCart")
	public Map<String, Integer> checkCart(@RequestParam(value="checkList[]") List<String> checkList) {
		log.info(" " + checkList);
		
		Map<String, Integer> map = new HashMap<>();
		map.put("result", 1);
		
		return map;
	}
	
	// 상품 주문
	@GetMapping("product/order")
	public String order(Model model) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		return "product/order";
	}
	
	// 상품 주문 완료
	@GetMapping("product/complete")
	public String complete(Model model) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		return "product/complete";
	}	

}
