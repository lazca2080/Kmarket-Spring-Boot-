package kr.co.kmarket.service.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.CsDAO;
import kr.co.kmarket.vo.CsVO;

@Service
public class QnaService {
	@Autowired
	private CsDAO dao;
	

	public List<CsVO> selectQnaArticles(String cateType1, int start){
		return dao.selectQnaArticles(cateType1, start);
	};
	
	public CsVO selectQCs(Integer no) {
		CsVO vo = dao.selectQCs(no);
		dao.updateQArticleHit(no);
		return vo;
	}
	
	public int QinsertArticle(CsVO vo) {
		
		return dao.QinsertArticle(vo);
		
		
	}
	
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
			
			// 전체 게시물 갯수
			public int getTotalCount(String cateType1) {
				return dao.selectQnaCountTotal(cateType1);
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
}
