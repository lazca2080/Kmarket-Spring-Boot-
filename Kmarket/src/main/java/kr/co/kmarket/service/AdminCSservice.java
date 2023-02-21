package kr.co.kmarket.service;

import kr.co.kmarket.dao.AdminCsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AdminCSservice {

    @Autowired
    private AdminCsDAO dao;



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

    public int selectCountTotal(){
        return dao.selectCountTotal();
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
