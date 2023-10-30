package co.kr.lotteon.controller.admin.product;

import co.kr.lotteon.dto.LtProductCate1DTO;
import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.repository.LtProductRepository;
import co.kr.lotteon.service.LtAdminService;
import co.kr.lotteon.service.LtProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Controller
public class RegisterController {

    @Autowired
    private LtAdminService ltAdminService;
    @Autowired
    private LtProductService ltProductService;

    //파일 저장 경로
    @Value("${upload.path.thumbs}")
    private String filePath;

    @GetMapping("/admin/product/register")
    public String ProductRegister(Model model){
        List<LtProductCate1DTO> pCate1List = ltAdminService.selectLtProductCate1s();
        log.info("pCate1List : " + pCate1List);

        String path = new File(filePath).getAbsolutePath();
        log.info("path : " + path);

        model.addAttribute("pCate1List", pCate1List);
        return "/admin/product/register";
    }

    @PostMapping("/admin/product/register")
    public String ProductRegister(LtProductDTO ltProductDTO, HttpServletRequest request, String cate1, String cate2){

        ltProductDTO.setIp(request.getRemoteAddr());

        System.out.println("Prod 해줘. : " + ltProductDTO);

        ltProductService.insertLtProduct(ltProductDTO);
        return "redirect:/admin/product/list";
    }

}
