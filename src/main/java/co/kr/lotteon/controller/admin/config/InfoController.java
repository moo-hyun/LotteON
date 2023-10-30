package co.kr.lotteon.controller.admin.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class InfoController {

    @GetMapping("/admin/config/info")
    public String info(){
        return "/admin/config/info";
    }


}