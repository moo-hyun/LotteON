package co.kr.lotteon.mapper.admin;

import co.kr.lotteon.dto.LtCsNoticeDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LtAdminCsMapper {

    public void updateAdminNotice(LtCsNoticeDTO dto);

}
