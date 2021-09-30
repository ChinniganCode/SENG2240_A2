package P3;

public class Job extends Thread {
    private String jobID;
    private int numPages;
    private Printer printer;
    private char jobType;
    private int headNo;

    public Job(String JobID, int numPages, Printer printer) {
        this.jobID = JobID;
        this.numPages = numPages;
        this.printer = printer;
        this.headNo = 0;
        this.jobType = jobID.charAt(0);
    }

    @Override
    public void run() { //remove clunky repeated code
        printer.acquireEntryLock(); //allows one job in at a time
        printer.lock(jobType); //locks jobtype to the first job specified
        printer.releaseEntryLock(); //releases turnstile, allowing next job to enter
        printer.acquireSem(jobType); //
        headNo = printer.takeHead();
        int arrTime = printer.getTime();
        System.out.println("(" + arrTime + ") " + jobID + " uses head " + headNo + " (time: " + numPages + ")");
            try {
                Thread.sleep(1000 * numPages);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        printer.incJobsCompleted();
        printer.releaseSem(jobType);
        printer.releaseHead(headNo);
        headNo = 0;
        printer.setTime(numPages + arrTime);
        printer.unlock(jobType);
        if (printer.getJobsCompleted() >= printer.getNumJobs()) {
            System.out.println("(" + printer.getTime() + ")" + " DONE");
            return;
        }
    }
}



