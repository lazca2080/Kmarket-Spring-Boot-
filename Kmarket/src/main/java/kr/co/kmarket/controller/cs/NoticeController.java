package kr.co.kmarket.controller.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.kmarket.service.cs.IndexService;
import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NoticeController {
	@Autowired
	private IndexService service;
	
	@GetMapping("/cs/notice/list")
	public String list(Model model, String cateType1) {
		//List<CsVO> notice = service.selectNtList(cateType1);
		//model.addAttribute("notice",notice);
		
		//log.info("sdf"+notice);
		return "/cs/notice/list";
	}
	
	@GetMapping("/cs/notice/view")
	public String view() {
		return "/cs/notice/view";
	}
}
