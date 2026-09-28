package springbootapp.minivms.model.dto.workitemdto;

import springbootapp.minivms.model.entities.workitems.JobSeeker;

import java.util.List;

public class WorkOrderCreateDto {
    private List<JobSeeker> jobSeekers;
    private String notes;

}
