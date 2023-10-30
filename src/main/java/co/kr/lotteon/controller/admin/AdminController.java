package co.kr.lotteon.controller.admin;

import co.kr.lotteon.dto.LtCsNoticeDTO;
import co.kr.lotteon.dto.LtCsQnaDTO;
import co.kr.lotteon.service.LtCsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

//메인페이지
@Log4j2
@Controller
public class AdminController {


    @Autowired
    private LtCsService ltCsService;

    @GetMapping(value={"/admin/","/admin/index"})
    public String index(Model model){

        List<LtCsNoticeDTO> noticelist = ltCsService.selectCsNotices();
        model.addAttribute("noticelist",noticelist);

        List<LtCsQnaDTO> qnalist = ltCsService.selectCsQna();
        model.addAttribute("qnalist",qnalist);

        log.info(noticelist.toString());
        log.info(qnalist.toString());

        return "/admin/index";
    }


}