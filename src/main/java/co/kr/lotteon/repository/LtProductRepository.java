package co.kr.lotteon.repository;


import co.kr.lotteon.entity.LtProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LtProductRepository extends JpaRepository<LtProductEntity, Integer> {

// admin 상품 리스트, 조건 검색

    //전체 출력(isRemoved = 0 판매 중 상품)
    public Page<LtProductEntity> findAllByIsRemoved(int isRemoved, Pageable pageable);

    public Page<LtProductEntity> findAllByProdCate1AndProdCate2(int prodCate1, int prodCate2, Pageable pageable);
    public Page<LtProductEntity> findAllByOrderBySoldDesc(Pageable pageable);
    public Page<LtProductEntity> findAllByOrderByPriceAsc(Pageable pageable);
    public Page<LtProductEntity> findAllByOrderByPriceDesc(Pageable pageable);
    public Page<LtProductEntity> findAllByOrderByReviewDesc(Pageable pageable);
    public Page<LtProductEntity> findAllByOrderByScoreDesc(Pageable pageable);
    public Page<LtProductEntity> findAllByOrderByProdNoDesc(Pageable pageable);
    public Page<LtProductEntity> findAllByProdNameContainsOrderByProdNoDesc(String prodName, Pageable pageable);

    //조건 검색(제품명, 제품코드, 제조사)
    public Page<LtProductEntity> findALLByIsRemovedAndProdNameContains(int isRemoved, String prodName, Pageable pageable);
    @Query(value = "SELECT * FROM lt_product WHERE isRemoved = ?1 AND prodNo LIKE %?2%", nativeQuery = true) //네이티브쿼리 1,2 파라미터 값 들어 가는 곳
    public Page<LtProductEntity> productNoList(int isRemoved, String prodNo, Pageable pageable);
    public Page<LtProductEntity> findAllByIsRemovedAndCompanyContains(int isRemoved, String company, Pageable pageable);

    //조건 검색 후 페이지 처리를 위한 total값 count
    public long countByIsRemovedAndProdNameContains(int isRemoved, String prodName);
    @Query(value = "SELECT COUNT(*) FROM lt_product WHERE isRemoved = ?1 AND prodNo LIKE %?2%", nativeQuery = true)
    public long countByproductNoList(int isRemoved, String prodNo);
    public long countByIsRemovedAndCompanyContains(int isRemoved, String company);

// index_prod_list

    // hit_prod
    public List<LtProductEntity> findTop8ByOrderByHitDesc();

    // recommend_prod
    public List<LtProductEntity> findTop8ByOrderByScoreDesc();

    // new_prod
    public List<LtProductEntity> findTop8ByOrderByRegDateDesc();

    // discount_prod
    public List<LtProductEntity> findTop8ByOrderByDiscountDesc();

    // Best_prod(sold)
    public  List<LtProductEntity> findTop5ByOrderBySoldDesc();


    @Modifying
    @Transactional
    @Query(value = "UPDATE lt_product m SET m.review = m.review + 1 , m.score = :score WHERE m.prodNo = :prodNo", nativeQuery = true)
    public void updateScore(@Param("prodNo") int prodNo, @Param("score") int score);
}
