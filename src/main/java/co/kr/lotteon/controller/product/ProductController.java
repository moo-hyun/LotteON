package co.kr.lotteon.controller.product;

import co.kr.lotteon.dto.LtProductCartDTO;
import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.dto.LtProductReviewDTO;
import co.kr.lotteon.dto.mypage.MyPageRequestDTO;
import co.kr.lotteon.dto.mypage.MyPageResponseDTO;
import co.kr.lotteon.dto.prodpage.ProdPageRequestDTO;
import co.kr.lotteon.dto.prodpage.ProdPageResponseDTO;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.LtProductService;
import co.kr.lotteon.service.ProductCartSerivce;
import co.kr.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final LtProductService ltProductService;
    private final ProductService productService;
    private final ProductCartSerivce productCartSerivce;
    private final ModelMapper modelMapper;

    @GetMapping("/list")
    public String list(Model model, ProdPageRequestDTO pageRequestDTO) {

        //ProdPageResponseDTO pageResponseDTO = productService.getProductListByCates(pageRequestDTO);
        ProdPageResponseDTO pageResponseDTO = productService.getProductList(pageRequestDTO);
        if(pageResponseDTO.getTotal()/10 < pageRequestDTO.getPg()){
            //return "redirect:/article/list?success=100";
        }

        log.info("pageResponseDTO : " + pageResponseDTO );

        model.addAttribute("pageResponseDTO", pageResponseDTO);
        return "/product/list";
    }

    @GetMapping("/view")
    public String view(@RequestParam(value = "prodNo")int prodNo, Model model, MyPageRequestDTO myPageRequestDTO) {

        LtProductDTO dto = ltProductService.getProdDto(prodNo);
        MyPageResponseDTO pageResponseDTO = productService.getReviews(prodNo, myPageRequestDTO);
        model.addAttribute("prod",dto);
        model.addAttribute("pageResponseDTO",pageResponseDTO);
        return "/product/view";

    }

    @GetMapping("/search")
    public String search() {
        return "/product/search";
    }

}

