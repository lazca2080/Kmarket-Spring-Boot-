package kr.co.kmarket.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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
	public List<OrderVO> selectMyHomeOrder(String uid, int start, String date, String rangeMonth, Map<String, String> dateRange) {
		Map<String, String> nowDate = nowDate(date, rangeMonth, dateRange);
		log.info("begin : "+nowDate.get("begin"));
		log.info("end : "+nowDate.get("end"));
		
		return dao.selectMyHomeOrder(uid, start, nowDate.get("begin"), nowDate.get("end"));
	}
	
	public List<PointVO> selectMyHomePoint(String uid, int start, String date, String rangeMonth, Map<String, String> dateRange) {
		Map<String, String> nowDate = nowDate(date, rangeMonth, dateRange);
		return dao.selectMyHomePoint(uid, start, nowDate.get("begin"), nowDate.get("end"));
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
	public MyVO selectOrder(int ordNo, String prodNo) {
		return dao.selectOrder(ordNo, prodNo);
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
	
	// 포인트 확정 여부
	public int selectPurConfStatus(String uid, int ordNo, int prodNo) {
		return dao.selectPurConfStatus(uid, ordNo, prodNo);
	}
	
	// 포인트 확정 && 포인트 확정 여부 변경
	@Transactional
	public int pointConfirm(PointVO vo) {
		
		switch (vo.getType()) {
		case "1th":
			vo.setType("적립");
			break;
		case "2th":
			vo.setType("차감");
			break;
		}
		
		switch (vo.getDescript()) {
		case "1th":
			vo.setDescript("상품 구매 확정");
			break;
		case "2th":
			vo.setDescript("이벤트");
			break;
		case "3rd":
			vo.setDescript("구매");
			break;
		}
		
		dao.updatePurConf(vo.getUid(), vo.getOrdNo(), vo.getProdNo());
		return dao.pointConfirm(vo);
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
	// 전체 order 갯수
	public long getTotalOrderCount(String uid, String date, String rangeMonth, Map<String, String> dateRange) {
		Map<String, String> nowDate = nowDate(date, rangeMonth, dateRange);
		return dao.selectCountOrderTotal(uid, nowDate.get("begin"), nowDate.get("end"));
	}
	
	// order 개수
	public int selectOrderTotal(String uid) {
		return dao.selectOrderTotal(uid);
	}
	
	// 전체 point 갯수
	public long getTotalPointCount(String uid, String date, String rangeMonth, Map<String, String> dateRange) {
		Map<String, String> nowDate = nowDate(date, rangeMonth, dateRange);
		return dao.selectCountPointTotal(uid, nowDate.get("begin"), nowDate.get("end"));
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
	public Map<String, String> nowDate(String date, String rangeMonth, Map<String, String> dateRange) {
		Map<String, String> map = new HashMap<>();
		
		LocalDate now = LocalDate.now();
		String begin = null;
		String end   = null;
		
		if(date != null && rangeMonth == null && dateRange.isEmpty()) {
			begin = date;
			end   = now.plusDays(1).toString();
			map.put("begin", begin);
			map.put("end", end);
		}else if(date == null && rangeMonth != null && dateRange.isEmpty()) {
			LocalDate dates = LocalDate.parse(rangeMonth, DateTimeFormatter.ISO_DATE);
			begin = rangeMonth;
			end   = dates.withDayOfMonth(dates.lengthOfMonth()).toString();
			map.put("begin", begin);
			map.put("end", end);
		}else if(date == null && rangeMonth == null && dateRange.isEmpty()) {
			begin = "1900-01-01";
			end   = "9999-01-01";
			map.put("begin", begin);
			map.put("end", end);
		}else if(!dateRange.isEmpty() && date == null && rangeMonth == null) {
			LocalDate dateBegin = LocalDate.parse(dateRange.get("begin"), DateTimeFormatter.ISO_DATE);
			LocalDate dateEnd = LocalDate.parse(dateRange.get("end"), DateTimeFormatter.ISO_DATE);
			begin = dateBegin.minusDays(1).toString();
			end   = dateEnd.plusDays(1).toString();
			map.put("begin", begin);
			map.put("end", end);
		}
		return map;
	}
}
