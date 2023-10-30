package co.kr.lotteon.repository;

import co.kr.lotteon.entity.LtMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LtMemberRepository extends JpaRepository<LtMemberEntity, String> {

    /*
    @Modifying(clearAutomatically = true)

    @Query("UPDATE lt_member m SET m.pass = :pass where m.uid = :uid")
    void updatePass(String uid,  String pass);

     */
    LtMemberEntity findAllByEmail(String email);
    int countByUid(String uid);
    int countByHp(String hp);
    int countByEmail(String email);
    int countByNameAndEmail(String name, String email);
    int countByUidAndEmail(String uid, String email);

    MemberPointInterface findPointByUid(String uid);

    LtMemberEntity findByUid(String uid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE lt_member m SET m.point = m.point + :point where m.uid = :uid", nativeQuery = true)
    public void updateMemberPointPlus(@Param("uid") String uid, @Param("point") int point);


    @Modifying
    @Transactional
    @Query(value = "UPDATE lt_member m SET m.point = m.point - :point where m.uid = :uid", nativeQuery = true)
    public void updateMemberPointMinus(@Param("uid") String uid, @Param("point") int point);

}
