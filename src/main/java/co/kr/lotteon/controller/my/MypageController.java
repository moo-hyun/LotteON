package co.kr.lotteon.controller.my;

import co.kr.lotteon.dto.LtCsQnaDTO;
import co.kr.lotteon.dto.LtProductReviewDTO;
import co.kr.lotteon.dto.mypage.MyHomeDTO;
import co.kr.lotteon.dto.mypage.MyMenuDTO;
import co.kr.lotteon.dto.mypage.MyPageRequestDTO;
import co.kr.lotteon.dto.mypage.MyPageResponseDTO;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.LtCsService;
import co.kr.lotteon.service.MyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/my")
public class MypageController {

    @Autowired
    private MyService myService;

    @Autowired
    private LtCsService ltCsService;

    @ModelAttribute("myTopMenu")
    public MyMenuDTO menu(@AuthenticationPrincipal MyUserDetails myUserDetails){
        MyMenuDTO myTopMenu = myService.getMyMenu(myUserDetails.getUser().getUid());
        log.info("myTopMenu : " + myTopMenu);
        return myTopMenu;
    }

    @GetMapping(value = "/home")
    public String myIndex(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MyHomeDTO myHomeDTO = myService.getMyHomeInfo(myUserDetails.getUser().getUid());

        model.addAttribute("myHomeDTO", myHomeDTO);

        return "/my/home";
    }

    @GetMapping(value = "/info")
    public String info(Model model) {

        return "/my/info";
    }

    @GetMapping(value = "/coupon")
    public String coupon(Model model) {

        return "/my/coupon";
    }

    @GetMapping(value = "/ordered")
    public String order(Model model, MyPageRequestDTO pageRequestDTO, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if(myUserDetails == null) return "redirect:/index";
        MyPageResponseDTO pageResponseDTO = myService.showOrder(pageRequestDTO, myUserDetails.getUser().getUid());
        log.info("pageResponseDTO : " + pageResponseDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        return "/my/ordered";
    }

    @GetMapping(value = "/point")
    public String point(Model model, MyPageRequestDTO pageRequestDTO, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        if(myUserDetails == null) return "redirect:/index";
        MyPageResponseDTO pageResponseDTO = myService.showPoint(pageRequestDTO, myUserDetails.getUser().getUid());
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        return "/my/point";
    }

    @RequestMapping(value = "/qna")
    public String qna(Model model,
                      @AuthenticationPrincipal MyUserDetails myUserDetails,
                      @RequestParam(name="pg", defaultValue = "1")String pg
                      ) {
        if(myUserDetails == null) return "redirect:/index";

        String writer = myUserDetails.getUser().getUid();
        log.info("qna writer---------"+writer);
        log.info("qnaPg----------"+pg);

        //현재 페이지 번호
        int currentPage = ltCsService.getCurrentPage(pg);

        log.info("currentPage----------"+currentPage);

        //시작 인덱스
        int start = ltCsService.getStartNum(currentPage);

        //전체 게시물 갯수
        int total;
        total = Integer.parseInt((ltCsService.selectMyQnaTotal(writer)));
        log.info("qnaBoardTotal------"+total);

        List<LtCsQnaDTO> ltCsQnaDTOList =  ltCsService.selectMyQnaBoard(writer,start);

        /*

        for(int i = 0; i<ltCsQnaDTOList.size(); i++){
            LtCsQnaDTO question = (LtCsQnaDTO) ((List) ltCsQnaDTOList).get(i);
            if(question.getAnswerComplete() != 1){
                question.setComment(ltCsService.selectCsQnaComment(question.getQnaNo()));
            }
        }

         */
        /*
        List<LtCsQnaDTO> commentList = new ArrayList<>();
        for(int i = 0; i<ltCsQnaDTOList.size(); i++){
            int answerComplete = ltCsQnaDTOList.get(i).getAnswerComplete();
            int parentNo = ltCsQnaDTOList.get(i).getQnaNo();
            if(answerComplete != 1){
                commentList.add(ltCsService.selectCsQnaComment(parentNo));
            }else{
                commentList.add(new LtCsQnaDTO());
            }
        }

         */


        log.info("ltCsQnaDTO-------"+ltCsQnaDTOList.toString());
        // 마지막 페이지 번호
        int lastPageNum = ltCsService.getLastPageNum(total);

        // 페이지 그룹 start, end 번호
        int[] result = ltCsService.getPageGroupNum(currentPage, lastPageNum);

        // 페이지 시작번호
        int pageStartNum = ltCsService.getPageStartNum(total, currentPage);

        model.addAttribute("ltCsQnaDTO",ltCsQnaDTOList);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("lastPageNum",lastPageNum);
        model.addAttribute("pageGroupStart",result[0]);
        model.addAttribute("pageGroupEnd",result[1]);
        model.addAttribute("pageStartNum",pageStartNum+1);

        log.info(currentPage);
        log.info(lastPageNum);
        log.info("pageStartNum-----="+pageStartNum);



        return "/my/qna";
    }
    @ResponseBody
    @GetMapping("/qna/comment")
    public List<LtCsQnaDTO> commentBoard(@RequestParam("qnaNo") int qnaNo){
        log.info("commentBoard qnaNo-------"+qnaNo);
        List<LtCsQnaDTO> commentBoard = ltCsService.selectCsQnaComment(qnaNo);

        log.info("commentBoard0----"+commentBoard.toString());
        return commentBoard;
    }

    @GetMapping(value = "/review")
    public String review(Model model, MyPageRequestDTO myPageRequestDTO, @AuthenticationPrincipal MyUserDetails myUserDetails) {

        MyPageResponseDTO pageResponseDTO  = myService.showReview(myPageRequestDTO, myUserDetails.getUser().getUid());
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        return "/my/review";
    }

    @GetMapping(value="/writeReview")
    public String writeReview(HttpServletRequest request, LtProductReviewDTO dto,  @AuthenticationPrincipal MyUserDetails myUserDetails){
        dto.setRegIp(request.getRemoteAddr());
        dto.setRDate(LocalDateTime.now());
        dto.setUid(myUserDetails.getUser().getUid());
        log.info("ReviewDTO : " + dto);
        myService.writeReview(dto, myUserDetails.getUser().getUid());
        return "redirect:/my/ordered?success=200";
    }

}
