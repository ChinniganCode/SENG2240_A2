package P2;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class Printer {
    private int time;
    private int numJobs;
    private int jobsCompleted;
    private ArrayList<Job> jobList = new ArrayList<>();
    private boolean colourLock;
    private boolean monoLock;
    public boolean empty;
    private ArrayList<Integer> headArray = new ArrayList<>(List.of(1,2,3));

    public Printer() {
        numJobs = 0;
        jobsCompleted = 0;
        time = 0;
        colourLock = false;
        monoLock = false;
        empty = true;
    }
    public ArrayList<Integer> getHeadArray() {
        return headArray;
    }
    public String getJobList() {
        String o = "";
        for(int i =0; i < jobList.size(); i++) {
            o+=jobList.get(i).getJobID() + " ";
        }
        return o;
    }
    public int takeHead() {
        return headArray.remove(0);
    }
    public void releaseHead(int headNo) {
        headArray.add(headNo);
    }

    public boolean isAllowed(char type) {
        if (type == 'M') {
            if(isMonoLock()) {
                return true;
            } else {
                return false;
            }
        } else {
            if(isColourLock()) {
                return true;
            } else {
                return false;
            }
        }
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

    public boolean headAvailable() {
        if(headArray.isEmpty()) {
            return false;
        } else return true;
    }
    public boolean isEmpty() {
        if(headArray.size() == 3) {
            return true;
        } else return false;
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
