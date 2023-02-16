package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket.vo.CsVO;

@Repository
@Mapper
public interface CsDAO {
	// 글 관리
	public List<CsVO> selectCss();
	public List<CsVO> selectArticles(@Param("cate") String cate, @Param("cateType1") String cateType1, @Param("start") int start);
	public CsVO selectCs(int no);
	public int updateArticleHit(int no);
	public List<CsVO> selectFaqArticles(@Param("cateType1") String cateType1, @Param("cateType2") String cateType2);
	
	
	
	// 글 페이지
	public int selectCountTotal(String cateType1);
}

