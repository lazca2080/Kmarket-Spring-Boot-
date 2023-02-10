package kr.co.kmarket.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.ProductDAO;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO dao;
	
	// index 상품 분류
	public Map<String, List<ProductVO>> selectIndex(){
		List<ProductVO> index = dao.selectIndex();
		return index.stream().collect(Collectors.groupingBy(ProductVO::getType));
	}

	// aside 카테고리 분류
	public Map<String, List<CateVO>> selectCate() {
		List<CateVO> cate = dao.selectCate();
		return cate.stream().collect(Collectors.groupingBy(CateVO::getCate1));
	}
	
	// 상품 카테고리에 맞는 상품 리스트
	public List<ProductVO> selectProducts(String prodCate1, String prodCate2){
		return dao.selectProducts(prodCate1, prodCate2);
	}
	
	// 상품번호에 맞는 상품 불러오기
	public ProductVO selectProduct(String prodNo) {
		return dao.selectProduct(prodNo);
	}
}