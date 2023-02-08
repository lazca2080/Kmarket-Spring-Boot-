package kr.co.kmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
	

	@GetMapping("/list")
	public String list() {
		return "/product/list";
	}
	

	@GetMapping("/view")
	public String view() {
		return "/product/view";
	}
	

	@GetMapping("/cart")
	public String cart() {
		return "/product/cart";
	}
	

	@GetMapping("/order")
	public String order() {
		return "/product/order";
	}
	
	@GetMapping("/complete")
	public String complete() {
		return "/product/complete";
	}	

}
