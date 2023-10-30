package co.kr.lotteon.dto;

import co.kr.lotteon.entity.LtProductEntity;
import co.kr.lotteon.entity.LtProductReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtProductReviewDTO {
    private int revNo;
    private int ordNo;
    private int prodNo;
    private String content;
    private String uid;
    private int rating;
    private String regIp;
    private LocalDateTime rDate;

    //추가필드
    private String prodName;
    private String subRDate;
    private String uidMasking;
    public LtProductReviewEntity toEntity(){
        return LtProductReviewEntity.builder()
                .revNo(revNo)
                .ordNo(ordNo)
                .prodNo(prodNo)
                .ltProductEntity(LtProductEntity.builder().prodNo(prodNo).prodName(prodName).build())
                .content(content)
                .uid(uid)
                .rating(rating)
                .regIp(regIp)
                .rdate(rDate)
                .build();

    }
}
