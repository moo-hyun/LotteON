package co.kr.lotteon.repository;

import co.kr.lotteon.entity.LtCsNoticeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LtCsNoticeRepository extends JpaRepository<LtCsNoticeEntity, Integer> {

    public Page<LtCsNoticeEntity> findAll(Pageable pageable);

    public Page<LtCsNoticeEntity> findByTitleContains(String keyword, Pageable pageable);
    public Page<LtCsNoticeEntity> findByCate1AndTitleContains(int cate1, String keyword, Pageable pageable);
    public Page<LtCsNoticeEntity> findByCate1(int cate1, Pageable pageable);

}


