package co.kr.lotteon.dto.mypage;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPageRequestDTO {

    @Builder.Default
    private int pg=1;

    @Builder.Default
    private int size=5;

    @Builder.Default
    private String how = "DESC";

    @Builder.Default
    private String sort = "";
    // list 조건 검색
    @Builder.Default
    private String searchType = "";
    @Builder.Default
    private String searchKeyword = "";
    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate = LocalDate.of(1,1,1);
    private LocalDate endDate = LocalDate.of(9999,9,9);

    public Pageable getPageable(String sort, int size){
        if (how.equals("ASC")) {
            return PageRequest.of(this.pg - 1, size, Sort.by(sort).ascending());
        } else {
            return PageRequest.of(this.pg - 1, size, Sort.by(sort).descending());
        }
    }
}
