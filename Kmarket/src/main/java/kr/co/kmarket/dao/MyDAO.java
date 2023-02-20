package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmarket.vo.CsVO;
import kr.co.kmarket.vo.MyVO;
import kr.co.kmarket.vo.OrderVO;
import kr.co.kmarket.vo.PointVO;
import kr.co.kmarket.vo.ReviewVO;

@Repository
@Mapper
public interface MyDAO {
	
	// MyHomeOrder
	public List<OrderVO> selectMyHomeOrder(String uid);
	// MyHomePoint	
	public List<PointVO> selectMyHomePoint(String uid);
	// MyHomeReview	
	public List<ReviewVO> selectMyHomeReview(String uid);
	// MyHomeCs
	public List<CsVO> selectMyHomeCs(String uid);
}
