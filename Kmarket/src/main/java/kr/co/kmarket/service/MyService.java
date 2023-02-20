package kr.co.kmarket.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.MyDAO;
import kr.co.kmarket.vo.CsVO;
import kr.co.kmarket.vo.MyVO;
import kr.co.kmarket.vo.OrderVO;
import kr.co.kmarket.vo.PointVO;
import kr.co.kmarket.vo.ReviewVO;

@Service
public class MyService {
	
	@Autowired
	private MyDAO dao;
	
	public List<OrderVO> selectMyHomeOrder() {
		return dao.selectMyHomeOrder();
	}
	
	public List<PointVO> selectMyHomePoint() {
		return dao.selectMyHomePoint();
	}
	
	public List<ReviewVO> selectMyHomeReview() {
		return dao.selectMyHomeReview();
	}
	
	public List<CsVO> selectMyHomeCs() {
		return dao.selectMyHomeCs();
	}
	
	

}
