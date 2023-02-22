package kr.co.kmarket.dao;

import kr.co.kmarket.vo.CsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminCsDAO {


    public int insertArticleNotice(CsVO vo);
    public int insertArticleFaq(CsVO vo);

    public List<CsVO> adminCSnotice(int start);
    public List<CsVO> adminCSfaq(int start);
    public List<CsVO> adminCSqna(int start);
	  public int selectCountTotal();


    public CsVO noticeSelectOne(int no);
    public CsVO faqSelectOne(int no);
    public CsVO qnaSelectOne(int no);

    public List<CsVO> selectAdminCSnotice(int start);
    public List<CsVO> selectAdminCSfaq(int start);
    public List<CsVO> selectAdminCSqna(int start);
    public int selectAdminNoticeTotal();
    public int selectAdminQnaTotal();
    public int selectAdminFaqTotal();

    public void updateNotice(CsVO vo);
    public void updateFaq(CsVO vo);
    public int checkDeleteNotice(String no);
    public int checkDeleteFaq(String no);
    public int checkDeleteQna(String no);
}