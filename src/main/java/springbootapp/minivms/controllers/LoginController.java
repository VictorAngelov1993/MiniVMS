package springbootapp.minivms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import springbootapp.minivms.model.dto.personDto.PersonLoginDto;
import springbootapp.minivms.model.entities.persons.AbstractPerson;
import springbootapp.minivms.model.entities.persons.Buyer;
import springbootapp.minivms.model.entities.persons.Supplier;
import springbootapp.minivms.model.entities.persons.Worker;
import springbootapp.minivms.services.personServices.PersonAuthenticationService;

@Controller
public class LoginController {

    private final PersonAuthenticationService personAuthenticationService;

    @Autowired
    public LoginController(PersonAuthenticationService personAuthenticationService) {
        this.personAuthenticationService = personAuthenticationService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // for now, I will not add Model to the login
        // todo add success message after registration
        //  and more advanced dynamic data the username to be prefilled when registration is successful

        model.addAttribute("personLoginDto", new PersonLoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("personLoginDto") PersonLoginDto personLoginDto,
                               Model model) {
        String username = personLoginDto.getUsername();
        String password = personLoginDto.getPassword();

        try{

            AbstractPerson personToLogIn = this.personAuthenticationService.authenticate(username, password);

            // TODO: store user in session later, for now just redirect by role

            if(personToLogIn instanceof Buyer) {
                return "redirect:/buyer/dashboard";
            } else if (personToLogIn instanceof Supplier) {
                return "redirect:/supplier/dashboard";
            } else if (personToLogIn instanceof Worker) {
                return "redirect:/worker/dashboard";
            }
            //fallback
            return "redirect:/";

        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "login";
        }


    }


}
