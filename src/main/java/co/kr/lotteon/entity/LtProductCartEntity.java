package co.kr.lotteon.entity;

import co.kr.lotteon.dto.LtProductCartDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.text.DecimalFormat;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lt_product_cart")
public class LtProductCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", insertable = false, updatable = false)
    private LtMemberEntity ltMemberEntity;

    @Column(name = "uid")
    private String  uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodNo", insertable = false, updatable = false)
    private LtProductEntity ltProductEntity;
    @Column(name="prodNo")
    private int prodNo;

    private int count;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    @CreationTimestamp
    private String rDate;

    private String getDeciFormat(int number){

        DecimalFormat deciFormat = new DecimalFormat("###,###");
        return deciFormat.format(number);
    }

    public LtProductCartDTO toDTO() {
        return LtProductCartDTO.builder()
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
                .totalSub(getDeciFormat(total))
                .product(ltProductEntity.toDTO())
                .user(ltMemberEntity.toDTO())
                .build();
    }

}
