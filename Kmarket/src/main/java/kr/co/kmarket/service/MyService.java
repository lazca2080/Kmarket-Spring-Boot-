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
	
	public List<OrderVO> selectMyHomeOrder(String uid) {
		return dao.selectMyHomeOrder(uid);
	}
	
	public List<PointVO> selectMyHomePoint(String uid) {
		return dao.selectMyHomePoint(uid);
	}
	
	public List<ReviewVO> selectMyHomeReview(String uid) {
		return dao.selectMyHomeReview(uid);
	}
	
	public List<CsVO> selectMyHomeCs(String uid) {
		return dao.selectMyHomeCs(uid);
	}
	
	

}
