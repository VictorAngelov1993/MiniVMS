package springbootapp.minivms.model.dto.workitemdto;

import springbootapp.minivms.model.entities.workitems.JobSeeker;

import java.util.List;

public class WorkOrderCreateDto {
    private List<JobSeeker> jobSeekers;
    private String notes;

    public WorkOrderCreateDto() {

    }

    public List<JobSeeker> getJobSeekers() {
        return jobSeekers;
    }

    public void setJobSeekers(List<JobSeeker> jobSeekers) {
        this.jobSeekers = jobSeekers;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
