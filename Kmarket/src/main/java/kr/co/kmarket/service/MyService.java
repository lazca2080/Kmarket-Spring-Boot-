package kr.co.kmarket.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.MyDAO;
import kr.co.kmarket.vo.CsVO;
import kr.co.kmarket.vo.MemberVO;
import kr.co.kmarket.vo.MyVO;
import kr.co.kmarket.vo.OrderVO;
import kr.co.kmarket.vo.PointVO;
import kr.co.kmarket.vo.ReviewVO;

@Service
public class MyService {
	
	@Autowired
	private MyDAO dao;
	
	/* 메인 */
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
	
	// 판매자 정보
	public MemberVO selectCompany(String company) {
		return dao.selectCompany(company);
	}
	
	// 주문 상세
	public MyVO selectOrder(int ordNo) {
		return dao.selectOrder(ordNo);
	}
	
	// 리뷰 작성 여부 판단
	public int selectRevStatus(String uid, String prodNo) {
		return dao.selectRevStatus(uid, prodNo);
	};
	
	// 리뷰 작성
	public int insertReview(ReviewVO vo) {
		return dao.insertReview(vo);
	}
	
	public int updateRevStatus(String uid, String prodNo) {
		return dao.updateRevStatus(uid, prodNo);
	}

}
