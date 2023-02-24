package kr.co.kmarket.dao;

import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminCsDAO {


    public int insertArticleNotice(CsVO vo);
    public int insertArticleFaq(CsVO vo);

    /** 글 보기 **/
    public CsVO noticeSelectOne(int no);
    public CsVO faqSelectOne(int no);
    public CsVO qnaSelectOne(int no);

    /** 글 리스트 **/
    public List<CsVO> selectAdminCSnotice(@Param("start") int start,
                                          @Param("cateType1") String cateType1);

    public List<CsVO> selectAdminCSfaq(@Param("start") int start,
                                       @Param("cateType1") String cateType1,
                                       @Param("cateType2") String cateType2);

    public List<CsVO> selectAdminCSqna(@Param("start") int start,
                                       @Param("cateType1") String cateType1,
                                       @Param("cateType2") String cateType2);

    /** 게시글 개수 **/
    public int selectAdminNoticeTotal(String cateType1);
    public int selectAdminQnaTotal(@Param("cateType1") String cateType1,@Param("cateType2")  String cateType2);
    public int selectAdminFaqTotal(@Param("cateType1") String cateType1,@Param("cateType2")  String cateType2);

    public void updateNotice(CsVO vo);
    public void updateFaq(CsVO vo);
    public int updateReply(CsVO vo);

    public int checkDeleteNotice(String no);
    public int checkDeleteFaq(String no);
    public int checkDeleteQna(String no);
}
