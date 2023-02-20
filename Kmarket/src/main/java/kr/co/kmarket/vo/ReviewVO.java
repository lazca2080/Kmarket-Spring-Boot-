package kr.co.kmarket.vo;

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
public class ReviewVO {
	
	private int revNo;
	private String prodNo;
	private String content;
	private String uid;
	private int rating;
	private String regip;
	private String rdate;
	
	// 추가
	private String prodName;
}
