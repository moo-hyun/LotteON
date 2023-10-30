package co.kr.lotteon.dto.mypage;

import co.kr.lotteon.dto.LtProductOrderDTO;
import co.kr.lotteon.dto.LtProductOrderItemDTO;
import co.kr.lotteon.entity.LtProductOrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrdersDTO {
    LtProductOrderDTO orderDTO;
    List<LtProductOrderItemDTO> itemList;
}
