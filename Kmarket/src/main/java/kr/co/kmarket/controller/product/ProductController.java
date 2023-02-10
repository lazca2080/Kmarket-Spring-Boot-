package kr.co.kmarket.controller.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket.service.ProductService;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProductController {
	
	@Autowired
	private ProductService service;
	
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
	
	@GetMapping("product/view")
	public String view(Model model, String prodNo) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		// 상품번호에 맞는 상품 불러오기
		ProductVO product = service.selectProduct(prodNo);
		model.addAttribute("prod", product);
		
		log.info("test " +product.getThumb2());
		
		return "product/view";
	}
	
	@GetMapping("/product/cart")
	public String cart(Model model) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		return "/product/cart";
	}
	
	@GetMapping("product/order")
	public String order(Model model) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		return "product/order";
	}
	
	@GetMapping("product/complete")
	public String complete(Model model) {
		// 카테고리 분류
		Map<String, List<CateVO>> cate = service.selectCate();
		model.addAttribute("cate", cate);
		
		return "product/complete";
	}	

}
