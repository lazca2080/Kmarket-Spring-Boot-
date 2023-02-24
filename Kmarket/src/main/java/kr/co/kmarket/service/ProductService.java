package kr.co.kmarket.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.kmarket.dao.ProductDAO;
import kr.co.kmarket.repository.ProductRepo;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.OrderVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public List<ProductVO> selectProducts(String prodCate1, String prodCate2, String sort, int start){
		return dao.selectProducts(prodCate1, prodCate2, sort, start);
	}
	
	// 상품번호에 맞는 상품 불러오기
	public ProductVO selectProduct(String prodNo) {
		return dao.selectProduct(prodNo);
	}
	
	// 장바구니 등록
	public int insertCart(ProductVO vo) {
		return dao.insertCart(vo);
	}
	
	// 장바구니 목록
	public List<ProductVO> selectCarts(String uid){
		return dao.selectCarts(uid);
	}
	
	// 장바구니 선택 삭제
	public int deleteCart(List<String> checkList, String uid) {
		return dao.deleteCart(checkList, uid);
	}
	
	// 선택 상품 주문 목록
	public List<ProductVO> selectCartOrder(List<String> checkList, String uid) {
		return dao.selectCartOrder(checkList, uid);
	}
	
	// 장바구니 전체 선택 가격
	public ProductVO selectCartTotal(String uid) {
		return dao.selectCartTotal(uid);
	}
	
	// 주문하기
	public int insertComplete(OrderVO vo) {
		return dao.insertComplete(vo);
	}
	
	// 주문 상품 삽입
	public int insertCompleteItem(int ordNo, String uid, List<String> checkList) {
		return dao.insertCompleteItem(ordNo, uid, checkList);
	}
	
	// 포인트 적입
	public int insertCompletePoint(int ordNo, String uid, int point) {
		return dao.insertCompletePoint(ordNo, uid, point);
	};
	
	// 장바구니 주문 상품 삭제
	public int deleteCompleteCart(String uid, List<String> checkList) {
		return dao.deleteCompleteCart(uid, checkList);
	}
	
	// 검색
	public List<ProductVO> searchProduct(String keyWord, String sort, int start) {
		return dao.searchProduct(keyWord, sort, start);
	}
	
	// 2차 검색
	public List<ProductVO> secondSearch(String min, String max, String chk, String search, String firstSearch, String sort, int start) {
		return dao.secondSearch(min, max, chk, search, firstSearch, sort, start);
	}
	
	
	// 페이징
	// 페이지 시작값
	public int getLimitStart(int currentPage) {
		return (currentPage - 1) * 10;
	}
	
	// 현재 페이지
	public int getCurrentPage(String pg) {
		int currentPage = 1;
		
		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		return currentPage;
	}
	
	// 전체 개수
	public int getTotalCount(String prodCate1, String prodCate2) {
		return dao.getTotalCount(prodCate1, prodCate2);
	}
	
	// 검색 개수
	public int getTotalSearchCount(String keyWord, String sort) {
		return dao.getTotalSearchCount(keyWord, sort);
	}
	
	// 2차 검색 개수
	public int getTotalSecondSearchCount(String min, String max, String chk, String search, String firstSearch, String sort) {
		return dao.getTotalSecondSearchCount(min, max, chk, search, firstSearch, sort);
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
	
	// 페이지 시작번호
	public int getPageStartNum(long total, int start) {
		return (int) (total - start);
	}
	
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPage) {
		int groupCurrent = (int) Math.ceil(currentPage / 10.0);
		int groupStart   = (groupCurrent-1)*10 + 1;
		int groupEnd = groupCurrent*10;
		
		if(groupEnd > lastPage) {
			groupEnd = lastPage;
		}
		
		int[] groups = {groupStart, groupEnd};
		
		return groups;
	}
}
