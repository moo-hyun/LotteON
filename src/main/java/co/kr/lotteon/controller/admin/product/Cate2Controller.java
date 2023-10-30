package co.kr.lotteon.controller.admin.product;

import co.kr.lotteon.dto.LtProductCate2DTO;
import co.kr.lotteon.service.LtAdminService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class Cate2Controller {

    @Autowired
    private LtAdminService ltAdminService;

    @GetMapping("/admin/product/cate2/{selectValue}")
    public List<LtProductCate2DTO> list(@PathVariable("selectValue") String selectValue, Model model){
        log.info("selectValue : "+selectValue);
        List<LtProductCate2DTO> cate2list = ltAdminService.selectLtProductCate2s(selectValue);

        log.info("cate2List : "+ cate2list);
        return cate2list;

    }



}
