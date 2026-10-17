package springbootapp.minivms.mappers;

import org.springframework.stereotype.Component;
import springbootapp.minivms.model.dto.workitemdto.timesheet.TimeSheetDayDto;
import springbootapp.minivms.model.dto.workitemdto.timesheet.TimeSheetEditDto;
import springbootapp.minivms.model.dto.workitemdto.timesheet.TimeSheetRowDto;
import springbootapp.minivms.model.entities.workitems.timesheets.TimeSheet;
import springbootapp.minivms.model.entities.workitems.timesheets.TimeSheetDay;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TimeSheetMapper {

    private TimeSheetRowDto mapTimeSheetToTimeSheetRowDto(TimeSheet timeSheet) {
        TimeSheetRowDto timeSheetRowDto = new TimeSheetRowDto();
        timeSheetRowDto.setId(timeSheet.getTimeSheetId());
        timeSheetRowDto.setWeekStartDate(timeSheet.getWeekStartDay());
        timeSheetRowDto.setWeekEndDate(timeSheet.getWeekEndDay());
        timeSheetRowDto.setStatus(timeSheet.getStatus());
        // Below will get each day of the Time Sheet and will sum the hours. All Time Sheets are weekly
        BigDecimal totalHours = timeSheet.getDays().stream()
                .map(TimeSheetDay::getHours)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        timeSheetRowDto.setTotalHours(totalHours);

        return timeSheetRowDto;
    }

    public List<TimeSheetRowDto> mapListOfTimeSheetsToListOfTimeSheetRowDto(List<TimeSheet> timeSheets) {
        return timeSheets.stream().map(this::mapTimeSheetToTimeSheetRowDto).toList();
    }

    public TimeSheetEditDto getEditDtoFromTimeSheet(TimeSheet timeSheet) {
        String workOrderId = timeSheet.getWorkOrder().getWorkOrderId();
        TimeSheetEditDto timeSheetEditDto = new TimeSheetEditDto();
        timeSheetEditDto.setId(timeSheet.getTimeSheetId());
        timeSheetEditDto.setWeekStartDate(timeSheet.getWeekStartDay());
        timeSheetEditDto.setWeekEndDate(timeSheet.getWeekEndDay());
        timeSheetEditDto.setWorkOrderId(workOrderId);
        List<TimeSheetDayDto> daysAsDto = this.mapListOfTimeSheetDaysToListOfDtos(timeSheet.getDays());
        timeSheetEditDto.setDays(daysAsDto);
        timeSheetEditDto.setNotes(timeSheet.getNotes());
        return timeSheetEditDto;
    }

    private TimeSheetDayDto mapTimeSheetDayToDto(TimeSheetDay timeSheetDay) {
        TimeSheetDayDto dto = new TimeSheetDayDto();
        dto.setDate(timeSheetDay.getDate());
        dto.setHours(timeSheetDay.getHours());
        return dto;
    }
    private List<TimeSheetDayDto> mapListOfTimeSheetDaysToListOfDtos(List<TimeSheetDay> timeSheetDays) {
        return timeSheetDays.stream().map(this::mapTimeSheetDayToDto).toList();
    }


}
