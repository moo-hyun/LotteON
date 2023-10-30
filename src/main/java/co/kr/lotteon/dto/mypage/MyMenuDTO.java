package co.kr.lotteon.dto.mypage;

import co.kr.lotteon.dto.LtMemberDTO;
import co.kr.lotteon.dto.LtProductOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MyMenuDTO {
    private int orderNowCount;
    private int point;
    private int myQnaCount;
}
