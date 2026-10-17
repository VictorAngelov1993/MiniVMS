package springbootapp.minivms.mappers;

import org.springframework.stereotype.Component;
import springbootapp.minivms.model.dto.workitemdto.TimeSheetEditDto;
import springbootapp.minivms.model.dto.workitemdto.TimeSheetRowDto;
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
        timeSheetEditDto.setDays(timeSheet.getDays());
        timeSheetEditDto.setNotes(timeSheet.getNotes());
        return timeSheetEditDto;
    }
}
