package co.kr.lotteon.controller.cs;

import co.kr.lotteon.dto.LtCsCate2DTO;
import co.kr.lotteon.dto.LtCsFaqDTO;
import co.kr.lotteon.dto.LtCsNoticeDTO;
import co.kr.lotteon.service.LtCsService;
import co.kr.lotteon.dto.LtCsQnaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class CsController {

/*    @Autowired
    private BuildProperties buildProperties;*/

    @Autowired
    private LtCsService ltCsService;

    @GetMapping("/cs/index")
    public String index(Model model){

/*        // build.gradle 파일 맨 밑에 빌드 정보를 가져오기 위해 buildInfo() 호출 해야됨
        String appName = buildProperties.getName(); // settings.gradle 파일에서 앱이름 가져옴
        String version = buildProperties.getVersion(); // build.gradle 파일에서 버전값 가져옴


        System.out.println("appName : " + appName);
        System.out.println("version : " + version);

        model.addAttribute("appInfo", appName+version);*/

        List<LtCsNoticeDTO> noticelist = ltCsService.selectCsNotices();
        model.addAttribute("noticelist",noticelist);

        List<LtCsQnaDTO> qnalist = ltCsService.selectCsQna();
        model.addAttribute("qnalist",qnalist);

        log.info(noticelist.toString());
        log.info(qnalist.toString());

        return "/cs/index";
    }
    @GetMapping("/cs/qna/write")
    public String writeView(){
        return "/cs/qna/write";
    }

    @PostMapping("/cs/qna/write")
    public String write(HttpServletRequest request, LtCsQnaDTO dto){

        dto.setRegip(request.getRemoteAddr());
        dto.setRdate(LocalDateTime.now());
        ltCsService.insertQnaWrite(dto);
        return "redirect:/cs/index";

    }

    //파일 다운로드
    @GetMapping("/cs/qna/download")
    public void filedownload(int qnaNo,HttpServletResponse response) throws IOException {
        // dto 객체로부터 파일 경로를 가져온다.
        LtCsQnaDTO dto = ltCsService.selectCsQnaView(qnaNo);
        log.info("qnadownload----------"+dto);
        String filePath = dto.getFile1();
        String oName = dto.getFile2();


        String path ="files/"+filePath;
        byte[] fileByte = FileUtils.readFileToByteArray(new File(path));


        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(oName, "UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }



    @RequestMapping("/cs/notice/list")
    public String selectCsNoticeList(@RequestParam(name="pg", defaultValue = "1")String pg,
                                     @RequestParam(name= "cate1" ,required = false) String cate1,
                                     Model model){

        log.info(pg);
        log.info("cate1-----"+cate1);


        //현재 페이지 번호
        int currentPage = ltCsService.getCurrentPage(pg);

        log.info("currentPage----------"+currentPage);

        //시작 인덱스
        int start = ltCsService.getStartNum(currentPage);

        //전체 게시물 갯수
        int total;
        List<LtCsNoticeDTO> ltCsNoticeDTOS;

        if(cate1 == null || cate1.isEmpty()){
            log.info("here1");
            total = ltCsService.selectCsNoticeTotal();
            ltCsNoticeDTOS = ltCsService.selectCsNoticeListAll(start);
        } else {
            log.info("here2");
            log.info("here2 cate1------"+cate1);
            total = ltCsService.selectCsNoticeTotalCate(Integer.parseInt(cate1));

            log.info("here2 total : "+total);


            ltCsNoticeDTOS = ltCsService.selectCsNoticeListCate(Integer.parseInt(cate1), start);


        }


        // 마지막 페이지 번호
        int lastPageNum = ltCsService.getLastPageNum(total);

        // 페이지 그룹 start, end 번호
        int[] result = ltCsService.getPageGroupNum(currentPage, lastPageNum);

        // 페이지 시작번호
        int pageStartNum = ltCsService.getPageStartNum(total, currentPage);


        model.addAttribute("ltCsNoticeDTOS",ltCsNoticeDTOS);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("lastPageNum",lastPageNum);
        model.addAttribute("pageGroupStart",result[0]);
        model.addAttribute("pageGroupEnd",result[1]);
        model.addAttribute("pageStartNum",pageStartNum+1);
        model.addAttribute("cate1",cate1);
        log.info(currentPage);
        log.info(lastPageNum);
        log.info("cate1last----------------"+cate1);


        return "/cs/notice/list";
    }

    @RequestMapping("/cs/qna/list")
    public String selectCsQnaListAll(@RequestParam(name="pg", defaultValue = "1")String pg,
                                     @RequestParam(name= "cate1" ,required = false) String cate1,
                                     Model model){

        log.info(pg);

        //현재 페이지 번호
        int currentPage = ltCsService.getCurrentPage(pg);

        log.info("currentPage----------"+currentPage);

        //시작 인덱스
        int start = ltCsService.getStartNum(currentPage);

        //전체 게시물 갯수
        int total;
        List<LtCsQnaDTO> ltCsQnaDTOS;

        if(cate1 == null || cate1.isEmpty()){
            log.info("here1");
            total = ltCsService.selectCsQnaTotal();
            ltCsQnaDTOS = ltCsService.selectCsQnaListAll(start);
        } else {
            log.info("here2");
            log.info("here2 cate1------"+cate1);
            total = ltCsService.selectCsQnaTotalCate(Integer.parseInt(cate1));

            log.info("here2 total : "+total);


            ltCsQnaDTOS = ltCsService.selectCsQnaListCate(Integer.parseInt(cate1), start);


        }


        // 마지막 페이지 번호
        int lastPageNum = ltCsService.getLastPageNum(total);

        // 페이지 그룹 start, end 번호
        int[] result = ltCsService.getPageGroupNum(currentPage, lastPageNum);

        // 페이지 시작번호
        int pageStartNum = ltCsService.getPageStartNum(total, currentPage);



        model.addAttribute("ltCsQnaDTOS",ltCsQnaDTOS);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("lastPageNum",lastPageNum);
        model.addAttribute("pageGroupStart",result[0]);
        model.addAttribute("pageGroupEnd",result[1]);
        model.addAttribute("pageStartNum",pageStartNum+1);
        model.addAttribute("cate1",cate1);

        log.info(currentPage);
        log.info(lastPageNum);


        return "/cs/qna/list";
    }

    @GetMapping("/cs/notice/view")
    public String selectCsNoticeView(int noticeNo, Model model){
        LtCsNoticeDTO notcieBoard = ltCsService.selectCSNoticeView(noticeNo);
        model.addAttribute("notcieBoard",notcieBoard);
        log.info("noticeNo------"+noticeNo);
        log.info("notcieBoard----------"+notcieBoard.toString());
        return "/cs/notice/view";
    }

    @GetMapping("/cs/qna/view")
    public String selectCsQnaView(int qnaNo, Model model){
        LtCsQnaDTO qnaBoard = ltCsService.selectCsQnaView(qnaNo);
        model.addAttribute("qnaBoard",qnaBoard);

        LtCsQnaDTO qnaChildBoard = ltCsService.selectCsQnaChildBoard(qnaNo);
        model.addAttribute("qnaChildBoard", qnaChildBoard);


        log.info("qnaNo------"+qnaNo);
        log.info("qnaBoard----------"+qnaBoard.toString());
        return "/cs/qna/view";
    }

    @RequestMapping("/cs/faq/list")
    public String faq(@RequestParam(name= "cate1" ,required = false) int cate1,
                      Model model){
        log.info("faqcate1-----------------"+cate1);
        List<LtCsFaqDTO> faqDTOList = ltCsService.selectCsFaqList10(cate1);

        List<LtCsCate2DTO> cate2list = ltCsService.selectCsCate2(cate1);
        model.addAttribute("cate2list",cate2list);
        model.addAttribute("faqDTOList",faqDTOList);
        model.addAttribute("cate1",cate1);
        log.info("faqDTOList==============="+faqDTOList);
        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String selectCsFaqView(int faqNo, Model model){
        LtCsFaqDTO faqBoard = ltCsService.selectCsFaqView(faqNo);
        model.addAttribute("faqBoard",faqBoard);

        log.info("faqNo--------"+faqNo);
        return "/cs/faq/view";
    }

    @GetMapping("/cs/qna/modify")
    public String selectCsQnaBoard(int qnaNo, Model model){
        LtCsQnaDTO qnaBoard = ltCsService.selectCsQnaBoard(qnaNo);
        model.addAttribute("qnaBoard",qnaBoard);

        log.info("qnaNo------"+qnaNo);
        log.info("qnaBoard----------"+qnaBoard.toString());

        return "/cs/qna/modify";
    }
    @PostMapping("/cs/qna/modify")
    public String updateQnaBoard(@ModelAttribute LtCsQnaDTO dto){
        ltCsService.updateQnaBoard(dto);
        log.info("updateQnaBoardDTO------"+dto.toString());
        int qnaNo= dto.getQnaNo();
        log.info("updateQnaBoardQnaNo----------"+qnaNo);
        return "redirect:/cs/qna/view?qnaNo="+qnaNo;
    }

    @GetMapping("/cs/qna/delete")
    public String deleteQnaBoard(int qnaNo) {

        //게시물 삭제하기 전 파일 정보 가져오기
        LtCsQnaDTO dto = ltCsService.selectCsQnaBoard(qnaNo);
        //파일 경로
        String filePath = dto.getFile1();

        //파일 삭제
        boolean isFileDeleted = deleteFile(filePath);
        ltCsService.deleteQnaBoard(qnaNo);

        // 만약 파일이 정상적으로 삭제되었다면 게시물을 삭제한다.
        if (isFileDeleted) {
            ltCsService.deleteQnaBoard(qnaNo);
        }

        return "redirect:/cs/qna/list";
    }
    //파일삭제 구현
    private boolean deleteFile(String filePath){
        try{
            File fileToDelete = new File("files/" + filePath);
            return fileToDelete.delete();

        }catch (Exception e){
            log.error("파일 삭제 중 오류 발생: "+ e.getMessage());
            return false;
        }
    }
}