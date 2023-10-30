package co.kr.lotteon.entity;

import co.kr.lotteon.dto.LtProductOrderItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.text.DecimalFormat;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lt_product_order_item")
public class LtProductOrderItemEntity {

    @EmbeddedId
    LtProductOrderItemPK ltProductOrderItemPK;
    private int count;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    private int hasReview;



    public LtProductOrderItemDTO toDTO(){
        return LtProductOrderItemDTO.builder()
                .ordNo(ltProductOrderItemPK.getOrdNo())
                .prodNo(ltProductOrderItemPK.getLtProductEntity().getProdNo())
                .count(count)
                .price(price)
                .discount(discount)
                .point(point)
                .delivery(delivery)
                .hasReview(hasReview)
                .discountPrice(price-((price*discount/100/10)*10))
                .discountPriceTotal((price-((price*discount/100/10)*10))*count+delivery)
                .thumb1(ltProductOrderItemPK.getLtProductEntity().getThumb1())
                .descript(ltProductOrderItemPK.getLtProductEntity().getDescript())
                .prodName(ltProductOrderItemPK.getLtProductEntity().getProdName())
                .company(ltProductOrderItemPK.getLtProductEntity().getCompany())
                .total(total)
        .build();
    }




}
