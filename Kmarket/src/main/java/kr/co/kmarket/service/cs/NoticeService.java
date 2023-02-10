package kr.co.kmarket.service.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.CsDAO;
import kr.co.kmarket.vo.CsVO;

@Service
public class NoticeService {
	@Autowired
	private CsDAO dao;
	
	public List<CsVO> selectNtList(String cateType1){
		return dao.selectNtList(cateType1);
	}
}
