package co.kr.lotteon.controller.admin.cs;

import co.kr.lotteon.dto.LtCsNoticeDTO;
import co.kr.lotteon.dto.LtCsQnaDTO;
import co.kr.lotteon.dto.cspage.CsPageRequestDTO;
import co.kr.lotteon.dto.cspage.CsPageResponseDTO;
import co.kr.lotteon.entity.LtCsQnaEntity;
import co.kr.lotteon.service.AdminCsService;
import co.kr.lotteon.service.LtCsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
public class AdminCsController {

    @Autowired
    private LtCsService csService;
    @Autowired
    private AdminCsService adminCsService;

    // Notice
    @GetMapping("/admin/cs/notice/write")
    public String noticeWrite(){
        return "/admin/cs/notice/write";
    }

    @PostMapping("/admin/cs/notice/write")
    public String noticeWrite(LtCsNoticeDTO dto, HttpServletRequest req){

        dto.setRegip(req.getRemoteAddr());
        log.info("회원가입정보 : " + dto.toString());

        adminCsService.saveNotice(dto);
        return "redirect:/admin/cs/notice/list";
    }

    @GetMapping("/admin/cs/notice/modify")
    public String noticeModify(@RequestParam(value = "noticeNo") int noticeNo , Model model) {
        LtCsNoticeDTO noticeDTO = csService.selectCSNoticeView(noticeNo);
        model.addAttribute("noticeDTO", noticeDTO) ;
        return "/admin/cs/notice/modify";
    }

    @PostMapping("/admin/cs/notice/modify")
    public String noticeModify(LtCsNoticeDTO dto, int noticeNo) {
        // update
        dto.setNoticeNo(noticeNo);

        System.out.println("controller : " + dto);

        adminCsService.updateAdminNotice(dto);

        return "redirect:/admin/cs/notice/list";
    }

    @GetMapping("/admin/cs/notice/view")
    public String noticeView(@RequestParam(value = "noticeNo")int noticeNo, Model model){
        LtCsNoticeDTO noticeDTO = csService.selectCSNoticeView(noticeNo);
        model.addAttribute("noticeDTO", noticeDTO) ;
        return "/admin/cs/notice/view";
    }

    @GetMapping(value = {"/admin/cs/notice/list", "/admin/cs/notice/"})
    public String noticeList(Model model, CsPageRequestDTO pageRequestDTO) {
        CsPageResponseDTO pageResponseDTO = adminCsService.noticeList(pageRequestDTO);

        if(pageResponseDTO.getTotal()/10 < pageRequestDTO.getPg()){
            //return "redirect:/article/list?success=100";
        }

        log.info("pageResponseDTO cate1 : " + pageResponseDTO.getCate1());
        log.info("pageResponseDTO cate2 : " + pageResponseDTO.getCate2());
        log.info("pageResponseDTO pg : " + pageResponseDTO.getPg());
        log.info("pageResponseDTO size : " + pageResponseDTO.getSize());
        log.info("pageResponseDTO total : " + pageResponseDTO.getTotal());
        log.info("pageResponseDTO start : " + pageResponseDTO.getStart());
        log.info("pageResponseDTO end : " + pageResponseDTO.getEnd());
        log.info("pageResponseDTO prev : " + pageResponseDTO.isPrev());
        log.info("pageResponseDTO next : " + pageResponseDTO.isNext());

        model.addAttribute("pageResponseDTO", pageResponseDTO);
        return "/admin/cs/notice/list";
    }

    @GetMapping("/admin/cs/notice/delete")
    public String noticeDelete(@RequestParam(value = "noticeNo")int noticeNo) {
        adminCsService.deleteNotice(noticeNo);
        return "redirect:/admin/cs/notice/list";
    }

    // Qna
    @GetMapping(value = {"/admin/cs/qna/list", "/admin/cs/qna/"})
    public String qnaList(Model model, CsPageRequestDTO pageRequestDTO) {

        CsPageResponseDTO pageResponseDTO = adminCsService.qnaList(pageRequestDTO);
        System.out.println("controller : " + pageRequestDTO.toString());
        model.addAttribute("pageResponseDTO", pageResponseDTO);

        return "/admin/cs/qna/list";

    }

    @GetMapping("/admin/cs/qna/view")
    public String qnaView(@RequestParam(value = "qnaNo")int qnaNo, Model model) {

        LtCsQnaDTO qnaDTO = csService.selectCsAdminQnaView(qnaNo);

        List<LtCsQnaEntity> commentList = new ArrayList<>();
        if(qnaDTO.getAnswerComplete() == 2){
            commentList = csService.selectComments(qnaDTO.getQnaNo());

            log.info("댓글  : " + commentList);

        }

        model.addAttribute("commentList",commentList);

        model.addAttribute("qnaDTO", qnaDTO) ;

        return "/admin/cs/qna/view";
    }

    @PostMapping("/admin/cs/qna/view")
    public String qnaView(Model model) {

        return "/admin/cs/qna/view";
    }

}
