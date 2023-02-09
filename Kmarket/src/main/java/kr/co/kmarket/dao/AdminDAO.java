package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;

@Repository
@Mapper
public interface AdminDAO {
	
	// 상품 등록
	public void register(ProductVO vo);
	
	// 카테고리 분류
	public List<CateVO> selectCate1();
	public List<CateVO> selectCate2(@RequestParam String cate1);
}
