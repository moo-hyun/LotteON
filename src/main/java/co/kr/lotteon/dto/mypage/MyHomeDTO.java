package co.kr.lotteon.dto.mypage;

import co.kr.lotteon.dto.*;
import co.kr.lotteon.entity.LtMemberPointEntity;
import co.kr.lotteon.entity.LtProductOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class MyHomeDTO {
    private LtProductOrderDTO order;
    private List<LtProductOrderItemDTO> orderList;
    private LtMemberPointDTO point;
    private List<LtProductReviewDTO> reviewList;
    private List<LtCsQnaDTO> qnaList;

    private LtMemberDTO member;
}
