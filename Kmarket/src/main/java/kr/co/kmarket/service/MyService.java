package kr.co.kmarket.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyService {
	
	@Autowired
	private MyDAO dao;
	
	/* 메인 */
	public List<OrderVO> selectMyHomeOrder(String uid, int start, String date) {
		LocalDate nowDate = nowDate(date);
		log.info(""+nowDate);
		return dao.selectMyHomeOrder(uid, 0, nowDate);
	}
	
	public List<PointVO> selectMyHomePoint(String uid, int start) {
		return dao.selectMyHomePoint(uid, start);
	}
	
	public List<ReviewVO> selectMyHomeReview(String uid, int start) {
		return dao.selectMyHomeReview(uid, start);
	}
	
	public List<CsVO> selectMyHomeCs(String uid, int start) {
		return dao.selectMyHomeCs(uid, start);
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
	
	
	// 전체주문내역 날짜 검색
	public List<OrderVO> searchDateOrdered(Map<String, String> map) {
		return dao.searchDateOrdered(map);
	}
	
	// 포인트내역 날짜 검색
	public List<PointVO> searchDatePoint(Map<String, String> map) {
		return dao.searchDatePoint(map);
	}

	
	// 페이징
	
	// 페이지 시작값
	public int getLimitStart(int currentPage) {
		return (currentPage - 1) * 10;
	}
	
	// 현재 페이지
	public int getCurrentPage(String pg) {
		int currentPage = 1;
		
		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		return currentPage;
	}
	// 전체 point 갯수
	public long getTotalOrderCount(String uid) {
		return dao.selectCountOrderTotal(uid);
	}
	
	// 전체 point 갯수
	public long getTotalPointCount(String uid) {
		return dao.selectCountPointTotal(uid);
	}
	
	// 전체 cs 갯수
	public long getTotalCsCount(String uid) {
		return dao.selectCountCsTotal(uid);
	}
	
	// 전체 review 갯수
	public long getTotalReviewCount(String uid) {
		return dao.selectCountReviewTotal(uid);
	}
	
	// 마지막 페이지 번호
	public int getLastPageNum(long total) {
		int lastPage = 0;
		
		if(total % 10 == 0) {
			lastPage = (int) (total / 10);
		}else {
			lastPage = (int) (total / 10) + 1;
		}
		return lastPage;
	}
	
	// 페이지 시작번호
	public int getPageStartNum(long total, int start) {
		return (int) (total - start);
	}
	
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPage) {
		int groupCurrent = (int) Math.ceil(currentPage / 10.0);
		int groupStart   = (groupCurrent-1)*10 + 1;
		int groupEnd = groupCurrent*10;
		
		if(groupEnd > lastPage) {
			groupEnd = lastPage;
		}
		
		int[] groups = {groupStart, groupEnd};
		
		return groups;
	}
	
	// 날짜 계산
	public LocalDate nowDate(String date) {
		LocalDate now = LocalDate.now();
		
		switch (date) {
		case "week":
			return now.plusDays(7);
		case "weeks":
			return now.plusDays(15);
		case "month":
			return now.minusMonths(1);
		case "1":
			return now.minusMonths(4);
		case "2":
			return now.minusMonths(3);
		case "3":
			return now.minusMonths(2);
		case "4":
			return now.minusMonths(1);
		case "5":
			return now;
		case "none":
			return null;
		}
		return now;
	}

}
