package P2;

public class Job extends Thread {
    private String JobID;
    private int numPages;
    private Printer printer;
    private boolean finished;

    public Job(String JobID, int numPages) {
        this.JobID = JobID;
        this.numPages = numPages;
    }

}
