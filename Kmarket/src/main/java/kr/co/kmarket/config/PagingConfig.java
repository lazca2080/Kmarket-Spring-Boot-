package kr.co.kmarket.config;

import kr.co.kmarket.vo.PageVO;

public class PagingConfig {

    public int getCurrentPage(String pg){
        int currentPage = 1;

        if(pg != null){
            currentPage = Integer.parseInt(pg);
        }

        return currentPage;
    }

    public int getLimitStart(int currentPage){
        // (현재 페이지 번호 - 1) * 페이지당 보여줄 게시글 개수
        return (currentPage - 1) * 10;
    }

    public int getLastPageNum(int total){
        int lastPage = 0;

        if(total %10 == 0){
            lastPage = (int)(total/10);
        }else{
            lastPage = (int)(total/10) + 1;
        }
        return lastPage;
    }

    public int getPageStartNum(int total, int start){
        return (int)total-start;
    }

    public int[] getPageGroup(int currentPage, int lastPage){
        int groupCurrent = (int)Math.ceil(currentPage/10.0);

        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage) {
            groupEnd = lastPage;
        }

        int[] groups = {groupStart, groupEnd};

        return groups;
    }

    public PageVO getPageVO(String pg, int total){
        int currentPage = getCurrentPage(pg);
        int start = getLimitStart(currentPage);
        int lastPage = getLastPageNum(total);
        int pageStartNum = getPageStartNum(total, start);
        int groups[] = getPageGroup(currentPage, lastPage);

        return new PageVO(groups[0], groups[1], currentPage, lastPage, start);
    }
}
