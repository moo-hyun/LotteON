package co.kr.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtMemberPointDTO {
    private int pointNo;
    private String uid;
    private int ordNo;
    private int revNo;
    private int point;
    private LocalDateTime pointDate;
    private String subPointDate;
    private String subExpiredDate;

}
