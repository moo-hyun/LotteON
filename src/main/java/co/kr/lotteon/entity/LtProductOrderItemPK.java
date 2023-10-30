package co.kr.lotteon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LtProductOrderItemPK implements Serializable {
    @Column(name = "ordNo")
    private int ordNo;

/*
    @Column(name = "prodNo")
    private int prodNo;
*/

    // 방향성을 고려해서 Itemd에서 Product를 참조해야 Item를 조회할때 Product가 동시에 참조됨
    // @JoinColumn 선언으로 생성되는 컬럼명을 설정
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="prodNo", insertable=false, updatable=false)
    private LtProductEntity ltProductEntity;

    @Override
    public boolean equals(Object o) {return false;  }

    @Override
    public int hashCode() {  return 0;}
}
