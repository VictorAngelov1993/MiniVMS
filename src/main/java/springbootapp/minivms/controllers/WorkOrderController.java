package springbootapp.minivms.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springbootapp.minivms.model.dto.persondto.LoggedUserDto;
import springbootapp.minivms.model.dto.workitemdto.JobPostingDetailsDto;
import springbootapp.minivms.model.dto.workitemdto.JobSeekerSubmitDto;
import springbootapp.minivms.model.dto.workitemdto.WorkOrderCardDto;
import springbootapp.minivms.model.dto.workitemdto.WorkOrderCreateDto;
import springbootapp.minivms.services.workitemsservices.JobPostingService;
import springbootapp.minivms.services.workitemsservices.JobSeekerSubmissionService;
import springbootapp.minivms.services.workitemsservices.WorkOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class WorkOrderController {
    private final WorkOrderService workOrderService;
    private final JobPostingService jobPostingService;
    private final JobSeekerSubmissionService jobSeekerSubmissionService;

    @Autowired
    public WorkOrderController(WorkOrderService workOrderService,
                               JobPostingService jobPostingService,
                               JobSeekerSubmissionService jobSeekerSubmissionService) {
        this.workOrderService = workOrderService;
        this.jobPostingService = jobPostingService;
        this.jobSeekerSubmissionService = jobSeekerSubmissionService;
    }

    @GetMapping("/buyer/workorders")
    public String loadTheWorkOrderList(HttpSession session,
                                       Model model) {

        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");
        // Get all the Work Orders of the User
        List<WorkOrderCardDto> allWorkOrderCards = this.workOrderService.getAllWorkOrdersAsCards(loggedUser.getUuid());
        model.addAttribute("workOrders", allWorkOrderCards);

        return "/buyer/workorders";

    }

    @GetMapping("/buyer/create-workorder")
    public String showWorkOrderCreatePage(@RequestParam("jobPostingId") String jobPostingId,
                                          HttpSession session,
                                          Model model) {
        // Get the details of the logged user
        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");
        // Add an empty WO dto that we will fill later
        model.addAttribute("workOrderCreateDto", new WorkOrderCreateDto());
        // Get the Job Posting Details some of them will be prefilled in the form.
        JobPostingDetailsDto jobPosting = this.jobPostingService.getJobPostingCreateDtoById(jobPostingId);
        model.addAttribute("jobPosting", jobPosting);
        // Auto generate Work Order id and pass it
        String workOrderId = this.workOrderService.autoGenerateWorkOrderId();
        model.addAttribute("generatedWorkOrderId", workOrderId);
        // Get all the Job Seekers who are submitted for the Job Posting.
        List<JobSeekerSubmitDto> jobSeekers = this.jobSeekerSubmissionService.getAllJobSeekersSubmittedForThisJobPosting(jobPostingId);
        model.addAttribute("jobSeekers", jobSeekers);

        return "buyer/create-workorder";
    }

    // TODO create the POST method that will actually save the WO in the DB, but first I need Job Seekers



}
