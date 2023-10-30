package co.kr.lotteon.repository;

import co.kr.lotteon.entity.LtCsQnaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LtCsQnaRepository extends JpaRepository<LtCsQnaEntity, Integer> {

    public Page<LtCsQnaEntity> findAll(Pageable pageable);

    public Page<LtCsQnaEntity> findByTitleContains(String keyword, Pageable pageable);

    public Page<LtCsQnaEntity> findByCate1AndTitleContains(int cate1, String keyword, Pageable pageable);

    public Page<LtCsQnaEntity> findByCate1(int cate1, Pageable pageable);

    public List<LtCsQnaEntity> findByParent(int parent);

    int countByWriter(String writer);

    List<LtCsQnaEntity> findTop3ByWriterOrderByRdate(String writer);
}
