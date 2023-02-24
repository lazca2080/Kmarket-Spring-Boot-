package kr.co.kmarket.service;

import kr.co.kmarket.dao.AdminCsDAO;
import kr.co.kmarket.vo.CsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;

@Slf4j
@Service
public class AdminCSservice {

    @Autowired
    private AdminCsDAO dao;

    public int insertArticleNotice(CsVO vo){
        return dao.insertArticleNotice(vo);
    };
    public int insertArticleFaq(CsVO vo){
        return dao.insertArticleFaq(vo);
    }

    /** 글 보기 ***/
    public CsVO noticeSelectOne(int no){
        return dao.noticeSelectOne(no);
    }
    public CsVO faqSelectOne(int no){
        return dao.faqSelectOne(no);
    }
    public CsVO qnaSelectOne(int no){
        return dao.qnaSelectOne(no);
    }

    public List<CsVO> selectAdminCSnotice(int start, String cateType1){
        return dao.selectAdminCSnotice(start, cateType1);
    }
    public List<CsVO> selectAdminCSfaq(int start, String cateType1, String cateType2){
        return dao.selectAdminCSfaq(start, cateType1, cateType2);
    }
    public List<CsVO> selectAdminCSqna(int start, String cateType1, String cateType2){
        return dao.selectAdminCSqna(start, cateType1, cateType2);
    }

    public int selectAdminNoticeTotal(String cateType1){
        return dao.selectAdminNoticeTotal(cateType1);
    };
    public int selectAdminQnaTotal(String cateType1, String cateType2){
        return dao.selectAdminQnaTotal(cateType1,cateType2);
    };
    public int selectAdminFaqTotal(String cateType1, String cateType2){
        return dao.selectAdminFaqTotal(cateType1,cateType2);
    };

    public void updateNotice(CsVO vo){dao.updateNotice(vo);};
    public void updateFaq(CsVO vo){dao.updateFaq(vo);};
    public int updateReply(CsVO vo){
        return dao.updateReply(vo);
    };

    public Integer checkDeleteNotice(String no){return dao.checkDeleteNotice(no);}
    public Integer checkDeleteFaq(String no){return dao.checkDeleteFaq(no);}
    public Integer checkDeleteQna(String no){return dao.checkDeleteQna(no);}

    // 페이지 시작값
    public int getLimitStart(int currentPage) {
        return (currentPage - 1) * 10;
    }

    // 현재 페이지
    public int getCurrentPage(@RequestParam("pg") String pg) {
        int currentPage = 1;

        if(pg != null) {
            currentPage = Integer.parseInt(pg);
        }
        return currentPage;
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

    // 페이지 시작 번호
    public int getPageStartNum(long total, int start) {
        return (int) (total - start);
    }

    // 페이지 그룹
    public int[] getPageGroup(int currentPage, int lastPage) {

        int groupCurrent = (int) Math.ceil(currentPage / 10.0);
        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage) {
            groupEnd = lastPage;
        }

        int[] groups = {groupStart, groupEnd};

        return groups;
    }

}
