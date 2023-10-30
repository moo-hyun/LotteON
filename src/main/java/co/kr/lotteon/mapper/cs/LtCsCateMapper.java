package co.kr.lotteon.mapper.cs;

import co.kr.lotteon.dto.LtCsCate1DTO;
import co.kr.lotteon.dto.LtCsCate2DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LtCsCateMapper {

    public List<LtCsCate1DTO> selectCsCate1();

    public List<LtCsCate2DTO>selectCsCate2(int dto);



}
