package springbootapp.minivms.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import springbootapp.minivms.model.dto.persondto.LoggedUserDto;
import springbootapp.minivms.model.dto.workitemdto.JobSeekerCreateDto;
import springbootapp.minivms.model.entities.workitems.JobSeeker;
import springbootapp.minivms.services.workitemsservices.JobSeekerService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class JobSeekerController {

    private JobSeekerService jobSeekerService;
    @Autowired
    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @GetMapping("/supplier/job-seekers")
    public String loadJobSeekers(HttpSession session, Model model) {

        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");

        // Get the Job Seekers and add them to the model
        List<JobSeeker> jobSeekers = this.jobSeekerService.getAllJobSeekersForTheSupplier(loggedUser.getUuid());
        model.addAttribute("jobSeekers", jobSeekers);

        return "supplier/job-seekers";
    }

    @GetMapping("supplier/create-job-seeker")
    public String showCreateJobSeeker(Model model) {
        model.addAttribute("JobSeekerDto", new JobSeekerCreateDto());
        return "supplier/create-job-seeker";
    }

    @PostMapping("supplier/create-job-seeker")
    public String createJobSeeker(HttpSession session,
                                  @ModelAttribute("jobSeekerDto") JobSeekerCreateDto jobSeekerCreateDto,
                                  Model model) {

        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");

        this.jobSeekerService.createJobSeeker(jobSeekerCreateDto, loggedUser.getUuid());

        return "redirect:/supplier/job-seekers";

    }
}
