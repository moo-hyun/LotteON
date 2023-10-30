package co.kr.lotteon.repository;

import co.kr.lotteon.entity.LtProductCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LtProductCartRepository extends JpaRepository<LtProductCartEntity, Integer> {


    public int countByUidAndProdNo(String uid, int prodNo);

    public List<LtProductCartEntity> findAllByUid(String uid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE lt_product_cart m SET m.count = m.count + :count, m.total = m.total + :sumPrice where m.uid = :uid and m.prodNo = :prodNo", nativeQuery = true)
    public void updateCountByUidAndProdNo(@Param("uid") String uid, @Param("count")int count, @Param("sumPrice") int sumPrice, @Param("prodNo") int prodNo);

}
