package co.kr.lotteon.controller.product;

import co.kr.lotteon.dto.LtProductCartDTO;
import co.kr.lotteon.dto.LtProductOrderDTO;
import co.kr.lotteon.dto.LtProductOrderItemDTO;
import co.kr.lotteon.dto.order.CompletePage;
import co.kr.lotteon.dto.order.OrderTotalDTO;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.ProductCartSerivce;
import co.kr.lotteon.service.ProductOrderService;
import co.kr.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductOrderController {

    private final ProductService productService;
    private final ProductCartSerivce productCartSerivce;
    private final ProductOrderService productOrderService;


    @GetMapping("/order")
    public String order(){return "redirect:/index";}

    @PostMapping("/order")
    public String order(Model model, OrderTotalDTO orderTotalDTO, @RequestParam(name="chk") String[] chk,  @AuthenticationPrincipal MyUserDetails myUserDetails){

        List<LtProductCartDTO> dtoList = productOrderService.showOrder(chk);
        int nowPoint = productOrderService.getPoint(myUserDetails.getUser().getUid());
        model.addAttribute("dtoList", dtoList);
        model.addAttribute("orderTotal", orderTotalDTO);
        model.addAttribute("nowPoint", nowPoint);
        log.info("orderTotal : " + orderTotalDTO);

        return "/product/order";
    }

    @PostMapping("/order/progress")
    public String orderProgress(Model model,LtProductOrderDTO ltProductOrderDTO, @AuthenticationPrincipal MyUserDetails myUserDetails,
                                @RequestParam(name="cartNo") String[] cartNo){
        log.info("ltProductOrderDTO : " + ltProductOrderDTO);
        CompletePage returnPageVal = productOrderService.insertOrder(ltProductOrderDTO, myUserDetails.getUser().getUid(), cartNo);
        ltProductOrderDTO.setOrdNo(returnPageVal.getOrdNo());

        model.addAttribute("ltProductOrderDTO",ltProductOrderDTO);
        model.addAttribute("dtoList", returnPageVal.getDtoList());

        return "/product/complete";
    }




}
