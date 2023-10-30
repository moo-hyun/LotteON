package co.kr.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lt_member_terms")
public class LtMemberTermsEntity {
    @Id
    private String buyer;
    private String privacy;
    private String location;
    private String finance;
    private String seller;
}
