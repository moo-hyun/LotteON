package co.kr.lotteon.entity;

import co.kr.lotteon.dto.LtMemberPointDTO;
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
@Table(name = "lt_member_point")
public class LtMemberPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointNo;
    private String uid;
    private int ordNo;
    private int revNo;
    private int point;
    @CreationTimestamp
    private LocalDateTime pointDate;

    public LtMemberPointDTO toDTO(){
        return  LtMemberPointDTO.builder()
                .pointNo(pointNo)
                .uid(uid)
                .ordNo(ordNo)
                .revNo(revNo)
                .point(point)
                .pointDate(pointDate)
                .subPointDate(pointDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .subExpiredDate(pointDate.plusMonths(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }

}
