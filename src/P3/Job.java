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
                printer.acquireTurnstile();
                printer.lockMono();
                printer.releaseTurnstile();
                printer.acquireMono();
                printer.incCurrHead();
                int arrTime = printer.getTime();
                System.out.println("(" + arrTime + ") " + getJobID() + " uses head " + printer.getCurrHead() + " (time: " + getNumPages() + ")");
                for(int i = 0; i < getNumPages(); i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isComplete = true;
                printer.releaseMono();
                printer.decCurrHead();
                printer.setTime(numPages+ arrTime);
                printer.unlockMono();


            } else {
                printer.acquireTurnstile();
                printer.lockColour();
                printer.releaseTurnstile();
                printer.acquireColour();
                printer.incCurrHead();
                int arrTime = printer.getTime();
                System.out.println("(" + arrTime + ") " + getJobID() + " uses head " + printer.getCurrHead() + " (time: " + getNumPages() + ")");
                for(int i = 0; i < getNumPages(); i++) {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isComplete = true;
                printer.releaseColour();
                printer.decCurrHead();
                printer.setTime(numPages+ arrTime);
                printer.unlockColour();
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

