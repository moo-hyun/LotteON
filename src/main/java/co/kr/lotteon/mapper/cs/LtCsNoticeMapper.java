package co.kr.lotteon.mapper.cs;

import co.kr.lotteon.dto.LtCsNoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LtCsNoticeMapper {


    //noticeList 전체
    List<LtCsNoticeDTO> selectCsNoticeListAll(int start);
    //noticeList cate1 참조
    List<LtCsNoticeDTO> selectCsNoticeListCate(int cate1, int start);

    //notice 전체
    public int selectCsNoticeTotal();
    //notice cate1 참조
    public int selectCsNoticeTotalCate(int cate1);

    public LtCsNoticeDTO selectCSNoticeView(int noticeNo);


}
