package kr.co.kmarket.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CsVO {
	private String no;
	private String uid;
	private int parent;
	private String cateType1;
	private String cateType2;
	private String title;
	private String content;
	private String regip;
	private String rdate;
	private int hit;
	private String reply;

	// 추가
	private int pg;
}
