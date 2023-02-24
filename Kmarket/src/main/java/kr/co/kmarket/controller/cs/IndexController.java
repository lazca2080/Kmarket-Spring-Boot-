package kr.co.kmarket.controller.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket.service.cs.IndexService;
import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
	@Autowired
	private IndexService service;
	
	@GetMapping(value = {"cs", "cs/index"})
	public String index(Model model, String pg) {
		List<CsVO> cate = service.selectCss();
		model.addAttribute("cate",cate);
		log.info("est"+cate);
		return "cs/index";
	}
}
