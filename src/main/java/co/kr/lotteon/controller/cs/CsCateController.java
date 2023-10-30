package co.kr.lotteon.controller.cs;

import co.kr.lotteon.dto.LtCsCate1DTO;
import co.kr.lotteon.dto.LtCsCate2DTO;
import co.kr.lotteon.service.CsCateService;
import co.kr.lotteon.service.LtCsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/cs")
@Log4j2
@RestController
public class CsCateController {

    @Autowired
    private CsCateService csCateService;

    @Autowired
    private LtCsService ltCsService;

    @GetMapping("/getCsCates")
    public Map<String, Object> getCsCates(){
        return csCateService.getCsCates();

    }

    @ResponseBody
    @GetMapping("/cate1")

    public List<LtCsCate1DTO> list(){

        List<LtCsCate1DTO> cate1list = ltCsService.selectCsCate1();

        log.info("listcate1list"+cate1list);
        return cate1list;

    }

    @ResponseBody
    @GetMapping("/cate2")

    public List<LtCsCate2DTO> cate2list(@RequestParam("cate1") int cate1) {
        log.info("cate2cate2cate2???" + cate1);
        List<LtCsCate2DTO> cate2list = ltCsService.selectCsCate2(cate1);

        return cate2list;
    }

}
