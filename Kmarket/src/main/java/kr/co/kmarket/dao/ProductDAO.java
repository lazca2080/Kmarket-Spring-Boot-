package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;

@Repository
@Mapper
public interface ProductDAO {
	
	// index 상품 분류
	public List<ProductVO> selectIndex();
	
	// aside 카테고리 분류
	public List<CateVO> selectCate();
	
	// 상품 카테고리에 맞는 상품 리스트
	public List<ProductVO> selectProducts(@Param("prodCate1") String prodCate1, @Param("prodCate2") String prodCate2);
	
	// 상품 번호에 맞는 상품 불러오기
	public ProductVO selectProduct(String prodNo);
	
	// 장바구니 등록
	public int insertCart(ProductVO vo);
	
	// 장바구니 목록
	public List<ProductVO> selectCarts(String uid);
}
