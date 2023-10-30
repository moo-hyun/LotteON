package co.kr.lotteon.dto.mypage;


import co.kr.lotteon.dto.LtMemberPointDTO;
import co.kr.lotteon.dto.LtProductOrderDTO;
import co.kr.lotteon.dto.LtProductReviewDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MyPageResponseDTO {

    private List<LtMemberPointDTO> pointList;
    //private List<LtProductOrderDTO> orderList;
    //private MyOrdersDTO myOrdersDTO;
    private List<MyOrdersDTO> myOrdersDTOList;
    private List<LtProductReviewDTO> reviewList;

    private int pg;
    private int size;
    private int total;

    private int start, end;
    private boolean prev, next;

    private String sort;
    private String how;
    private String searchType;
    private String searchKeyword;
    private LocalDate beginDate;
    private LocalDate endDate;

    @Builder
    public MyPageResponseDTO(MyPageRequestDTO pageRequestDTO,
                             List<LtMemberPointDTO> pointList, int total
                                ,List<MyOrdersDTO> myOrdersDTOList
                                ,List<LtProductReviewDTO> reviewList){
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.sort = pageRequestDTO.getSort();
        this.how = pageRequestDTO.getHow();
        this.searchType = pageRequestDTO.getSearchType();
        this.searchKeyword = pageRequestDTO.getSearchKeyword();
        this.beginDate = pageRequestDTO.getBeginDate();
        this.endDate = pageRequestDTO.getEndDate();

        this.pointList = pointList;
        this.myOrdersDTOList = myOrdersDTOList;
        this.reviewList = reviewList;

        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;
        int last = (int)(Math.ceil(total / (double)size));

        this.end = end > last ? last:end;
        this.end = end == 0 ? 1 : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}
