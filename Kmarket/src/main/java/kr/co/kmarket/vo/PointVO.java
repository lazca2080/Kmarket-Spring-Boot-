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
public class PointVO {
	
	private int pointNo;
	private String uid;
	private String type;
	private int ordNo;
	private int point;
	private String descript;
	private String pointDate;
	private String pointExpired;
	
	private int prodNo;
}
