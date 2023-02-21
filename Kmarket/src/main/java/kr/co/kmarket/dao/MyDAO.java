package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket.vo.CsVO;
import kr.co.kmarket.vo.MemberVO;
import kr.co.kmarket.vo.MyVO;
import kr.co.kmarket.vo.OrderVO;
import kr.co.kmarket.vo.PointVO;
import kr.co.kmarket.vo.ReviewVO;

@Repository
@Mapper
public interface MyDAO {
	
	/* 메인 */
	// MyHomeOrder
	public List<OrderVO> selectMyHomeOrder(String uid);
	// MyHomePoint	
	public List<PointVO> selectMyHomePoint(String uid);
	// MyHomeReview	
	public List<ReviewVO> selectMyHomeReview(String uid);
	// MyHomeCs
	public List<CsVO> selectMyHomeCs(String uid);
	
	// 판매자 정보
	public MemberVO selectCompany(String company);
	
	// 주문 상세
	public MyVO selectOrder(int ordNo);
	
	// 리뷰 작성 여부 판단
	public int selectRevStatus(@Param("uid") String uid, @Param("prodNo") String prodNo);
	
	// 리뷰 작성
	public int insertReview(ReviewVO vo);
	
	public int updateRevStatus(@Param("uid") String uid, @Param("prodNo") String prodNo);
}
