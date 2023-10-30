package co.kr.lotteon.entity;

import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.repository.LtProductOrderItemRepository;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.text.DecimalFormat;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "lt_product")
public class LtProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodNo;
    private int prodCate1;
    private int prodCate2;
    private String prodName;
    private String descript;
    private String company;
    private String seller;
    private int price;
    private int discount;
    private int point;
    private int stock;
    private int sold;
    private int delivery;
    private int hit;
    private int score;
    private int review;
    private String thumb1;
    private String thumb2;
    private String thumb3;
    private String detail;
    private String status;
    private String duty;
    private String receipt;
    private String bizType;
    private String origin;
    private String ip;

    @Column(name = "rdate")
    @CreationTimestamp
    private LocalDateTime regDate;
    private int isRemoved;
    private Integer etc1;
    private Integer etc2;
    private String etc3;
    private String etc4;
    private String etc5;

    private String wDate;

    /*
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="prodNo", insertable=false, updatable=false)
    private LtProductOrderItemEntity ltProductOrderItemEntity;
*/
    private String getDeciFormat(int number){

        DecimalFormat deciFormat = new DecimalFormat("###,###");
        return deciFormat.format(number);
    }

    private int getDiscountPrice(){
        return (price*discount/100/10)*10;
    }


    public LtProductDTO toDTO() {
        return LtProductDTO.builder()
                .prodNo(prodNo)
                .prodCate1(prodCate1)
                .prodCate2(prodCate2)
                .prodName(prodName)
                .descript(descript)
                .company(company)
                .seller(seller)
                .price(price)
                .discount(discount)
                .point(point)
                .stock(stock)
                .sold(sold)
                .delivery(delivery)
                .hit(hit)
                .score(score)
                .review(review)
                .thumb1(thumb1)
                .thumb2(thumb2)
                .thumb3(thumb3)
                .detail(detail)
                .status(status)
                .duty(duty)
                .receipt(receipt)
                .bizType(bizType)
                .origin(origin)
                .ip(ip)
                .rDate(regDate)
                .wDate(wDate)
                .isRemoved(isRemoved)
                .etc1(etc1+"")
                .etc2(etc2+"")
                .etc3(etc3)
                .etc4(etc4)
                .etc5(etc5)
                .priceSub(getDeciFormat(price))
                .deliverySub(getDeciFormat(delivery))
                .discountPrice(getDiscountPrice())
                .discountPriceSub(getDeciFormat(getDiscountPrice()))
                .finalPriceSub(getDeciFormat(price - getDiscountPrice() ))
                .build();
    }

    public LtProductOrderItemEntity toOrderItemEntity(int ordNo, int prodNo, int count, int total){
        return LtProductOrderItemEntity.builder()
                .ltProductOrderItemPK(new LtProductOrderItemPK(ordNo, this))
                .count(count)
                .price(price)
                .discount(discount)
                .point(point)
                .delivery(delivery)
                .total(total)
                .build();
    }

}

