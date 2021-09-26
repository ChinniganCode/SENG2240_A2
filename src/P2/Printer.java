package P2;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Printer {
    private int time;
    private int numJobs;
    private int jobsCompleted;
    private int currHead;
    private char jobType;
    private ArrayList<Job> jobList = new ArrayList<>();
    private ArrayList<Job> current = new ArrayList<>();
    private boolean colourLock;
    private boolean monoLock;
    private ReentrantLock turnstile;

    public Printer() {
        numJobs = 0;
        jobsCompleted = 0;
        currHead = 0;
        time = 0;
        jobType = 'Z';
        colourLock = false;
        monoLock = false;
        turnstile = new ReentrantLock();
    }
    public void lockTurnstile() {
        turnstile.lock();
    }
    public void unlockTurnstile() {
        turnstile.unlock();
    }

    public void colourLock() {
        colourLock = true;
        monoLock = false;
    }
    public void monoLock() {
        colourLock = false;
        monoLock = true;
    }
    public void lock(char type) {
        if (type == 'M') {
            colourLock = false;
            monoLock = true;
        } else {
            colourLock = true;
            monoLock = false;
        }
    }
    public boolean isColourLock() {
        return colourLock;
    }


    public boolean isMonoLock() {
        return monoLock;
    }


    public boolean checkLine(Job job) {
        if(jobList.get(0).getJobID() == job.getJobID()) {
            return true;
        } else {
            return false;
        }
    }
    public synchronized void removeJob(Job job) {
        jobList.remove(job);
        }

    public  void removeHead() {
        jobList.remove(0);
    }
    public void incCurrHead() {
        currHead++;
    }

    public void decCurrHead() {
        currHead--;
    }

    public char getJobType() {
        return jobType;
    }

    public int getCurrHead() {
        return currHead;
    }
    public boolean isFull() {
        if(this.current.size() == 3) {
            return true;
        } else
            return false;
    }
    public boolean isEmpty() {
        return this.current.isEmpty();
    }
    public boolean isUsing(Job job) {
        if(current.contains(job)) {
            return true;
        } else {
            return false;
        }
    }
    public int getTime() {
        return time;
    }

    public synchronized void setTime(int data) {
        time = data;
    }

    public int getNumJobs() { return numJobs; }

    public void setNumJobs(int numJobs) {
        this.numJobs = numJobs;
    }

    public synchronized void incJobsCompleted() {
        jobsCompleted++;
    }

    public int getJobsCompleted() {
        return jobsCompleted;
    }
    public void setJobList(ArrayList<Job> jobs) {
        jobList = jobs;
    }
}
