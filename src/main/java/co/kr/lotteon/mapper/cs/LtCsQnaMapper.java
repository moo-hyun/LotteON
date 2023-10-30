package co.kr.lotteon.mapper.cs;

import co.kr.lotteon.dto.LtCsQnaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface LtCsQnaMapper {

    public void insertQnaWrite(LtCsQnaDTO dto);


    List<LtCsQnaDTO> selectCsQnaListAll(int start);

    List<LtCsQnaDTO> selectCsQnaListCate(int cate1 , int start);

    public int selectCsQnaTotalCate(int cate1);

    public int selectCsQnaTotal();

    public LtCsQnaDTO selectCsQnaView(int qnaNo);

    public LtCsQnaDTO selectCsAdminQnaView(int qnaNo);

    public LtCsQnaDTO selectCsQnaBoard(int qnaNO);

    public void updateQnaBoard(LtCsQnaDTO dto);

    public void deleteQnaBoard(int qnaNo);

    public LtCsQnaDTO selectCsQnaChildBoard(int qnaNo);

}
