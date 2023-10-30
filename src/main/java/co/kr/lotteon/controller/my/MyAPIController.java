package co.kr.lotteon.controller.my;

import co.kr.lotteon.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my/api")
public class MyAPIController {

    @Autowired
    MyService myService;
    @GetMapping("/delivery/{ordNo}")
    public int checkUid(@PathVariable("ordNo") String ordNo){
        // 5 반환
        return myService.deliveryComplete(Integer.parseInt(ordNo));
    }

}
