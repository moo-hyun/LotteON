package co.kr.lotteon.entity;

import co.kr.lotteon.dto.LtProductCate1DTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lt_product_cate1")
public class LtProductCate1Entity {

    @Id
    private int cate1 ;
    private String c1Name;

    public LtProductCate1DTO toDTO() {
        return LtProductCate1DTO.builder()
                .cate1(cate1)
                .c1Name(c1Name)
                .build();
    }

}
