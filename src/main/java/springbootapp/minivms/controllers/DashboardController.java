package springbootapp.minivms.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springbootapp.minivms.model.dto.persondto.BuyerDashboardStatsDto;
import springbootapp.minivms.model.dto.persondto.LoggedUserDto;
import springbootapp.minivms.model.entities.persons.Buyer;
import springbootapp.minivms.repositories.personrepositories.BuyerRepository;
import springbootapp.minivms.services.workitemsservices.JobPostingService;

import java.util.Optional;

@Controller
public class DashboardController {

    private JobPostingService jobPostingService;
    private BuyerRepository buyerRepository;

    @Autowired
    public DashboardController(JobPostingService jobPostingService,
                               BuyerRepository buyerRepository) {
        this.jobPostingService = jobPostingService;
        this.buyerRepository = buyerRepository;
    }


    @GetMapping("buyer/dashboard")
    public String goToBuyerDashboard(Model model, HttpSession session) {

        // we get the user who is logged in
        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");
        // create empty stats DTO to fill later
        BuyerDashboardStatsDto stats = new BuyerDashboardStatsDto();
        // Get the Buyer who is the logged user.
        Optional<Buyer> getBuyer = this.buyerRepository.getBuyerByUuid(loggedUser.getUuid());
        Buyer buyer = null;
        try {
            buyer = getBuyer.orElseThrow(() -> new IllegalStateException("Something went wrong. Contact Support"));
        } catch (Exception exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "redirect:/access-denied";
        }
        // get the Job Postings for the logged user
        int countJobPostings = this.jobPostingService.countJobPostingsForBuyer(buyer);
        stats.setJobPostingCount(countJobPostings);
        // add the status to the model so that thymeleaf can get them.
        model.addAttribute("stats", stats);

        // return the dashboard
        return "buyer/dashboard";
    }

    @GetMapping("supplier/dashboard")
    public String goToSupplierDashboard(Model model) {
        return "supplier/dashboard";
    }

    @GetMapping("worker/dashboard")
    public String goToWorkerDashboard(Model model) {
        return "worker/dashboard";
    }


}
