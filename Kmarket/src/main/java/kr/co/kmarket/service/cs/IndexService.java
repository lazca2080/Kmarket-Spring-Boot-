package kr.co.kmarket.service.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.CsDAO;
import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IndexService {
	@Autowired
	private CsDAO dao;
	
	public List<CsVO> selectCs(){
		return dao.selectCs();
	}
	
	
}
