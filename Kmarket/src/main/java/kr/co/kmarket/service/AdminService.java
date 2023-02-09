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
	
	// 상품 등록
	public void register(ProductVO vo) {
		
		MultipartFile thumb1 = vo.getThumb1();
		MultipartFile thumb2 = vo.getThumb2();
		MultipartFile thumb3 = vo.getThumb3();
		MultipartFile detail = vo.getDetail();
		
		// 파일 업로드
		ProductVO file = fileUpload(thumb1, thumb2, thumb3, detail, vo);
		vo.setNewThumb1(file.getNewThumb1());
		vo.setNewThumb2(file.getNewThumb2());
		vo.setNewThumb3(file.getNewThumb3());
		vo.setNewDetail(file.getNewDetail());
		
		dao.register(vo);
		
	}
	
	// 카테고리 분류
	public List<CateVO> selectCate1(){
		return dao.selectCate1();
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
		
		log.info("path"+path);
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
        File checkFolder = new File(path);
        if(!checkFolder.exists()){
            try {
				Files.createDirectories(checkFolder.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProductVO file = new ProductVO();
		file.setNewThumb1(newThumb1);
		file.setNewThumb2(newThumb2);
		file.setNewThumb3(newThumb3);
		file.setNewDetail(newDetail);
		
		return file;
		
	}
}
