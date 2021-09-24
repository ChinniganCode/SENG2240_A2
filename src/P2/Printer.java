package P2;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Printer {
    private int time;
    private int numJobs;
    private int jobsCompleted;
    private int currHead;
    private char jobType;
    private ArrayList<Job> jobList = new ArrayList<>();
    private ArrayList<Job> current = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    public Printer() {
        numJobs = 0;
        jobsCompleted = 0;
        currHead = 0;
        time = 0;
        jobType = 'Z';
    }
    public boolean checkLine(Job job) {
        if(jobList.get(0).getJobID().equals(job.getJobID())) {
            return true;
        } else {
            return false;
        }
    }

    public  Job getListHead() {
        return jobList.get(0);
    }


    public void addJob(Job job) {
        this.lock.lock();
        try {
            if(this.current.isEmpty()) {
                jobType = job.getJobID().charAt(0);
            }
            if(this.current.size() < 3
                    && !this.current.contains(job)
                    && this.jobType == job.getJobID().charAt(0)) {
                current.add(job);
                System.out.println("(" + time + ") " + job.getJobID() + " uses head " + current.size() + " (time: " + job.getNumPages() + ")");
                job.runJob();
            }
        } finally {
            this.lock.unlock();
        }
    }
    public void removeJob(Job job) {
        this.lock.lock();
        try {
            if(!this.current.isEmpty()) {
                jobType = job.getJobID().charAt(0);
                this.current.remove(job);
            }
            if(this.current.isEmpty()) {
                this.jobType = 'Z';
            }
        } finally {
            this.lock.unlock();
        }
    }


    public  void setJobType(char type) {
        jobType = type;
    }
    public  void removeHead() {
        jobList.remove(0);
    }
    public  void incCurrHead() {
        currHead++;
    }

    public  void decCurrHead() {
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

    public void setTime(int data) {
        time = data;
    }

    public int getNumJobs() { return numJobs; }

    public void setNumJobs(int numJobs) {
        this.numJobs = numJobs;
    }

    public void incJobsCompleted() {
        jobsCompleted++;
    }

    public int getJobsCompleted() {
        return jobsCompleted;
    }
    public void setJobList(ArrayList<Job> jobs) {
        jobList = jobs;
    }
}
