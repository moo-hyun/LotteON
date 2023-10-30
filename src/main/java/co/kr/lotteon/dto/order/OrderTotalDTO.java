package co.kr.lotteon.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class OrderTotalDTO {
    private int totalCount;
    private int totalPrice;
    private int totalDiscount;
    private int totalDelivery;
    private int totalPoint;
    private int totalSumPrice;
}
