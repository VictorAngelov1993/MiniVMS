package springbootapp.minivms.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springbootapp.minivms.model.dto.persondto.LoggedUserDto;
import springbootapp.minivms.model.dto.workitemdto.TimeSheetRowDto;
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



        List<TimeSheetRowDto> workerTimeSheets = this.timeSheetService.getWorkerTimeSheetsRowDto(loggedUser.getUuid());
        model.addAttribute("timesheets", workerTimeSheets);


        return "worker/timesheets";
    }

}
