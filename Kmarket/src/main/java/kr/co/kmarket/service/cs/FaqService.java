package kr.co.kmarket.service.cs; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.CsDAO;
import kr.co.kmarket.vo.CsVO;

@Service
public class FaqService {
	@Autowired
	private CsDAO dao;
	
	public List<CsVO> selectFaqArticles(String cateType1, String cateType2) {
		return dao.selectFaqArticles(cateType1,cateType2);
	}
	
	public CsVO selectarticle(String no) {
		return dao.selectarticle(no);
	}
	
}
