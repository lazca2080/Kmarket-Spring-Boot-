package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmarket.vo.CsVO;

@Repository
@Mapper
public interface CsDAO {
	// cs index 리스트
	public List<CsVO> selectCs();
	// cs notice 리스트
	public List<CsVO> selectNtList(String cateType1);
}

