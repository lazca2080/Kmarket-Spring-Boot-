package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kmarket.vo.CsVO;

@Repository
@Mapper
public interface CsDAO {
	// cs index 리스트
	public List<CsVO> selectCs();
	// cs notice 리스트
	public List<CsVO> selectNtList(@Param("cateType1") String cateType1, @Param("start") int start);
	
	// 글 페이지
	public int selectCountTotal(String cateType1);
	
}

