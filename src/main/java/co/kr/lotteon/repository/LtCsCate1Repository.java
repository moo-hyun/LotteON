package co.kr.lotteon.repository;

import co.kr.lotteon.entity.LtCsCate1Entity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LtCsCate1Repository extends JpaRepository<LtCsCate1Entity, Integer> {


    @Query("SELECT a FROM LtCsCate1Entity a " +
            "WHERE a.cate1 < 20 ")
    List<LtCsCate1Entity> findCate1sForNotice();
    @Query("SELECT a FROM LtCsCate1Entity a " +
            "WHERE a.cate1 >= 20 ")
    List<LtCsCate1Entity> findCate1sForQna();
}
