package co.kr.lotteon.dto;

import co.kr.lotteon.entity.LtProductCartEntity;
import co.kr.lotteon.entity.LtProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtProductCartDTO {

    private int cartNo;
    private String  uid;
    private int prodNo;
    private int count;

    /** 나중에 컬럼 삭제하자 **/
    private int price;
    private int discount;
    private int point;
    private int delivery;
    /** 나중에 컬럼 삭제하자 **/
    private int total;
    private String rDate;


    private LtProductDTO product;
    private LtMemberDTO user;
    private String totalSub;

    //추가필드
    private String prodName;
    private String descript;
    private String thumb1;
    private String deliverySub;
    private String priceSub;

    public LtProductCartEntity toEntity() {
        return LtProductCartEntity.builder()
                .cartNo(cartNo)
                .uid(uid)
                .prodNo(prodNo)
                .count(count)
                .price(price)
                .discount(discount)
                .point(point)
                .delivery(delivery)
                .total(total)
                .rDate(rDate)
                .build();
    }


}
