package co.kr.lotteon.repository;

import co.kr.lotteon.entity.LtCsFaqEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LtCsFaqRepository extends JpaRepository<LtCsFaqEntity, Integer> {

    public Page<LtCsFaqEntity> findAll(Pageable pageable);

    public Page<LtCsFaqEntity> findByTitleContains(String keyword, Pageable pageable);

    public Page<LtCsFaqEntity> findByCate1AndTitleContains(int cate1, String keyword, Pageable pageable);

    public Page<LtCsFaqEntity> findByCate1(int cate1, Pageable pageable);

}
