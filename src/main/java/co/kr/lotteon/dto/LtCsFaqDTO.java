package co.kr.lotteon.dto;

import co.kr.lotteon.entity.LtCsFaqEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtCsFaqDTO {
	private int faqNo;
	private int cate1;
	private int cate2;
	private String title;
	private String content;
	private int relatedFaq;
	private String writer;
	private String regip;
//	private String rdate;

	private LocalDateTime rdate;

	private int hit;
	//추가필드 
	private String c1Name;
	private String c2Name;
	private String rdateSub;

	public LtCsFaqEntity toEntity() {
		return LtCsFaqEntity.builder()
				.faqNo(faqNo)
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
