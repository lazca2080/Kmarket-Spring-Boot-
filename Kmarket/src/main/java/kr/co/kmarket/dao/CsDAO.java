package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmarket.vo.CsVO;

@Repository
@Mapper
public interface CsDAO {
	public List<CsVO> selectCs();
}
