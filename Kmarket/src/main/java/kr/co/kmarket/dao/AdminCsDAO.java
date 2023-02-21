package kr.co.kmarket.dao;

import kr.co.kmarket.vo.CsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminCsDAO {

    public List<CsVO> adminCSnotice(int start);
    public List<CsVO> adminCSfaq(int start);
    public List<CsVO> adminCSqna(int start);
	public int selectCountTotal();

}
