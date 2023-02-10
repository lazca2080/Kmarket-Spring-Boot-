package kr.co.kmarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmarket.vo.ProductVO;

@Repository
@Mapper
public interface ProductDAO {
	
	public List<ProductVO> selectIndex();
}
