package co.kr.lotteon.controller.admin.product;

import co.kr.lotteon.dto.prodpage.ProdPageRequestDTO;
import co.kr.lotteon.dto.prodpage.ProdPageResponseDTO;
import co.kr.lotteon.service.LtAdminService;
import co.kr.lotteon.service.LtProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class AdminListController {

    @Autowired
    private LtProductService ltProductService;

    @Autowired
    private LtAdminService ltAdminService;

    @GetMapping("/admin/product/list")
    public String ProductList(Model model, ProdPageRequestDTO prodPageRequestDTO){

        log.info("prodPageRequestDTO : " + prodPageRequestDTO);

        //상품 전체 조회, 조건 검색
        ProdPageResponseDTO prodPageResponseDTO = ltProductService.search(prodPageRequestDTO);
        System.out.println("prodPageResponseDTO : " + prodPageResponseDTO);
        model.addAttribute(prodPageResponseDTO);

        return "/admin/product/list";
    }





}
