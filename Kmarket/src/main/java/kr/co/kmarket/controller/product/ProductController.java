package kr.co.kmarket.controller.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
	
	@GetMapping("/product/list")
	public String list() {
		return "/product/list";
	}
	
	@GetMapping("/product/view")
	public String view() {
		return "/product/view";
	}
	
	@GetMapping("/product/cart")
	public String cart() {
		return "/product/cart";
	}
	
	@GetMapping("/product/order")
	public String order() {
		return "/product/order";
	}
	
	@GetMapping("/product/complete")
	public String complete() {
		return "/product/complete";
	}	

}