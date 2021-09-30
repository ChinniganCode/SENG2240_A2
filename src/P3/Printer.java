package P3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Printer {
    private int time;
    private Semaphore empty;
    private Semaphore colourSem;
    private Semaphore monoSem;
    private Semaphore entryLock;
    private Semaphore monoLock = new Semaphore(1);
    private Semaphore colourLock = new Semaphore(1);
    private int numJobs;
    private int jobsCompleted;
    private int colourSwitchCounter;
    private int monoSwitchCounter;
    private ArrayList<Integer> headArray = new ArrayList<>(List.of(1,2,3));

    public Printer() {
        empty = new Semaphore(1);
        colourSem = new Semaphore(3, true);
        monoSem = new Semaphore(3, true);
        entryLock = new Semaphore(1);
        numJobs = 0;
        jobsCompleted = 0;
        time = 0;
        colourSwitchCounter = 0;
        monoSwitchCounter = 0;
    }

    public void acquireEntryLock() {
        try {
            entryLock.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int takeHead() {
        return headArray.remove(0);
    }
    public void releaseHead(int headNo) {
        headArray.add(headNo);
    }

    public void releaseEntryLock() {
        entryLock.release();
    }

    public void acquireSem(char type) {
        if (type == 'M') {
            try {
                monoSem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (type == 'C') {
                try {
                    colourSem.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void lock(char type) {
        if (type == 'M') {
            try {
                monoLock.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++monoSwitchCounter;
            if (monoSwitchCounter == 1) {
                try {
                    empty.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            monoLock.release();
        } else {
            try {
                colourLock.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++colourSwitchCounter;
            if (colourSwitchCounter == 1) {
                try {
                    empty.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            colourLock.release();
        }
    }
    public void unlock(char type) {
        if (type == 'M') {
            try {
                monoLock.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            --monoSwitchCounter;
            if (monoSwitchCounter == 0) empty.release();
            monoLock.release();
        } else {
            try {
                colourLock.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            --colourSwitchCounter;
            if (colourSwitchCounter == 0) empty.release();
            colourLock.release();
        }
    }

    public void releaseSem(char type) {
        if(type == 'M') {
            monoSem.release();
        } else {
            colourSem.release();
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
}
