package kr.co.kmarket.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.ProductDAO;
import kr.co.kmarket.vo.ProductVO;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO dao;
	
	public Map<String, List<ProductVO>> selectIndex(){
		
		List<ProductVO> index = dao.selectIndex();
		
		return index.stream().collect(Collectors.groupingBy(ProductVO::getType));
	}

}
