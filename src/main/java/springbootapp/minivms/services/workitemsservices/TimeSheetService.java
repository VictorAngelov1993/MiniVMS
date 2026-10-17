package springbootapp.minivms.services.workitemsservices;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springbootapp.minivms.mappers.TimeSheetMapper;
import springbootapp.minivms.model.dto.workitemdto.TimeSheetEditDto;
import springbootapp.minivms.model.dto.workitemdto.TimeSheetRowDto;
import springbootapp.minivms.model.entities.enums.TimeSheetStatus;
import springbootapp.minivms.model.entities.persons.Worker;
import springbootapp.minivms.model.entities.workitems.WorkOrder;
import springbootapp.minivms.model.entities.workitems.timesheets.TimeSheet;
import springbootapp.minivms.model.entities.workitems.timesheets.TimeSheetDay;
import springbootapp.minivms.repositories.personrepositories.WorkerRepository;
import springbootapp.minivms.repositories.workitems.TimeSheetRepository;
import springbootapp.minivms.repositories.workitems.WorkOrderRepository;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TimeSheetService {

    private final TimeSheetRepository timeSheetRepository;
    private final WorkerRepository workerRepository;
    private final TimeSheetMapper timeSheetMapper;
    private final WorkOrderRepository workOrderRepository;


    public TimeSheetService(TimeSheetRepository timeSheetRepository,
                            WorkerRepository workerRepository,
                            TimeSheetMapper timeSheetMapper,
                            WorkOrderRepository workOrderRepository) {
        this.timeSheetRepository = timeSheetRepository;
        this.workerRepository = workerRepository;
        this.timeSheetMapper = timeSheetMapper;
        this.workOrderRepository = workOrderRepository;
    }

    private List<TimeSheet> getWorkerTimeSheets(UUID workerUuid) {
        Worker worker = this.workerRepository.getWorkerByUuid(workerUuid).orElseThrow(() -> new RuntimeException("Invalid Worker id"));
        List<WorkOrder> workerWorkOrders = worker.getWorkOrders();

        // Below is the method that will generate the Worker Time Sheets on demand.
        // In the future I will test the performance and check if adding time sheets is better to be in the registration method.
        for(WorkOrder wo : workerWorkOrders) {
            this.generateWorkerTimeSheets(wo);
        }


        List<TimeSheet> workerTimeSheets = worker.getWorkOrders().stream()
                .flatMap(wo -> wo.getTimeSheets().stream())
                .toList();

        return workerTimeSheets;
    }

    public List<TimeSheetRowDto> getWorkerTimeSheetsRowDto(UUID workerUuid) {
        return this.timeSheetMapper.mapListOfTimeSheetsToListOfTimeSheetRowDto(this.getWorkerTimeSheets(workerUuid));
    }

    private void generateWorkerTimeSheets(WorkOrder workerWorkOrder) {
        LocalDate start = workerWorkOrder.getStartDate();
        LocalDate end = workerWorkOrder.getEndDate();
        // below will get Monday of the starting week.
        LocalDate weekStartDay = start.with(DayOfWeek.MONDAY);

        while (!weekStartDay.isAfter(end)) {
            LocalDate weekEnd = weekStartDay.plusDays(6);

            LocalDate finalWeekStartDay = weekStartDay;
            boolean doesTimeSheetExist = workerWorkOrder.getTimeSheets().stream()
                    .anyMatch(timeSheet -> timeSheet.getWeekStartDay().equals(finalWeekStartDay));

            if(!doesTimeSheetExist) {
                TimeSheet timeSheet = new TimeSheet();
                timeSheet.setWeekStartDay(weekStartDay);
                timeSheet.setWeekEndDay(weekEnd);
                timeSheet.setStatus(TimeSheetStatus.DRAFT);
                timeSheet.setWorkOrder(workerWorkOrder);
                timeSheet.setTimeSheetId(this.generateTimeSheetId());

                for(int a = 0; a < 7; a++) {
                    TimeSheetDay timeSheetDay = new TimeSheetDay();
                    timeSheetDay.setTimeSheet(timeSheet);
                    timeSheetDay.setDate(weekStartDay.plusDays(a));

                    if(a < 5) {
                        timeSheetDay.setHours(BigDecimal.valueOf(8));
                    } else {
                        timeSheetDay.setHours(BigDecimal.ZERO);
                    }

                    timeSheet.getDays().add(timeSheetDay);
                }

                workerWorkOrder.getTimeSheets().add(timeSheet);
                this.workOrderRepository.save(workerWorkOrder);

            }

            weekStartDay = weekStartDay.plusWeeks(1);
        }

    }

    private String generateTimeSheetId() {
        long timeSheetCount = this.timeSheetRepository.count();
        return String.format("TS-%05d", timeSheetCount + 1);
    }

    private TimeSheet getTimeSheetById(String timeSheetId) {
        return this.timeSheetRepository.getTimeSheetByTimeSheetId(timeSheetId);
    }

    public TimeSheetEditDto getTimeSheetEditDtoById(String timeSheetId) {
        return this.timeSheetMapper.getEditDtoFromTimeSheet(this.getTimeSheetById(timeSheetId));
    }

}
