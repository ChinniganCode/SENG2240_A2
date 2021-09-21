package P2;

import java.util.concurrent.Semaphore;

public class Printer {
    private int time;
    private Semaphore empty;
    private Semaphore colourMultiplex;
    private Semaphore monoMultiplex;
    private Semaphore turnstile;
    private Semaphore monoMutex;
    private Semaphore colourMutex;
    private int numJobs;
    private int jobsCompleted;
    private int currHead;
    private int colourSwitchCounter = 0;
    private int monoSwitchCounter = 0;
    public Printer() {
        empty = new Semaphore(1);
        colourMultiplex = new Semaphore (3, true);
        monoMultiplex = new Semaphore(3, true);
        turnstile = new Semaphore(1);
        monoMutex = new Semaphore(1);
        colourMutex = new Semaphore(1);
        numJobs = 0;
        jobsCompleted = 0;
        currHead = 0;
        time = 0;
    }

    public void acquireTurnstile() {
        try {
            turnstile.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lockColour(){
        try {
            colourMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ++colourSwitchCounter;
        if(colourSwitchCounter == 1) {
            try {
                empty.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        colourMutex.release();
    }
    public void unlockColour() {
        try {
            colourMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        --colourSwitchCounter;
        if(colourSwitchCounter == 0) empty.release();
        colourMutex.release();
    }
    public void lockMono(){
        try {
            monoMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ++monoSwitchCounter;
        if(monoSwitchCounter == 1) {
            try {
                empty.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        monoMutex.release();
    }

    public void unlockMono() {
        try {
            monoMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        --monoSwitchCounter;
        if(monoSwitchCounter == 0) empty.release();
        monoMutex.release();
    }
    public void releaseTurnstile() {
        turnstile.release();
    }
    public void acquireColour() {
        try {
            colourMultiplex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void releaseColour() {
        colourMultiplex.release();
    }
    public void acquireMono() {
        try {
            monoMultiplex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void releaseMono() {
        monoMultiplex.release();
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
    public int getTime() {
        return time;
    }
    public void setTime(int data) {
        time = data;
    }
    public void setNumJobs(int numJobs) {
        this.numJobs = numJobs;
    }
    public int getNumJobs() { return numJobs; }
    public void incJobsCompleted() {
        jobsCompleted++;
    }
    public int getJobsCompleted() {
        return jobsCompleted;
    }

}