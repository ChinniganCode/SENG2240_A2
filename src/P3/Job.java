package P3;

public class Job extends Thread {
    private String jobID;
    private int numPages;
    private Printer printer;
    private boolean isComplete;

    public Job(String JobID, int numPages, Printer printer) {
        this.jobID = JobID;
        this.numPages = numPages;
        this.printer = printer;
        isComplete = false;
    }

    @Override
    public void run() {
        while (isComplete == false) {
            if(jobID.charAt(0) == 'M') {
                try {
                    printer.acquireMonoSem(this);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    printer.acquireColourSem(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        }
    public String getJobID() {
        return jobID;
    }
    public int getNumPages() {
        return numPages;
    }
    public void setComplete() {
        isComplete = true;
    }
}

