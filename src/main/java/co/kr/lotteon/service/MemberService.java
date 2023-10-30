package co.kr.lotteon.service;

import co.kr.lotteon.dto.EmailMessage;
import co.kr.lotteon.dto.LtMemberDTO;
import co.kr.lotteon.entity.LtMemberEntity;
import co.kr.lotteon.entity.LtMemberTermsEntity;
import co.kr.lotteon.repository.LtMemberRepository;
import co.kr.lotteon.repository.LtMemberTermsRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@Service
public class MemberService {
    @Autowired
    LtMemberRepository ltMemberRepository;

    @Autowired
    private LtMemberTermsRepository ltMemberTermsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;

    private static String generatedCode; // 인증코드 생성시 필요

    public LtMemberTermsEntity findByTerms(){
        return ltMemberTermsRepository.findAll().get(0);
    }

    public LtMemberDTO findAllByEmail(String email){
        return ltMemberRepository.findAllByEmail(email).toDTO();
    }
    public void save(LtMemberDTO dto){


        // 비밀번호 암호화
        dto.setPass(passwordEncoder.encode(dto.getPass()));

        // DTO를 Entity로 변환
        LtMemberEntity entity = dto.toEntity();

        // DB insert
        ltMemberRepository.save(entity);
    }

    public int countUid(String uid){
        return ltMemberRepository.countByUid(uid);
    }
    public int countHp(String hp){
        return ltMemberRepository.countByHp(hp);
    }
    public int countEmail(String email){
        return ltMemberRepository.countByEmail(email);
    }
    public int countUidAndEmail(String uid, String email){ return ltMemberRepository.countByUidAndEmail(uid, email);}
    public int countNameAndEmail(String name, String email){
        return ltMemberRepository.countByNameAndEmail(name, email);
    }
    public int sendCodeByEmail(EmailMessage emailMessage) { // dao에서 가져오는 거 아님

        // 인증코드 생성
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        generatedCode = String.valueOf(code);


        // 메일 발송
        int status = 0;
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            mimeMessageHelper.setText("<h1>인증코드는 " + code + " 입니다.</h1>", true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            status = 1; // 메일 발송하면 status 1
            log.info("인증코드 : " + generatedCode);
            log.info("Success");


        } catch (Exception e) {
            log.info("fail");
            throw new RuntimeException(e);
        }




        return status;
    }// sendCodeByEmail end

    public int confirmCodeByEmail(String code) {

        if(code.equals(generatedCode)) {
            log.info("return 1...");
            return 1;
        }else {
            log.info("return 0...");
            return 0;
        }
    }
    public void updatePass(String uid, String pass) {
        pass = passwordEncoder.encode(pass);
        LtMemberEntity entity = ltMemberRepository.findById(uid).get();
        entity.setPass(pass);
        ltMemberRepository.save(entity);
    }

    public void updatePoint(String ordUid, int point) {
        //ltMemberRepository.save(LtMemberEntity.builder().uid(ordUid).point(point).build());
    }

}
