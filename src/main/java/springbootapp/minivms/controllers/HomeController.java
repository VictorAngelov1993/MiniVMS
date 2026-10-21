package springbootapp.minivms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        // TODO maybe the go to home button should send the user to the dashboard intend of login page
        return "access-denied";
    }
}
