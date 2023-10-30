package co.kr.lotteon.controller.policy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    @GetMapping(value = "/buyer")
    public String buyer(Model model) {

        return "/policy/buyer";
    }

    @GetMapping(value = "/finance")
    public String finance(Model model) {

        return "/policy/finance";
    }

    @GetMapping(value = "/location")
    public String location(Model model) {

        return "/policy/location";
    }

    @GetMapping(value = "/privacy")
    public String privacy(Model model) {

        return "/policy/privacy";
    }

    @GetMapping(value = "/seller")
    public String seller(Model model) {

        return "/policy/seller";
    }

}
