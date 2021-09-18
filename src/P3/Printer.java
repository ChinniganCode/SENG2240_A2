package P3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Printer {
    private int time;
    private Semaphore empty;
    private LightSwitch colourSwitch;
    private LightSwitch monoSwitch;
    private Semaphore colourMultiplex;
    private Semaphore monoMultiplex;
    private Semaphore turnstile;
    private Semaphore updateTime;
    private char mode;
    private int numJobs;
    private int jobsCompleted;
    private int currHead;
    private int lastTime;

    public Printer() {
        empty = new Semaphore(1);
        colourSwitch = new LightSwitch();
        monoSwitch = new LightSwitch();
        colourMultiplex = new Semaphore (3);
        monoMultiplex = new Semaphore(3);
        turnstile = new Semaphore(1);
        updateTime = new Semaphore(0);
        numJobs = 0;
        jobsCompleted = 0;
        mode = 'Z';
        currHead = 0;
        time = 0;
        lastTime = 0;
    }
    public void acquireColourSem(Job job) throws InterruptedException {
        turnstile.acquire();
        colourSwitch.lock(empty);
        turnstile.release();
        colourMultiplex.acquire();
        currHead++;
        time += lastTime;
        System.out.println("(" + time + ") " + job.getJobID() + " uses head " + currHead + " (time: " + job.getNumPages() + ")");
        for(int i = 0; i < job.getNumPages(); i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        job.setComplete();
        colourMultiplex.release();
        currHead--;
        lastTime = job.getNumPages();
        System.out.println("Job " + job.getJobID() + " done" + " time " + (time + lastTime));
        colourSwitch.unlock(empty);
    }
    public void acquireMonoSem(Job job) throws InterruptedException {
        turnstile.acquire();
        monoSwitch.lock(empty);
        turnstile.release();
        monoMultiplex.acquire();
        currHead++;
        System.out.println("(" + (time + lastTime) + ") " + job.getJobID() + " uses head " + currHead + " (time: " + job.getNumPages() + ")");
        for(int i = 0; i < job.getNumPages(); i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        job.setComplete();
        monoMultiplex.release();
        currHead--;
        lastTime = job.getNumPages();
        System.out.println("Job " + job.getJobID() + " done" + " time " + (time + lastTime));
        monoSwitch.unlock(empty);
    }

    public void incCurrHead() {
        currHead++;
    }
    public void decCurrHead() {
        currHead--;
    }
    public int getCurrHead() {
        return currHead;
    }


//    public void printJob(Job job) {
//            try {
//                noOfHeadsSem.acquire();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            currHead.incrementAndGet();
//            setHighest(job.getNumPages());
//            System.out.println("(" + time + ") " + job.getJobID() + " uses head " + currHead + " (time: " + job.getNumPages() + ")");
//            for(int i = 0; i < job.getNumPages(); i++) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            noOfHeadsSem.release();
//            currHead.decrementAndGet();
//            time.set(highest + time.get());
//            highest = 0;
//            job.setComplete();
//            // System.out.println("Job " + job.getJobID() + " done");
//    }

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
