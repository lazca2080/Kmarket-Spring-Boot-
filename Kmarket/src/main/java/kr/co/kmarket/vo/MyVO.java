package kr.co.kmarket.vo;

import java.util.List;

import groovy.transform.ToString;
import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MyVO {
	
	private OrderVO order;
	
	private List<PointVO> point;
	
	private List<ReviewVO> review;
	
	private List<CsVO> cs;
	
	private ProductVO product;
}
