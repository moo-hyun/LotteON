package co.kr.lotteon.controller.member;

import co.kr.lotteon.dto.EmailMessage;
import co.kr.lotteon.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/member/find")
public class MemberFindController {
    @Autowired
    MemberService ltMemberService;

    @GetMapping("/authEmail")
    public Map<String, Integer> authEmail(@RequestParam Map<String, Object> params){
        String type = (String) params.get("type"); // authEmail.js 에서 AJAX로 GET 전송한 거 수신
        String uid = (String) params.get("uid"); // authEmail.js 에서 AJAX로 GET 전송한 거 수신
        String name = (String) params.get("name"); // authEmail.js 에서 AJAX로 GET 전송한 거 수신
        String email = (String) params.get("email"); // authEmail.js 에서 AJAX로 GET 전송한 거 수신
        EmailMessage emailMessage = EmailMessage.builder()
                .to(email)
                .subject("[LotteON] 이메일 인증을 위한 인증 코드 발송")
                .build();

        int result = 0;
        int status = 0;

        if(type.equals("FIND_ID")) {

            // 아이디 찾기할 때 이메일 인증
            result = ltMemberService.countNameAndEmail(name, email);

            // 회원가입된 이메일인지 DB에서 select 하고, 존재하면 인증코드 전송한다
            if(result == 1) {
                status = ltMemberService.sendCodeByEmail(emailMessage); // service.sendCodeByEmail(email); 이렇게만 있어서, status를 안 받아줘서 계속 status=0 으로 인식을 못했던 것임
            }
        }else if(type.equals("FIND_PASS")) {

            result = ltMemberService.countUidAndEmail(uid, email);

            if(result == 1) {
                status = ltMemberService.sendCodeByEmail(emailMessage);
            }
        }
        Map<String, Integer> returnMap = new HashMap<>();
        returnMap.put("result", result);
        returnMap.put("status", status);
        return returnMap;
    }

    @PostMapping("/authEmailCode")
    public int authEmailCode(@RequestParam Map<String, Object> params){
        String code = (String) params.get("code");
        log.info("code : " + code);

        int result = ltMemberService.confirmCodeByEmail(code);

        return result;
    }

}