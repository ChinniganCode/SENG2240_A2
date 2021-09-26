package P2;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Job extends Thread {
    private String jobID;
    private int numPages;
    private Printer printer;
    private boolean isComplete;
    private char type;
    private int headNo;

    public Job(String JobID, int numPages, Printer printer) {
        this.jobID = JobID;
        this.numPages = numPages;
        this.printer = printer;
        isComplete = false;
        type = JobID.charAt(0);
        headNo = 0;
    }

    @Override
    public synchronized void run() { //remove clunky repeated code
        while(!isComplete) {
            if(printer.checkLine(this) && printer.headAvailable()) { // if printer has a spare head && this job is next
                if(printer.isEmpty()) { //if printer is empty, lock to job type
                    printer.lock(type);
                }
                if(!printer.isEmpty()) { //if printer has jobs running
                    if (!printer.isAllowed(type)) {
                        while (!isComplete) {
                            try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                            if (printer.isEmpty()) //if printer is empty
                            {
                                try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                                printer.lock(type);
                                doJob();
                                break;
                            }
                        }
                    } else if(printer.isAllowed(type))
                    {
                        doJob();
                    }
                } else if(printer.isAllowed(type))
                {
                    doJob();
                }
            }
            else {
                try { Thread.sleep(15); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
        if (printer.getJobsCompleted() >= printer.getNumJobs()) {
            System.out.println("(" + printer.getTime() + ")" + " DONE");
            return;
        }
    }

    public synchronized void doJob() {
        int arrTime = printer.getTime();
        headNo = printer.takeHead();
        System.out.println("(" + arrTime + ") " + jobID + " uses head " + headNo + " (time: " + numPages + ")");
        printer.removeJob(this);
            try { Thread.sleep(1000 * numPages); } catch (InterruptedException e) { e.printStackTrace(); }
        printer.incJobsCompleted();
        printer.releaseHead(headNo);
        headNo = 0;
        printer.setTime(numPages + arrTime);
        isComplete = true;
    }

    public String getJobID() {
        return jobID;
    }
}

