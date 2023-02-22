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
public class OrderVO {
	
	private int ordNo;
	private String ordUid;
	private int ordCount;
	private int ordPrice;
	private int ordDiscount;
	private int ordDelivery;
	private int savePoint;
	private int usedPoint;
	private int ordTotPrice;
	private String recipName;
	private String recipZip;
	private String recipHp;
	private String recipAddr1;
	private String recipAddr2;
	private String ordPayment;
	private String ordComplete;
	private String ordDate;
	private int deliStatus;
	private int purConfirm;
	
	// 추가
	private String prodName;
	private String company;
	private String prodNo;
	private String thumb2;
	private String uid;
	private String prodCate1;
	private String prodCate2;
	private int count;
	private int discount;
	private int point;
	private int delivery;
	private int total;
	private int price;
	
	
}
