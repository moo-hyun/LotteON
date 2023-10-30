package co.kr.lotteon.mapper.cs;

import co.kr.lotteon.dto.LtCsFaqDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface LtCsFaqMapper {

    List<LtCsFaqDTO> selectCsFaqList10(int cate1);

    public LtCsFaqDTO selectCsFaqView(int faqNo);

}
