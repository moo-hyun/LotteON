package co.kr.lotteon.mapper.cs;

import co.kr.lotteon.dto.LtCsNoticeDTO;
import co.kr.lotteon.dto.LtCsQnaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LtCsIndexMapper {

    public List<LtCsNoticeDTO> selectCsNotices();

    public List<LtCsQnaDTO> selectCsQna();


}
