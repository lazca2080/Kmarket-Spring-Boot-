package kr.co.kmarket.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
	public List<OrderVO> selectMyHomeOrder(@Param("uid") String uid, @Param("start") int start, @Param(value = "date") LocalDate date);
	// MyHomePoint	
	public List<PointVO> selectMyHomePoint(@Param("uid") String uid, @Param("start") int start);
	// MyHomeReview	
	public List<ReviewVO> selectMyHomeReview(@Param("uid") String uid, @Param("start") int start);
	// MyHomeCs
	public List<CsVO> selectMyHomeCs(@Param("uid") String uid, @Param("start") int start);
	
	// 판매자 정보
	public MemberVO selectCompany(String company);
	
	// 주문 상세
	public MyVO selectOrder(int ordNo);
	
	// 리뷰 작성 여부 판단
	public int selectRevStatus(@Param("uid") String uid, @Param("prodNo") String prodNo);
	
	// 리뷰 작성
	public int insertReview(ReviewVO vo);
	
	// 리뷰 작성 여부 업데이트
	public int updateRevStatus(@Param("uid") String uid, @Param("prodNo") String prodNo);
	
	// 전체주문내역 날짜 검색
	public List<OrderVO> searchDateOrdered(Map<String, String> map);
	
	// 전체주문내역 날짜 검색
	public List<PointVO> searchDatePoint(Map<String, String> map);
	
	// 페이징
	public int selectCountOrderTotal(String uid);
	
	public int selectCountPointTotal(String uid);
	
	public int selectCountCsTotal(String uid);
	
	public int selectCountReviewTotal(String uid);
}
