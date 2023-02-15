package kr.co.kmarket.service.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.CsDAO;
import kr.co.kmarket.vo.CsVO;

@Service
public class QnaService {
	@Autowired
	private CsDAO dao;
	
	public void selectArticles(){};
}
