package co.kr.lotteon.dto;

import co.kr.lotteon.entity.LtProductEntity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtProductDTO {
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

    private MultipartFile fileThumb1;
    private MultipartFile fileThumb2;
    private MultipartFile fileThumb3;
    private MultipartFile fileDetail;

    private String status;
    private String duty;
    private String receipt;
    private String bizType;
    private String origin;
    private String ip;

    @Transient
    private String priceFromUser;

    @Transient
    private String pointFromUser;

    @Transient
    private String stockFromUser;

    @Transient
    private String deliveryFromUser;

    private LocalDateTime rDate;
    private int isRemoved;
    private String etc1; //null처리 위해 string
    private String etc2;
    private String etc3;
    private String etc4;
    private String etc5;

    private String wDate;
    private int total;
    private int rating; //별점

    private String path;
    private int level;



    private String c1Name;
    private String c2Name;


    //추가필드
    private String totalSub;
    private String deliverySub;
    private String priceSub;
    private int discountPrice;
    private String discountPriceSub;
    private String finalPriceSub;



    public LtProductEntity toEntity() {
        return LtProductEntity.builder()
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
/*                .thumb1(thumb1)
                .thumb2(thumb2)
                .thumb3(thumb3)
                .detail(detail)*/
                .status(status)
                .duty(duty)
                .receipt(receipt)
                .bizType(bizType)
                .origin(origin)
                .ip(ip)
                .regDate(rDate)
                .wDate(wDate)
                .isRemoved(isRemoved)
                .etc1(Integer.parseInt(etc1))
                .etc2(Integer.parseInt(etc2))
                .etc3(etc3)
                .etc4(etc4)
                .etc5(etc5)
                .build();
    }

}

