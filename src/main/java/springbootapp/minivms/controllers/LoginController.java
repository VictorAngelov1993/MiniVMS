package springbootapp.minivms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        // for now, I will not add Model to the login
        // todo add success message after registration
        //  and more advanced dynamic data the username to be prefilled when registration is successful
        return "login";
    }
}
