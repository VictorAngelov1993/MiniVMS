package springbootapp.minivms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
