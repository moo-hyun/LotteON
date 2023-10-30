package co.kr.lotteon.repository;

import co.kr.lotteon.entity.LtCsCate1Entity;
import co.kr.lotteon.entity.LtCsCate2Entity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface LtCsCate2Repository extends JpaRepository<LtCsCate2Entity, Integer> {
    //List<LtCsCate2Entity> findAllByCate1(LtCsCate1Entity cate1);
    @Query("SELECT a FROM LtCsCate2Entity a " +
            "WHERE a.ltCsCatePK.cate1 = :cate1 ")
    List<LtCsCate2Entity> findAllByCate1(@Param("cate1") int cate1);
/*
    @Query("SELECT a FROM LtCsCate2Entity a " +
            "WHERE a.cate1 < 20 ")
    List<LtCsCate2Entity> findCate2sForNotice();
    @Query("SELECT a FROM LtCsCate2Entity a " +
            "WHERE a.cate1 >= 20 ")
    List<LtCsCate2Entity> findCate2sForQna();
*/

}
