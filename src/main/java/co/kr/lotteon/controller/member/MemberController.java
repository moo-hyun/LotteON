package co.kr.lotteon.controller.member;

import co.kr.lotteon.dto.LtMemberDTO;
import co.kr.lotteon.entity.LtMemberTermsEntity;
import co.kr.lotteon.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class MemberController {


    @Autowired
    private MemberService ltMemberService;

    @GetMapping("/member/login")
    public String login(){
        return "/member/login";
    }
    @GetMapping("/member/join")
    public String join(){
        return "/member/join";
    }
    @GetMapping("/member/signup/{type}")
    public String signup(Model model, @PathVariable("type") String type)
    {
        LtMemberTermsEntity terms  = ltMemberService.findByTerms();
        model.addAttribute("terms", terms);
        model.addAttribute("type", type);
        return "/member/signup";
    }/*

    @GetMapping("/member/register")
    public String register(HttpServletRequest req){

        if(req.getParameter("type").equals("SELLER")) return "/member/registerSeller";
        return "/member/register";
    }
    */

    @GetMapping("/member/register")
    public String register(@RequestParam("type") String type){
        if(type.equals("SELLER")) return "/member/registerSeller";
        return "/member/register";
    }
    @PostMapping("/member/register")
    public String register(LtMemberDTO dto, HttpServletRequest req){

        dto.setRegIp(req.getRemoteAddr());
        log.info("회원가입정보 : " + dto.toString());

        ltMemberService.save(dto);
        return "redirect:/member/login";
    }
    @GetMapping("/member/findId")
    public String findId(){
        return "/member/findId";
    }
    @PostMapping("/member/findIdResult")
    public String findIdResult(Model model,@RequestParam("email") String email){
        LtMemberDTO dto = ltMemberService.findAllByEmail(email);
        log.info("member : " + dto.toString());
        model.addAttribute("dto", dto);
        return "/member/findIdResult";
    }
    @GetMapping("/member/findPass")
    public String findPass(){
        return "/member/findPass";
    }
    @PostMapping("/member/findPassChange")
    public String findPassChange(Model model,@RequestParam("email") String email){
        LtMemberDTO dto = ltMemberService.findAllByEmail(email);
        log.info("member : " + dto.toString());
        model.addAttribute("dto", dto);
        return "/member/findPassChange";
    }
    @PostMapping("/member/findPassChangeDo")
    public String findPassChangeDo(LtMemberDTO dto){
        ltMemberService.updatePass(dto.getUid(), dto.getPass());
        return "redirect:/member/login";
    }


}
