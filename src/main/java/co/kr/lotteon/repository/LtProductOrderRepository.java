package co.kr.lotteon.repository;

import co.kr.lotteon.entity.LtProductOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LtProductOrderRepository extends JpaRepository<LtProductOrderEntity, Integer> {

    public Page<LtProductOrderEntity> findAllByOrdUid(String  ordUid, Pageable pageable);

    public LtProductOrderEntity findTop1ByOrdUidOrderByOrdDateDesc(String uid);
    @Modifying @Transactional
    @Query(value = "UPDATE  lt_product_order a " +
            " SET a.ordComplete = :ordComplete " +
            " WHERE a.ordNo= :ordNo ", nativeQuery = true)
    public void updateOrdComplete(@Param("ordNo") int ordNo, @Param("ordComplete") int ordComplete);

    public int countByOrdUidAndOrdComplete(String uid, int ordComplete);
}
