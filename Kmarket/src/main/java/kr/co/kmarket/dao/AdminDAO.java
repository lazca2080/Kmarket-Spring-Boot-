package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;

@Repository
@Mapper
public interface AdminDAO {
	
	// 상품 등록
	public void register(ProductVO vo);

	// (최고관리자) 상품 목록조회
	public List<ProductVO> selectProductsAdmin();

	// (판매회원) 판매자 조회
	public List<ProductVO> selectProducts(@Param("start") int start, @Param("seller") String seller);

	// 카테고리 분류
	public List<CateVO> selectCate1();
	public List<CateVO> selectCate2(@RequestParam String cate1);

	// admin-product-list 상품 총합
	public int selectCountTotal(String seller);
}
