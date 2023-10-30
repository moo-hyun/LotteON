package co.kr.lotteon.entity;

import co.kr.lotteon.dto.LtCsCate2DTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@IdClass(LtCsCatePK.class)
@Table(name = "lt_cs_cate2")
public class LtCsCate2Entity {
	//@Id
	//private int cate2No;

	@EmbeddedId
	LtCsCatePK ltCsCatePK;

	//private int cate1;
	//private int cate2;
/*
	@Id
	//@Column(name="cate1")
	//private int cate1;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cate1", insertable = false, updatable = false)
	private LtCsCate1Entity ltCsCate1Entity;

	@Column(name = "cate1")
	private int cate1;

	@Id
	@Column(name="cate2")
	private int cate2;
*/

//	@EmbeddedId
//	private LtCsCatePK ltCsCatePK;

	private String c2Name;
  
	public LtCsCate2DTO toDTO(){
		return LtCsCate2DTO.builder()
				.cate1(ltCsCatePK.getCate1())
				.cate2(ltCsCatePK.getCate2())
				//.cate1(cate1)
				//.cate2(cate2)
				.c2Name(c2Name)
				.build();
	}
}
