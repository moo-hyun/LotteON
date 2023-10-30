package co.kr.lotteon.entity;

import co.kr.lotteon.dto.LtCsFaqDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lt_cs_faq")
public class LtCsFaqEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int faqNo;

	//	private int cate1;
	//	private int cate2;

	// 방향성을 고려해서 Faq에서 cate1를 참조해야 Faq를 조회할때 Cate1가 동시에 참조됨
	// @JoinColumn 선언으로 생성되는 컬럼명을 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cate1", insertable = false, updatable = false)
	private LtCsCate1Entity ltCsCate1Entity;

	@Column(name = "cate1")
	private int cate1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({@JoinColumn( name = "cate1",  referencedColumnName = "cate1", insertable = false, updatable = false),
			@JoinColumn(name="cate2", referencedColumnName = "cate2", insertable = false, updatable = false)}
	)
	private LtCsCate2Entity ltCsCate2Entity;

	@Column(name = "cate2")
	private int cate2;

	private String title;
	private String content;
//	private int relatedFaq;
//	private String writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer", insertable = false, updatable = false)
	private LtMemberEntity ltMemberEntity;
	@Column(name = "writer")
	private String writer;


	private String regip;
	@CreationTimestamp
	private LocalDateTime rdate;

	private int hit;

	public String getRdateSub() {
		String formatDate
				//= LocalDateTime.now()
				= rdate
				.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
		//return LocalDateTime.parse(formatDate);
		return formatDate;
	}

	public LtCsFaqDTO toDTO() {
		return LtCsFaqDTO.builder()
				.faqNo(faqNo)
				.cate1(cate1)
				.cate2(cate2)
				.title(title)
				.content(content)
//				.relatedFaq(relatedFaq)
				.writer(writer)
				.regip(regip)
				.rdate(rdate)
				.c1Name(ltCsCate1Entity.getC1Name())
				.c2Name(ltCsCate2Entity.getC2Name())
				.rdateSub(this.getRdateSub())
				.build();
	}

}
