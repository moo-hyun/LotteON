package co.kr.lotteon.entity;

import co.kr.lotteon.dto.LtCsQnaDTO;
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
@Table(name = "lt_cs_qna")
public class LtCsQnaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int qnaNo;

//	private int cate1;
//	private int cate2;

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
	private String file1;
	private String file2;
	private String file3;
	private String file4;
//	private String writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer", insertable = false, updatable = false)
	private LtMemberEntity ltMemberEntity;
	@Column(name = "writer")
	private String writer;


	private String ordNo; //null 값 넣기 위해 String으로 변경 
	private String prodNo;
	private int parent;
	private int answerComplete;
	private String regip;

	@CreationTimestamp
	private LocalDateTime rdate;

	public String getRdateSub() {
		String formatDate = rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
		return formatDate;
	}

	public LtCsQnaDTO toDTO() {
		return LtCsQnaDTO.builder()
				.qnaNo(qnaNo)
				.cate1(cate1)
				.cate2(cate2)
				.title(title)
				.content(content)
				.file1(file1)
				.file2(file2)
				.writer(writer)
				.ordNo(ordNo)
				.prodNo(prodNo)
				.parent(parent)
				.answerComplete(answerComplete)
				.regip(regip)
				.rdate(rdate)
				.c1Name(ltCsCate1Entity.getC1Name())
				.c2Name(ltCsCate2Entity.getC2Name())
				.build();
	}

}
