package P2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Job extends Thread {
    private String jobID;
    private int numPages;
    private Printer printer;
    private boolean isComplete;
    private boolean isRunning;
    private char type;
    private ReentrantLock lock;

    public Job(String JobID, int numPages, Printer printer) {
        this.jobID = JobID;
        this.numPages = numPages;
        this.printer = printer;
        isComplete = false;
        isRunning = false;
        type = JobID.charAt(0);
        lock = new ReentrantLock();
    }

    @Override
    public synchronized void run() { //remove clunky repeated code
        Matcher matcher = Pattern.compile("\\d+").matcher(jobID);
        matcher.find();
        int jobNum = Integer.valueOf(matcher.group()); //finds job number
        printer.lockTurnstile();
        try { Thread.sleep(jobNum * 2); } catch (InterruptedException e) { e.printStackTrace(); }
        printer.unlockTurnstile();
        while(!isComplete) {
            if(printer.checkLine(this) && printer.getCurrHead() < 3) {
                if(printer.getCurrHead() == 0) {
                    printer.lock(type);
                }
                if(printer.getCurrHead() > 0) {
                    if (type == 'M' && printer.isColourLock() == true) {
                        lock.lock();
                        while (isComplete == false) {
                            try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                            if (printer.getCurrHead() == 0)
                            {
                                try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                                printer.monoLock();
                                printer.incCurrHead();
                                doJob();
                                lock.unlock();
                                break;
                            }
                        }
                    }
                    else if (type == 'C' && printer.isMonoLock() == true) {
                   //     System.out.println("TYPE C and M-lock");
                        lock.lock();
                        printer.colourLock();
                        while (isComplete == false) {
                            try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                            if (printer.getCurrHead() == 0) {
                                try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                                printer.colourLock();
                                printer.incCurrHead();
                                doJob();
                                lock.unlock();
                                break;
                            }
                        }
                    }
                    else if(type == 'C' && printer.isColourLock()==true || type=='M' && printer.isMonoLock() == true)
                    {
                  //      System.out.println("CorrectLock");
                        printer.incCurrHead();
                        doJob();
                    }
                }
                else if(type == 'C' && printer.isColourLock()==true || type=='M' && printer.isMonoLock() == true)
                {
                //    System.out.println("CorrectLock");
                    printer.incCurrHead();
                    doJob();
                }
            }
            else {
                try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
        if (printer.getJobsCompleted() >= printer.getNumJobs()) {
            System.out.println("(" + printer.getTime() + ")" + " DONE");
            return;
        }
    }

    public synchronized void doJob() {
        printer.removeJob(this);
        int arrTime = printer.getTime();
        System.out.println("(" + arrTime + ") " + jobID + " uses head " + printer.getCurrHead() + " (time: " + numPages + ")");
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
        isComplete = true;
    //   System.out.println("JOB: " + jobID + " DONE " + printer.getTime() + " " + printer.getListHead().getJobID() + " "+ type);
      //  System.out.println("currHead=" + printer.getCurrHead() + " MonoLock=" + printer.isMonoLock() + " ColourLock = " + printer.isColourLock());
    }

    public char getType() {
        return type;
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
    public boolean isRunning() {
        return isRunning;
    }
}

