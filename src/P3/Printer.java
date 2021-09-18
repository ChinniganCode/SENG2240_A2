package P3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Printer {
    private AtomicInteger time;
    private Semaphore noOfHeadsSem;
    private Semaphore modeSem;
    private Semaphore colourLock;
    private Semaphore monoLock;
    private char mode;
    private int numJobs;
    private int jobsCompleted;
    private AtomicInteger currHead;
    private int highest;

    public Printer() {

        noOfHeadsSem = new Semaphore(3, true);
        modeSem = new Semaphore(1, true);
        colourLock = new Semaphore(1, true);
        monoLock = new Semaphore(1, true);
        numJobs = 0;
        jobsCompleted = 0;
        mode = 'Z';
        currHead = new AtomicInteger(0);
        time = new AtomicInteger(0);
        highest = 0;
    }
    public void setHighest(int hi) {
        if(hi > highest) {
            highest = hi;
        }
    }
    public void acquireSem(Job job) {
        acquireModeSem(job);
        printJob(job);
        releaseModeSem(job);
    }
    public void acquireModeSem(Job job) {
        if(currHead.get() == 0) { //if printer is empty, change mode
            try {
                modeSem.acquire();
                System.out.println("Printer empty");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mode = job.getJobID().charAt(0);
        }
        if(job.getJobID().charAt(0) != mode && currHead.get() != 3) { //if current mode is different, change mode
            try {
                modeSem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mode = job.getJobID().charAt(0);
        }


    }
    public void releaseModeSem(Job job) {
        if(currHead.get() == 0) {
            modeSem.release();
            mode = 'Z';
           // System.out.println("ModeSem Released");
        }
    }
    public void printJob(Job job) {
            try {
                noOfHeadsSem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currHead.incrementAndGet();
            setHighest(job.getNumPages());
            System.out.println("(" + time + ") " + job.getJobID() + " uses head " + currHead + " (time: " + job.getNumPages() + ")");
            for(int i = 0; i < job.getNumPages(); i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            noOfHeadsSem.release();
            currHead.decrementAndGet();
            time.set(highest + time.get());
            highest = 0;
            job.setComplete();
            // System.out.println("Job " + job.getJobID() + " done");
    }

    public char getMode() {
        return mode;
    }
    public void setNumJobs(int numJobs) {
        this.numJobs = numJobs;
    }
    public int getNumJobs() {
        return numJobs;
    }

    public void incJobsCompleted() {
        jobsCompleted++;
    }
    public int getJobsCompleted() {
        return jobsCompleted;
    }
    public void setMode(char mode) {
        this.mode = mode;
    }

}
