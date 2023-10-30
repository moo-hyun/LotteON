package co.kr.lotteon.controller.admin.product;

import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.service.LtProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.net.PortUnreachableException;
import java.net.URLDecoder;
import java.util.List;

@Log4j2
@Controller
public class AdminDeleteController {

    @Autowired
    private LtProductService ltProductService;


    //1개 삭제
    @GetMapping("/admin/product/delete")
    public String deleteLtProduct(LtProductDTO ltProductDTO){

        ltProductDTO = ltProductService.selectProduct(ltProductDTO.getProdNo());
        ltProductService.deleteLtProduct(ltProductDTO);

        return "redirect:/admin/product/list";
    }
    
    //다중 삭제
    @PostMapping("/admin/product/delete")
    public String deleteLtProduct(@RequestParam(value = "chk") List<String> chks){

        for (String chk : chks) {
            System.out.println("체크 값 : " + chk);

            LtProductDTO ltProductDTO = ltProductService.selectProduct(Integer.parseInt(chk));
            ltProductService.deleteLtProduct(ltProductDTO);

        }

        log.info("삭제처리 합니다.");

        return "redirect:/admin/product/list";
    }





}
