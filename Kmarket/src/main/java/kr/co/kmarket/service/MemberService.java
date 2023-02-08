package kr.co.kmarket.service;

import kr.co.kmarket.dao.MemberDAO;
import kr.co.kmarket.vo.TermsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberDAO dao;

    public TermsVO selectTerms(){
        return dao.selectTerms();
    }
}
