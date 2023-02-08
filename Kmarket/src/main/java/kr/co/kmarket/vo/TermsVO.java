package kr.co.kmarket.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermsVO {
    private String terms;
    private String privacy;
    private String location;
    private String finance;
    private String tax;
}
