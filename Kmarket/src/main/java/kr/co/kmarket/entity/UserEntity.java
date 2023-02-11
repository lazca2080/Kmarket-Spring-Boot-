package kr.co.kmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "km_member")
public class UserEntity {

    @Id
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


}
