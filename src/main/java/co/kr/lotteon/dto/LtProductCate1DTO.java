package co.kr.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtProductCate1DTO {

    private int cate1 ;
    private String c1Name;

    private List<LtProductCate2DTO> cate2s;

}
