package co.kr.lotteon.controller;

import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.service.LtProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log4j2
@Controller
public class MainController {

    @Autowired
    private BuildProperties buildProperties;

    @Autowired
    private LtProductService ltProductService;

    @GetMapping("/test")
    public String test(Model model){
        return "redirect:/my/home?test=success";
    }

    @GetMapping(value={"/","/index"})
    public String index(Model model){
        // index

        // Hit Top 8
        List<LtProductDTO> hit = ltProductService.selectProductHit();
        // Discount Top 8
        List<LtProductDTO> discount = ltProductService.selectProductDiscount();
        // Score Top 8
        List<LtProductDTO> score = ltProductService.selectProductScore();
        // New Top 8
        List<LtProductDTO> newProd = ltProductService.selectProductNew();
        // sold Top 5 (Side Best prod)
        List<LtProductDTO> sold = ltProductService.selectProductSold();

        model.addAttribute("hitTop8", hit);
        model.addAttribute("discountTop8", discount);
        model.addAttribute("scoreTop8", score);
        model.addAttribute("newTop8", newProd);
        model.addAttribute("soldTop5", sold);

        // 자동업로드시 파일 Version Check
        // build.gradle 파일 맨 밑에 빌드 정보를 가져오기 위해 buildInfo() 호출 해야됨
        String appName = buildProperties.getName(); // settings.gradle 파일에서 앱이름 가져옴
        String version = buildProperties.getVersion(); // build.gradle 파일에서 버전값 가져옴
        System.out.println("appName : " + appName);
        System.out.println("version : " + version);

        model.addAttribute("appInfo", appName+version);

        return "/index";
    }

}
