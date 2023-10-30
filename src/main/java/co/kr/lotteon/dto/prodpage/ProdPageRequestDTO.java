package co.kr.lotteon.dto.prodpage;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class ProdPageRequestDTO {

    @Builder.Default
    private int pg = 1;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private String how = "DESC";

    @Builder.Default
    private String sort = "regDate";

    // cate Default 없으면 오류 발생 - Default 값을 지정해줘야함.
    @Builder.Default
    private int cate1 = 10;
    @Builder.Default
    private int cate2 = 10;

    //admin list 조건 검색
    @Builder.Default
    private String searchType = "";
    @Builder.Default
    private String searchKeyword = "";
    @Builder.Default
    private int searchProdNo = 0;

    //list 조건 검색
    @Builder.Default
    private String isCategory = "Y";
    @Builder.Default
    private String search = "";

    public Pageable getPageable(String cate1) {
        if (how.equals("ASC")) {
            return PageRequest.of(this.pg - 1, this.size, Sort.by(this.sort).ascending());
        } else {
            return PageRequest.of(this.pg - 1, this.size, Sort.by(this.sort).descending());
        }
    }
    public Pageable getPageable(String sort, String how) {
        if (how.equals("ASC")) {
            return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).ascending());
        } else {
            return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
        }
    }


}
