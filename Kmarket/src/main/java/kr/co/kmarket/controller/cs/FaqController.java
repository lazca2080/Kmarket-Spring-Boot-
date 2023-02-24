package kr.co.kmarket.controller.cs;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket.service.cs.FaqService;
import kr.co.kmarket.service.cs.IndexService;
import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FaqController {
	@Autowired
	private FaqService service;
	
	@GetMapping("cs/faq/list")
	public String list(String cateType1, String cateType2, String no, Model model) {
		List<CsVO> faq = service.selectFaqArticles(cateType1, cateType2); 
		
		model.addAttribute("cateType1", cateType1);
		model.addAttribute("cateType2", cateType2);
		model.addAttribute("no", no);
		model.addAttribute("faq", faq);
		
		log.info("sdf : "+cateType1);
		log.info("sdf : "+cateType2);
		log.info("sdf : "+no);
		log.info("sdf : "+faq);
		return "cs/faq/list";
	}
	
	@GetMapping("cs/faq/view")
	public String view(String cateType1, String cateType2, String no, Model model) {
		
		CsVO vo = service.selectarticle(no);
		
		model.addAttribute("cateType1",cateType1);
		model.addAttribute("cateType2",cateType2);
		model.addAttribute("vo",vo);
		
		return "cs/faq/view";
	}
	
	
}
