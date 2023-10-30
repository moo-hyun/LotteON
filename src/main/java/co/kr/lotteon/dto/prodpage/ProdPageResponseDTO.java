package co.kr.lotteon.dto.prodpage;

import co.kr.lotteon.dto.LtProductDTO;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProdPageResponseDTO {

    private List<LtProductDTO> dtoList;
    private int cate1;
    private int cate2;
    private int pg;
    private int size;
    private int total;
    private int start, end;
    private boolean prev, next;
    private String sort;
    private String how;
    private String searchType;
    private String searchKeyword;
    private int searchProdNo;

    private String isCategory;
    private String search;

    @Builder
    public ProdPageResponseDTO(ProdPageRequestDTO pageRequestDTO, List<LtProductDTO> dtoList, int total) {

        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.cate1 = pageRequestDTO.getCate1();
        this.cate2 = pageRequestDTO.getCate2();
        this.sort = pageRequestDTO.getSort();
        this.how = pageRequestDTO.getHow();
        this.dtoList = dtoList;
        this.isCategory = pageRequestDTO.getIsCategory();
        this.search = pageRequestDTO.getSearch();

        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;
        int last = (int) (Math.ceil(total / (double) size));

        this.end = Math.min(end, last);
        this.end = end == 0 ? 1 : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
        this.searchType=pageRequestDTO.getSearchType();
        this.searchKeyword=pageRequestDTO.getSearchKeyword();
        this.searchProdNo=pageRequestDTO.getSearchProdNo();

    }

}
