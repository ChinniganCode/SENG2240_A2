package P2;

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
    public void run() {
        while (!isComplete) {
            if (printer.checkLine(this) && printer.headAvailable()) { // if printer has a spare head && this job is next
                if (printer.isEmpty()) { //if printer is empty, lock to job type
                    printer.lock(type);
                } else { //if printer has jobs running
                    if (!printer.isAllowed(type)) { //if wrong job type,wait until empty
                        while (!printer.isEmpty()) {
                            try {
                                Thread.sleep(1); //should use wait() here instead
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        printer.lock(type);
                        doJob();
                        break;
                    }
                }
                doJob();
            } else { //if its not the jobs turn or if the printer is full
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (printer.getJobsCompleted() >= printer.getNumJobs()) { //checks if all jobs completed
            System.out.println("(" + printer.getTime() + ")" + " DONE");
            return;
        }
    }

    public void doJob() {
        int arrTime = printer.getTime(); //keep locals copy of arrival time to prevent incorrect incrementation
        headNo = printer.takeHead(); //ties heads to an 'object'
        System.out.println("(" + arrTime + ") " + jobID + " uses head " + headNo + " (time: " + numPages + ")");
        printer.removeJob(this);
        try {
            Thread.sleep(1000 * numPages);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

