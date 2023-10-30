package co.kr.lotteon.controller.member;

import co.kr.lotteon.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member/check")
public class MemberCheckController {

    @Autowired
    MemberService ltMemberService;
    @GetMapping("/uid/{uid}")
    public int checkUid(@PathVariable("uid") String uid){
        return ltMemberService.countUid(uid);
    }

    @GetMapping("/email/{email}")
    public int checkEmail(@PathVariable("email") String email){
        return ltMemberService.countEmail(email);
    }

    @GetMapping("/hp/{hp}")
    public int checkHp(@PathVariable("hp") String hp){
        return ltMemberService.countHp(hp);
    }

}
