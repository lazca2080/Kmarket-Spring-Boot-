package kr.co.kmarket.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.kmarket.dao.AdminDAO;
import kr.co.kmarket.vo.CateVO;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminService {
	
	@Autowired
	private AdminDAO dao;

	// (판매회원) 판매자 조회
	public List<ProductVO> selectProducts(int start,
										  String seller,
										  String searchType,
										  String keyword,
										  int level)
	{
		return dao.selectProducts(start, seller, searchType, keyword, level);
	}

	// 상품 등록
	public void register(ProductVO vo) {
		
		// vo에 있는 파일들 가져오기
		MultipartFile thumb1 = vo.getNewThumb1();
		MultipartFile thumb2 = vo.getNewThumb2();
		MultipartFile thumb3 = vo.getNewThumb3();
		MultipartFile detail = vo.getNewDetail();
		
		// 파일 업로드
		ProductVO file = fileUpload(thumb1, thumb2, thumb3, detail, vo);
		vo.setThumb1(file.getThumb1());
		vo.setThumb2(file.getThumb2());
		vo.setThumb3(file.getThumb3());
		vo.setDetail(file.getDetail());
		
		dao.register(vo);
	}
	
	// 카테고리 분류
	public List<CateVO> selectCate1(){
		return dao.selectCate1();
	}

	// 상품업데이트
	public int updateProduct(ProductVO vo){
		return dao.updateProduct(vo);
	}

	// 상품삭제
	public int deleteProduct(String prodNo){
		return dao.deleteProduct(prodNo);
	}

	public List<CateVO> selectCate2(@RequestParam String cate1) {
		return dao.selectCate2(cate1);
	}
	
	// 서비스----------------------------------------------------------------------------
	
	// 파일 경로
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;
	
	// 파일 업로드
	public ProductVO fileUpload(MultipartFile thumb1, MultipartFile thumb2, MultipartFile thumb3, MultipartFile detail, ProductVO vo) {
		
		// cate1, cate2
		String cate1 = vo.getProdCate1();
		String cate2 = vo.getProdCate2();
		
		// 시스템 경로
		String path = new File(uploadPath+cate1+"/"+cate2).getAbsolutePath();
		
		// 새 파일명 생성
		String oriThumb1 = thumb1.getOriginalFilename();
		String oriThumb2 = thumb2.getOriginalFilename();
		String oriThumb3 = thumb3.getOriginalFilename();
		String oriDetail = detail.getOriginalFilename();
		
		// 확장자
		String extThumb1 = oriThumb1.substring(oriThumb1.lastIndexOf("."));
		String extThumb2 = oriThumb2.substring(oriThumb2.lastIndexOf("."));
		String extThumb3 = oriThumb3.substring(oriThumb3.lastIndexOf("."));
		String extDetail = oriDetail.substring(oriDetail.lastIndexOf("."));
		
		// 새로운 이름
		String newThumb1 = cate1 + "-" + cate2 + "-" + UUID.randomUUID().toString() + extThumb1;
		String newThumb2 = cate1 + "-" + cate2 + "-" + UUID.randomUUID().toString() + extThumb2;
		String newThumb3 = cate1 + "-" + cate2 + "-" + UUID.randomUUID().toString() + extThumb3;
		String newDetail = cate1 + "-" + cate2 + "-" + UUID.randomUUID().toString() + extDetail;
		
		// 저장 폴더가 없다면 생성
		// 저장 폴더는 cate1 한번, cate2 한번 총 두번 이루어짐.
		// mkdir 같은 경우 없는 폴더 두번 생성이 불가능함
		// 따라서 Files를 이용해서 한번에 생성
        File checkFolder = new File(path);
        if(!checkFolder.exists()){
            try {
				Files.createDirectories(checkFolder.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		
		// 파일 저장
		try {
			thumb1.transferTo(new File(path, newThumb1));
			thumb2.transferTo(new File(path, newThumb2));
			thumb3.transferTo(new File(path, newThumb3));
			detail.transferTo(new File(path, newDetail));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ProductVO file = new ProductVO();
		file.setThumb1(newThumb1);
		file.setThumb2(newThumb2);
		file.setThumb3(newThumb3);
		file.setDetail(newDetail);
		
		return file;
	}


	// 페이지 시작값
	public int getLimitStart(int currentPage) {
		return (currentPage - 1) * 10;
	}

	// 현재 페이지
	public int getCurrentPage(@RequestParam("pg") String pg) {
		int currentPage = 1;

		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		return currentPage;
	}

	/***
	 * Level과 Seller(uid)로 따져서 admin, seller 구분
	 * */
	public int selectCountTotal(int level, String seller, String searchType, String keyword){
		return dao.selectCountTotal(level, seller, searchType, keyword);
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

	// 페이지 시작 번호
	public int getPageStartNum(long total, int start) {
		return (int) (total - start);
	}

	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPage) {

		int groupCurrent = (int) Math.ceil(currentPage / 10.0);
		int groupStart = (groupCurrent - 1) * 10 + 1;
		int groupEnd = groupCurrent * 10;

		if(groupEnd > lastPage) {
			groupEnd = lastPage;
		}

		int[] groups = {groupStart, groupEnd};

		return groups;
	}


}
