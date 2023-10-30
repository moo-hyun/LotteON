package co.kr.lotteon.entity;

import co.kr.lotteon.dto.LtCsNoticeDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
@Getter
@Setter
/*
 * @ToString에서 exclude 속성을 통한 무한참조(StackOverflow) 에러를 방지
 * toString() 메서드에서 양방향 참조를 모두 처리하면 무한순환 참조가 발생 할 수 있기 때문에
 * 어느 한쪽에서만 참조할 수 있게 exclude를 해줘야됨
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lt_cs_notice")
public class LtCsNoticeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int noticeNo;
	

	//private int cate1;
	//private int cate2;

	// 방향성을 고려해서 Notice에서 cate1를 참조해야 Notice를 조회할때 Cate1가 동시에 참조됨
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
//	private String writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer", insertable = false, updatable = false)
	private LtMemberEntity ltMemberEntity;
	@Column(name = "writer")
	private String writer;


	private String regip;
	@CreationTimestamp
	private LocalDateTime rdate;
	public String getRdateSub() {
		String formatDate
				//= LocalDateTime.now()
				= rdate
				.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
		//return LocalDateTime.parse(formatDate);
		return formatDate;
	}
	public LtCsNoticeDTO toDTO(){
		return LtCsNoticeDTO.builder()
				.noticeNo(noticeNo)
				.cate1(cate1)
				.cate2(cate2)
				.title(title)
				.content(content)
				.writer(writer)
				.regip(regip)
				.rdate(rdate)
				.c1Name(ltCsCate1Entity.getC1Name())
				.c2Name(ltCsCate2Entity.getC2Name())
				.rdateSub(this.getRdateSub())
				.build();
	}

}

