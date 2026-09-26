package springbootapp.minivms.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import springbootapp.minivms.model.dto.persondto.LoggedUserDto;
import springbootapp.minivms.model.dto.workitemdto.JobPostingCreateDto;
import springbootapp.minivms.model.entities.enums.Role;
import springbootapp.minivms.model.entities.workitems.JobPosting;
import springbootapp.minivms.services.workitemsservices.JobPostingService;

import java.util.List;

// This controller will handle the Job Posting request.

@Controller
public class JobPostingController {

    private JobPostingService jobPostingService;


    @Autowired
    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    // @PreAuthorize("hasRole('BUYER')") this is for the advanced course because it is part of spring security.
    @GetMapping("/buyer/job-postings")
    public String showBuyerJobPostings(HttpSession session, Model model) {

        // Getting the logged user so that we can do some validations.
        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");
       // Check if the user is Buyer otherwise access-denied
        // loggedUser == null is used so that if non logged user tries to use the URL directly.
        // TODO Create Interceptor to handle who user can access the URL. To prevent user directly typing the url
        if(loggedUser == null || !loggedUser.getRole().equals(Role.BUYER)) {
            return "redirect:/access-denied";
        }

        List<JobPosting> jobPostings = this.jobPostingService.getJobPostingForBuyer(loggedUser.getUuid());
        model.addAttribute("jobPostings", jobPostings);

        return "buyer/job-postings";
    }

    @GetMapping("/buyer/create-job-posting")
    public String createJobPosting(Model model) {
        // passing an empty Dto
        model.addAttribute("jobPostingDto", new JobPostingCreateDto());
        return "buyer/create-job-posting";
    }

    @PostMapping("/buyer/create-job-posting")
    public String processCreateJobPosting(@ModelAttribute("jobPostingDto") JobPostingCreateDto dto,
                                          HttpSession session,
                                          Model model) {

        // Getting the logged user we will use it to know who user created the Job Posting
        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");

        try {
            this.jobPostingService.createJobPosting(dto, loggedUser.getUuid());
            return "redirect:/buyer/job-postings";
        } catch (Exception exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "/buyer/create-job-posting";
        }

    }

    @GetMapping("/supplier/job-postings")
    public String showSupplierJobPostings(HttpSession session, Model model) {

        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");
        // Check if the user is Supplier otherwise access-denied
        // loggedUser == null is used so that if non logged user tries to use the URL directly.
        if(loggedUser == null || !loggedUser.getRole().equals(Role.SUPPLIER)) {
            return "redirect:/access-denied";
        }

        List<JobPosting> supplierJobPostings = this.jobPostingService.getJobPostingForSupplier();
        model.addAttribute("jobPostings", supplierJobPostings);


        return "supplier/job-postings";
    }
}
