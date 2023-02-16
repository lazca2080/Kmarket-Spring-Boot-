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
	
	private String prodNo;
	private String prodCate1;
	private String prodCate2;
	private String prodName;
	private String descript;
	private String company;
	private String seller;
	private int price;
	private int discount;
	private int sellPrice;
	private int point;
	private int stock;
	private int sold;
	private int delivery;
	private int hit;
	private int score;
	private int review;
	private MultipartFile newThumb1;
	private MultipartFile newThumb2;
	private MultipartFile newThumb3;
	private MultipartFile newDetail;
	private String status;
	private String duty;
	private String receipt;
	private String bizType;
	private String origin;
	private String ip;
	private String rdate;
	
	// 추가
	private String thumb1;
	private String thumb2;
	private String thumb3;
	private String detail;
	private String type;
	private int total;
	private int count;

	// 추가2
	private String searchType;
	private String keyWord;
}
