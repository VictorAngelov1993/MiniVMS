package springbootapp.minivms.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springbootapp.minivms.model.dto.persondto.LoggedUserDto;
import springbootapp.minivms.model.dto.workitemdto.timesheet.TimeSheetEditDto;
import springbootapp.minivms.model.dto.workitemdto.timesheet.TimeSheetRowDto;
import springbootapp.minivms.model.dto.workitemdto.timesheet.TimeSheetSubmitDto;
import springbootapp.minivms.services.workitemsservices.TimeSheetService;

import java.util.List;

@Controller
public class TimeSheetController {

    private final TimeSheetService timeSheetService;

    @Autowired
    public TimeSheetController(TimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @GetMapping("worker/timesheets")
    public String displayTimeSheetsForTheWorker(HttpSession session, Model model) {

        LoggedUserDto loggedUser = (LoggedUserDto) session.getAttribute("loggedUser");

        // get all worker time sheets from all work orders and display them.
        List<TimeSheetRowDto> workerTimeSheets = this.timeSheetService.getWorkerTimeSheetsRowDto(loggedUser.getUuid());
        model.addAttribute("timesheets", workerTimeSheets);

        return "worker/timesheets";
    }

    @GetMapping("/worker/timesheets/{id}/edit")
    public String displayTimeSheetEditPage(@PathVariable String id,
                                           Model model) {
        TimeSheetEditDto timeSheet = this.timeSheetService.getTimeSheetEditDtoById(id);
        model.addAttribute("timesheet", timeSheet);

        return "worker/timesheet-edit";
    }

    @PostMapping("/worker/timesheets/{id}/submit")
    public String submitTimeSheet(@PathVariable("id") String id,
                                  @ModelAttribute("timesheet")TimeSheetSubmitDto submitDto) {

        if (submitDto.getTimeSheetId() == null) {
            submitDto.setTimeSheetId(id);
        }

        this.timeSheetService.submitTimeSheet(submitDto);

        return "redirect:/worker/timesheets";

    }

}
