package co.kr.lotteon.controller.admin.product;

import co.kr.lotteon.dto.LtProductCate1DTO;
import co.kr.lotteon.dto.LtProductCate2DTO;
import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.service.LtAdminService;
import co.kr.lotteon.service.LtProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log4j2
@Controller
public class AdminModifyController {

    @Autowired
    private LtAdminService ltAdminService;
    @Autowired
    private LtProductService ltProductService;

    @GetMapping("/admin/product/modify")
    public String modify(@RequestParam("prodNo") int prodNo, Model model){

        //상품 조회 + 카테 조인
        LtProductDTO ltProductDTO = ltProductService.selectProduct(prodNo);
        log.info("ltProductDTO : " + ltProductDTO);
        model.addAttribute("ltProductDTO", ltProductDTO);


        return "/admin/product/modify";
    }
}
