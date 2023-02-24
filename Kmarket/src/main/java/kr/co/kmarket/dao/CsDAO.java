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
	public List<CsVO> selectNoticeArticles(@Param("cateType1") String cateType1, @Param("start") int start);
	public List<CsVO> selectQnaArticles(@Param("cateType1") String cateType1, @Param("start") int start);
	
	public CsVO selectNCs(int no);
	public CsVO selectFCs(int no);
	public CsVO selectQCs(Integer no);
	
	public int updateNArticleHit(int no);
	public int updateQArticleHit(int no);
	public int updateFArticleHit(int no);
	
	public List<CsVO> selectFaqArticles(@Param("cateType1") String cateType1, @Param("cateType2") String cateType2);
	
	
	//글 작성
	public int QinsertArticle(CsVO vo);
	
	
	// 글 페이지
	public int selectNoticeCountTotal(String cateType1);
	public int selectFaqCountTotal(String cateType1);
	public int selectQnaCountTotal(String cateType1);
	
	
	public CsVO selectarticle(String no);
}

