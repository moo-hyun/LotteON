package co.kr.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lt_cs_faq_rate")
public class LtCsFaqRateEntity {
	@Id
	private int faqNo;
	@Id
	private String uid;
	private int rate;
	
	
}
