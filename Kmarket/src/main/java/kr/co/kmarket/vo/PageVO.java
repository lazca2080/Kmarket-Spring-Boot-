package kr.co.kmarket.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PageVO {
    private int currentPage;
    private int lastPage;
    private int groupStart;
    private int groupEnd;
    private int start;
}
