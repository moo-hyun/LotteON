package co.kr.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtMemberTermsDTO {
    private String buyer;
    private String privacy;
    private String location;
    private String finance;
    private String seller;

}
