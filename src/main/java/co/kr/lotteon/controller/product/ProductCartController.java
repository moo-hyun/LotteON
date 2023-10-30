package co.kr.lotteon.controller.product;

import co.kr.lotteon.dto.LtProductCartDTO;
import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.dto.prodpage.ProdPageRequestDTO;
import co.kr.lotteon.dto.prodpage.ProdPageResponseDTO;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.LtProductService;
import co.kr.lotteon.service.ProductCartSerivce;
import co.kr.lotteon.service.ProductService;
import com.fasterxml.jackson.core.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductCartController {

    private final LtProductService ltProductService;
    private final ProductService productService;
    private final ProductCartSerivce productCartSerivce;



    @PostMapping("/cart")
    public String cart(LtProductCartDTO dto){
        productCartSerivce.saveCart(dto);
        return "redirect:/product/cart";
    }
    @GetMapping("/cart")
    public String cart(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){
        if(myUserDetails == null){
            return "redirect:/index";
        } else {
            List<LtProductCartDTO> dtoList = productCartSerivce.showCart(myUserDetails.getUser().getUid());
            model.addAttribute("dtoList", dtoList);
            return "/product/cart";
        }
    }

    @GetMapping("/cartDelete")
    public String cart(String jsonData) throws ParseException {
        log.info("jsonData : " + jsonData);
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(jsonData);
        List<Integer> cartNoList = jsonArray.stream().map(obj -> Integer.parseInt((String) obj)).toList();
        for(Integer cartNo : cartNoList){
            productCartSerivce.deleteCart(cartNo);
        }
        return "redirect:/product/cart?success=200";
    }

}

