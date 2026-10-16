package springbootapp.minivms.services.workitemsservices;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import springbootapp.minivms.mappers.TimeSheetMapper;
import springbootapp.minivms.model.dto.workitemdto.TimeSheetRowDto;
import springbootapp.minivms.model.entities.persons.Worker;
import springbootapp.minivms.model.entities.workitems.WorkOrder;
import springbootapp.minivms.model.entities.workitems.timesheets.TimeSheet;
import springbootapp.minivms.repositories.personrepositories.WorkerRepository;
import springbootapp.minivms.repositories.workitems.TimeSheetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TimeSheetService {

    private final TimeSheetRepository timeSheetRepository;
    private final WorkerRepository workerRepository;
    private final TimeSheetMapper timeSheetMapper;


    public TimeSheetService(TimeSheetRepository timeSheetRepository,
                            WorkerRepository workerRepository,
                            TimeSheetMapper timeSheetMapper) {
        this.timeSheetRepository = timeSheetRepository;
        this.workerRepository = workerRepository;
        this.timeSheetMapper = timeSheetMapper;
    }

    private List<TimeSheet> getWorkerTimeSheets(UUID workerUuid) {
        Worker worker = this.workerRepository.getWorkerByUuid(workerUuid).orElseThrow(() -> new RuntimeException("Invalid Worker id"));
        List<WorkOrder> workerWorkOrders = worker.getWorkOrders();
        List<TimeSheet> workerTimeSheets = worker.getWorkOrders().stream()
                .flatMap(wo -> wo.getTimeSheets().stream())
                .toList();

        return workerTimeSheets;
    }

    public List<TimeSheetRowDto> getWorkerTimeSheetsRowDto(UUID workerUuid) {
        return this.timeSheetMapper.mapListOfTimeSheetsToListOfTimeSheetRowDto(this.getWorkerTimeSheets(workerUuid));
    }

}
