package springbootapp.minivms.model.dto.persondto;

public class BuyerDashboardStatsDto {
    private int jobPostingCount;
    private int workOrderCount;
    private int activeWorkerCount;
    private int pendingTimesheetCount;


    public BuyerDashboardStatsDto() {

    }

    public int getJobPostingCount() {
        return jobPostingCount;
    }

    public void setJobPostingCount(int jobPostingCount) {
        this.jobPostingCount = jobPostingCount;
    }

    public int getWorkOrderCount() {
        return workOrderCount;
    }

    public void setWorkOrderCount(int workOrderCount) {
        this.workOrderCount = workOrderCount;
    }

    public int getActiveWorkerCount() {
        return activeWorkerCount;
    }

    public void setActiveWorkerCount(int activeWorkerCount) {
        this.activeWorkerCount = activeWorkerCount;
    }

    public int getPendingTimesheetCount() {
        return pendingTimesheetCount;
    }

    public void setPendingTimesheetCount(int pendingTimesheetCount) {
        this.pendingTimesheetCount = pendingTimesheetCount;
    }
}
