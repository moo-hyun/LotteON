package co.kr.lotteon.dto;

import co.kr.lotteon.entity.LtCsCate1Entity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LtCsCate1DTO {
	
	private int cate1;
	private String c1Name;

	public LtCsCate1Entity toEntity(){
		return LtCsCate1Entity.builder()
				.cate1(cate1)
				.c1Name(c1Name)
				.build();
	}

}
