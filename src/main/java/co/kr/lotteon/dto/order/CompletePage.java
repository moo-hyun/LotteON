package co.kr.lotteon.dto.order;

import co.kr.lotteon.dto.LtProductOrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletePage{
    List<LtProductOrderItemDTO> dtoList;
    int ordNo;
}