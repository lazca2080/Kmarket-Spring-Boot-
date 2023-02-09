package kr.co.kmarket.vo;

import org.springframework.web.multipart.MultipartFile;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductVO {
	
	private String prodCate1;
	private String prodCate2;
	private String prodName;
	private String descript;
	private String company;
	private String seller;
	private String price;
	private String discount;
	private String sellPrice;
	private String point;
	private String stock;
	private String sold;
	private String delivery;
	private String hit;
	private String score;
	private String review;
	private MultipartFile thumb1;
	private MultipartFile thumb2;
	private MultipartFile thumb3;
	private MultipartFile detail;
	private String status;
	private String duty;
	private String receipt;
	private String bizType;
	private String origin;
	private String ip;
	private String rdate;
	
	// 추가
	
	private String newThumb1;
	private String newThumb2;
	private String newThumb3;
	private String newDetail;
}
