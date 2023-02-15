package kr.co.kmarket.vo;

import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberVO {
    private String uid;
    private String pass;
    private String pass1;
    private String pass2;
    private String name;
    private int gender;
    private String hp;
    private String email;
    private int type;
    private int point;
    private int level;
    private String zip;
    private String addr1;
    private String addr2;
    private String regip;
    private String wdate;
    private String rdate;

    private String company;
    private String ceo;
    private String bizRegNum;
    private String cornRegNum;
    private String tel;
    private String manager;
    private String managerHp;
    private String fax;
    private int etc1;
    private int etc2;
    private String etc3;
    private String etc4;
    private String etc5;
    
    // 추가
    private String payment;
    private String orderer;
}
