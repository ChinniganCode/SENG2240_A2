package P3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Job extends Thread {
    private String jobID;
    private int numPages;
    private Printer printer;

    public Job(String JobID, int numPages, Printer printer) {
        this.jobID = JobID;
        this.numPages = numPages;
        this.printer = printer;
    }

    @Override
    public void run() { //remove clunky repeated code
        char jobType = jobID.charAt(0);
        Matcher matcher = Pattern.compile("\\d+").matcher(jobID);
        matcher.find();
        int jobNum = Integer.valueOf(matcher.group());

        printer.acquireTurnstile();
        if (jobType == 'M') {
            printer.lockMono();
        } else {
            printer.lockColour();
        }
        //inserts a small delay based on job number, potentially bad practice (i.e. slower systems)
        try {
            Thread.sleep(jobNum * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printer.releaseTurnstile();

        if (jobType == 'M') {
            printer.acquireMono();
        } else {
            printer.acquireColour();
        }
        printer.incCurrHead();
        int arrTime = printer.getTime();
        System.out.println("(" + arrTime + ") " + getJobID() + " uses head " + printer.getCurrHead() + " (time: " + getNumPages() + ")");
        for (int i = 0; i < getNumPages(); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printer.incJobsCompleted();
        if (jobType == 'M') {
            printer.releaseMono();
        } else {
            printer.releaseColour();
        }
        printer.decCurrHead();
        printer.setTime(numPages + arrTime);
        if (jobType == 'M') {
            printer.unlockMono();
        } else {
            printer.unlockColour();
        }
        if (printer.getJobsCompleted() >= printer.getNumJobs()) {
            System.out.println("(" + printer.getTime() + ")" + " DONE");
            return;
        }
    }

    public String getJobID() {
        return jobID;
    }

    public int getNumPages() {
        return numPages;
    }
}


