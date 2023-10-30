package co.kr.lotteon.dto;

import co.kr.lotteon.entity.LtCsNoticeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LtCsNoticeDTO {
	private int noticeNo;
	private int cate1;
	private int cate2;
	private String title;
	private String content;
	private String writer;
	private String regip;
	private LocalDateTime rdate;

	private int hit;
	//추가필드
	private String c1Name;
	private String c2Name;
	private String rdateSub;

	public LtCsNoticeEntity toEntity(){
		return LtCsNoticeEntity.builder()
				.noticeNo(noticeNo)
				.cate1(cate1)
				.cate2(cate2)
				.title(title)
				.content(content)
				.writer(writer)
				.regip(regip)
				.rdate(rdate)
				.build();
	}
}
