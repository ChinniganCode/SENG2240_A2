package P2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void run() { //remove clunky repeated code
        while(!isComplete) {
            if((this.printer.getJobType() == jobID.charAt(0) || this.printer.getJobType() == 'Z') && !this.printer.isFull() && !this.printer.isUsing(this)) {
                this.runJob();
                }
            }
        }

    public void runJob() {
        this.printer.addJob(this);
        int arrTime = printer.getTime();
        for (int i = 0; i < numPages; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printer.incJobsCompleted();
        printer.decCurrHead();
        printer.setTime(numPages + arrTime);
        System.out.println(jobID + " done" + " time " + printer.getTime());
        isComplete = true;
        printer.removeJob(this);
    }

    public String getJobID() {
        return jobID;
    }
    public int getNumPages() {
        return numPages;
    }
    public boolean isComplete() {
    return isComplete;
    }
}

