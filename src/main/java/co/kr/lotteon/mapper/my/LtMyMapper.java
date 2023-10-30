package co.kr.lotteon.mapper.my;

import co.kr.lotteon.dto.LtCsQnaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface LtMyMapper {

    public List<LtCsQnaDTO> selectCsQnaComment(int qnaNo);

    public List<LtCsQnaDTO> selectMyQnaBoard(String writer, int start);

    public String selectMyQnaTotal(String writer);
}
