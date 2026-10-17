package springbootapp.minivms.repositories.workitems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springbootapp.minivms.model.entities.persons.Worker;
import springbootapp.minivms.model.entities.workitems.timesheets.TimeSheet;

import java.util.UUID;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, UUID> {

    TimeSheet getTimeSheetByTimeSheetId(String timeSheetId);
    @Query("""
    select count(*)
    from TimeSheet as ts
    where ts.workOrder.worker = :worker
    and ts.status in ('DRAFT', 'REJECTED')
""")
    long countAllPendingWorkerTimeSheets(@Param("worker")Worker worker);
}
