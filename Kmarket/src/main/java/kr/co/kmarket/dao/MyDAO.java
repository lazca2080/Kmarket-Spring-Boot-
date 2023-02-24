package kr.co.kmarket.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import groovyjarjarpicocli.CommandLine.Parameters;
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
	public List<OrderVO> selectMyHomeOrder(@Param("uid") String uid, @Param("start") int start, @Param("begin") String begin, @Param("end") String end);
	// MyHomePoint	
	public List<PointVO> selectMyHomePoint(@Param("uid") String uid, @Param("start") int start, @Param("begin") String begin, @Param("end") String end);
	// MyHomeReview	
	public List<ReviewVO> selectMyHomeReview(@Param("uid") String uid, @Param("start") int start);
	// MyHomeCs
	public List<CsVO> selectMyHomeCs(@Param("uid") String uid, @Param("start") int start);
	
	// 판매자 정보
	public MemberVO selectCompany(String company);
	
	// 주문 상세
	public MyVO selectOrder(@Param("ordNo") int ordNo, @Param("prodNo") String prodNo);
	
	// 리뷰 작성 여부 판단
	public int selectRevStatus(@Param("uid") String uid, @Param("prodNo") String prodNo);
	
	// 리뷰 작성
	public int insertReview(ReviewVO vo);
	
	// 리뷰 작성 여부 업데이트
	public int updateRevStatus(@Param("uid") String uid, @Param("prodNo") String prodNo);
	
	// 포인트 확정 여부
	public int selectPurConfStatus(@Param("uid") String uid, @Param("ordNo") int ordNo, @Param("prodNo") int prodNo);
	
	// 포인트 확정
	public int pointConfirm(PointVO vo);
	
	// 포인트 확정 여부 업테이드
	public int updatePurConf(@Param("uid") String uid, @Param("ordNo") int ordNo, @Param("prodNo") int prodNo);
	
	// 페이징
	public int selectOrderTotal(String uid);
	
	public int selectCountOrderTotal(@Param("uid") String uid, @Param("begin") String begin, @Param("end") String end);
	
	public int selectCountPointTotal(@Param("uid") String uid, @Param("begin") String begin, @Param("end") String end);
	
	public int selectCountCsTotal(String uid);
	
	public int selectCountReviewTotal(String uid);
}
