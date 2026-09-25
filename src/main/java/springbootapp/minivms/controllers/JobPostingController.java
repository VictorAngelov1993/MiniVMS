package springbootapp.minivms.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springbootapp.minivms.model.dto.personDto.LoggedUserDto;
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

        // Getting the logged user so that we can do some valiations.
        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");
       // Check if the user is Buyer otherwise access-denied
        // loggedUser == null is used so that if non logged user tries to use the URL directly.
        if(loggedUser == null || !loggedUser.getRole().equals(Role.BUYER)) {
            return "redirect:/access-denied";
        }

        List<JobPosting> jobPostings = this.jobPostingService.getJobPostingForBuyer(loggedUser.getUuid());
        model.addAttribute("jobPostings", jobPostings);

        return "buyer/job-postings";
    }

    @GetMapping("/buyer/create-job-posting")
    public String createJobPosting() {
        return "buyer/create-job-posting";
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
