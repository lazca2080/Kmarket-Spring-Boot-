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

    public CsVO noticeSelectOne(int no);
    public CsVO faqSelectOne(int no);
    public CsVO qnaSelectOne(int no);

    public List<CsVO> selectAdminCSnotice(int start);
    public List<CsVO> selectAdminCSfaq(int start);
    public List<CsVO> selectAdminCSqna(int start);
    public int selectAdminNoticeTotal();
    public int selectAdminQnaTotal();
    public int selectAdminFaqTotal();
}
