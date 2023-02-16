package kr.co.kmarket.controller.admin;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.kmarket.entity.UserEntity;
import kr.co.kmarket.security.MyUserDetails;
import kr.co.kmarket.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import kr.co.kmarket.service.AdminService;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	// 상품 목록 페이지
	@GetMapping(value = { "admin", "admin/index"})
	public String index() {
		return "admin/index";
	}

//	@GetMapping("adminSearch")
//	@ResponseBody
//	public List<ProductVO> getSeachList(@RequestParam("searchType") String searchType,
//										@RequestParam("keyword") String keyword){
//		return service.selectProducts(start, seller)
//	}


	// 상품 목록 페이지
	@GetMapping("admin/product/list")
	public String list(String pg, Model m, @AuthenticationPrincipal MyUserDetails myUserDetails,
					   @RequestParam(value = "searchType", defaultValue = "prodName") String searchType,
					   @RequestParam(value = "keyword", required = false) String keyword) throws Exception
	{
		UserEntity member =  myUserDetails.getUser();

		String seller = member.getUid(); // uid
		int level = member.getLevel(); // uid level

		int currentPage = service.getCurrentPage(pg);
		int start = service.getLimitStart(currentPage);
		int total = service.selectCountTotalAdmin();
		//int total = service.selectCountTotalSeller(seller);
		int lastPage = service.getLastPageNum(total);
		int pageStartNum = service.getPageStartNum(total, start);
		int groups[] = service.getPageGroup(currentPage, lastPage);

		// 판매회원 일 경우 보일 게시글
		if(level == 5){
			// 판매자 자기 자신 상품목록만 보여져야 하기 때문에 seller 추가
			List<ProductVO> products = service.selectProducts(start, seller, searchType, keyword);
			log.info("searchType : ", searchType);
			log.info("keyword ", keyword);
			m.addAttribute("products", products);
		}else if(level == 7){
			// 최고관리자 일 경우 보일 상품 전체 게시글
			List<ProductVO> products = service.selectProductsAdmin(start);
			m.addAttribute("products", products);
		}
		m.addAttribute("currentPage", currentPage);
		m.addAttribute("lastPage", lastPage);
		m.addAttribute("pageStartNum", pageStartNum);
		m.addAttribute("groups", groups);
		return "admin/product/list";
	}
	
	// 상품 등록 페이지
	@GetMapping("admin/product/register")
	public String register(Model model) {
		
		// 카테고리 분류
		List<CateVO> cate1 = service.selectCate1();
		model.addAttribute("cate", cate1);
		
		return "admin/product/register";
	}
	
	// 상품 등록
	@PostMapping("admin/product/register")
	public String register(ProductVO vo, HttpServletRequest req) {
		vo.setSeller("admin");
		vo.setIp(req.getRemoteAddr());
		
		service.register(vo);
		
		return "redirect:/admin/product/register";
	}
	
	
	// 카테고리 분류
	@ResponseBody
	@GetMapping("admin/register/selectCate2")
	public Map<String, List<CateVO>> selectCate2(@RequestParam String cate1) {
		List<CateVO> cate2 = service.selectCate2(cate1);
		
		Map<String, List<CateVO>> map = new HashMap<>();
		map.put("result", cate2);
		
		return map;
	};
}
