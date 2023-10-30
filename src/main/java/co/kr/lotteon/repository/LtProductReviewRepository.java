package co.kr.lotteon.repository;

import co.kr.lotteon.entity.LtMemberPointEntity;
import co.kr.lotteon.entity.LtProductEntity;
import co.kr.lotteon.entity.LtProductReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LtProductReviewRepository extends JpaRepository<LtProductReviewEntity, Integer> {

    public Page<LtProductReviewEntity> findAllByUid(String uid, Pageable pageable);


    public Page<LtProductReviewEntity> findAllByLtProductEntity(LtProductEntity ltProductEntity, Pageable pageable);

     List<LtProductReviewEntity> findTop5ByUidOrderByRdateDesc(String uid);


    @Query(value = "SELECT AVG(m.rating) FROM lt_product_review m where m.prodNo = :prodNo", nativeQuery = true)
     int findAVG(@Param("prodNo") int prodNo);

}
