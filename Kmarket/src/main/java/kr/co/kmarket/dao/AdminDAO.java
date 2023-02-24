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

	// 상품 조회
	public List<ProductVO> selectProducts(@Param("start") int start,
										  @Param("uid") String seller,
										  @Param("searchType") String searchType,
										  @Param("keyword") String keyword,
										  @Param("level") int level
	);

	// 카테고리 분류
	public List<CateVO> selectCate1();
	public List<CateVO> selectCate2(@RequestParam String cate1);

	// (admin) 판매 게시물 총 개수
	public int selectCountTotal(@Param("level") int level,
								@Param("uid") String seller,
								@Param("searchType") String searchType,
								@Param("keyword") String keyword
	);
	// 상품 업데이트
	public int updateProduct(ProductVO vo);

	// 상품삭제
	public int deleteProduct(String prodNo);
}
