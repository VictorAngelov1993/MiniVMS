package springbootapp.minivms.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springbootapp.minivms.model.dto.persondto.BuyerDashboardStatsDto;
import springbootapp.minivms.model.dto.persondto.LoggedUserDto;
import springbootapp.minivms.model.dto.persondto.SupplierDashboardDto;
import springbootapp.minivms.model.entities.persons.Buyer;
import springbootapp.minivms.model.entities.persons.Supplier;
import springbootapp.minivms.repositories.personrepositories.BuyerRepository;
import springbootapp.minivms.services.personservices.SupplierService;
import springbootapp.minivms.services.workitemsservices.JobPostingService;
import springbootapp.minivms.services.workitemsservices.JobSeekerService;

import java.util.Optional;

@Controller
public class DashboardController {

    private JobPostingService jobPostingService;
    // Below should be a Service
    // TODO fix later
    private BuyerRepository buyerRepository;
    private SupplierService supplierService;
    private JobSeekerService jobSeekerService;

    @Autowired
    public DashboardController(JobPostingService jobPostingService,
                               BuyerRepository buyerRepository,
                               SupplierService supplierService,
                               JobSeekerService jobSeekerService) {
        this.jobPostingService = jobPostingService;
        this.buyerRepository = buyerRepository;
        this.supplierService = supplierService;
        this.jobSeekerService = jobSeekerService;
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

        // TODO later here I need to count and add the other status to the stats and then to the model.

        // add the status to the model so that thymeleaf can get them.
        model.addAttribute("stats", stats);

        // return the dashboard
        return "buyer/dashboard";
    }

    @GetMapping("supplier/dashboard")
    public String goToSupplierDashboard(Model model, HttpSession session) {

        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");
        Optional<Supplier> loggedSupplier = this.supplierService.getSupplierByUuid(loggedUser.getUuid());
        Supplier supplier = null;

        try{
            supplier = loggedSupplier.orElseThrow(() -> new IllegalStateException("Something went wrong. Contact Support"));
        } catch (Exception exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "redirect:/access-denied";
        }
        SupplierDashboardDto supplierDashboardStats = new SupplierDashboardDto();
        long countSupplierJobSeekers = this.jobSeekerService.countTheSupplierJobSeekers(supplier);
        supplierDashboardStats.setJobSeekerCount(countSupplierJobSeekers);
        model.addAttribute("stats", supplierDashboardStats);

        return "supplier/dashboard";
    }

    @GetMapping("worker/dashboard")
    public String goToWorkerDashboard(Model model) {
        return "worker/dashboard";
    }


}
