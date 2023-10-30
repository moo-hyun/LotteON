package co.kr.lotteon.dto;

import co.kr.lotteon.entity.LtCsCate2Entity;
import co.kr.lotteon.entity.LtCsCatePK;
import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LtCsCate2DTO {

	private int cate1;
	private int cate2;
	private String c2Name;

	public LtCsCate2Entity toEntity(){
		return LtCsCate2Entity.builder()
				.ltCsCatePK(new LtCsCatePK(cate1, cate2))
				//.cate1(cate1)
				//.cate2(cate2)
				.c2Name(c2Name)
				.build();
	}
}
