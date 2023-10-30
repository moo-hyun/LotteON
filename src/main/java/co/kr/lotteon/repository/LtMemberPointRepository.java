package co.kr.lotteon.repository;

import co.kr.lotteon.dto.LtMemberPointDTO;
import co.kr.lotteon.entity.LtMemberEntity;
import co.kr.lotteon.entity.LtMemberPointEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LtMemberPointRepository extends JpaRepository<LtMemberPointEntity, Integer> {


    Page<LtMemberPointEntity> findAllByUid(String uid, Pageable pageable);
    LtMemberPointEntity findTop1ByUidOrderByPointDateDesc(String uid);
}
