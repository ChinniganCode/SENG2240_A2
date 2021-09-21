package P3;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Printer {
    private int time;
    private Semaphore empty;
    private Semaphore colourMultiplex;
    private Semaphore monoMultiplex;
    private Semaphore turnstile;
    private Semaphore monoMutex = new Semaphore(1);
    private Semaphore colourMutex = new Semaphore(1);
    private int numJobs;
    private int jobsCompleted;
    private int currHead;
    private int colourSwitchCounter;
    private int monoSwitchCounter;
    private ArrayList<Job> jobList = new ArrayList<Job>();

    public Printer() {
        empty = new Semaphore(1);
        colourMultiplex = new Semaphore(3, true);
        monoMultiplex = new Semaphore(3, true);
        turnstile = new Semaphore(1);
        numJobs = 0;
        jobsCompleted = 0;
        currHead = 0;
        time = 0;
        colourSwitchCounter = 0;
        monoSwitchCounter = 0;
    }

    public void acquireTurnstile() {
        try {
            turnstile.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void lockColour() {
        try {
            colourMutex.acquire();
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
        colourMutex.release();
    }

    public void unlockColour() {
        try {
            colourMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        --colourSwitchCounter;
        if (colourSwitchCounter == 0) empty.release();
        colourMutex.release();
    }

    public void lockMono() {
        try {
            monoMutex.acquire();
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
        monoMutex.release();
    }

    public void unlockMono() {
        try {
            monoMutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        --monoSwitchCounter;
        if (monoSwitchCounter == 0) empty.release();
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
    public void acquireSem(char type) {
        if (type == 'M') {
            try {
                monoMultiplex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (type == 'C') {
                try {
                    colourMultiplex.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void lock(char type) {
        if (type == 'M') {
            try {
                monoMutex.acquire();
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
            monoMutex.release();
        } else {
            try {
                colourMutex.acquire();
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
            colourMutex.release();
        }
    }
    public void unlock(char type) {
        if (type == 'M') {
            try {
                monoMutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            --monoSwitchCounter;
            if (monoSwitchCounter == 0) empty.release();
            monoMutex.release();
        } else {
                try {
                    colourMutex.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                --colourSwitchCounter;
                if (colourSwitchCounter == 0) empty.release();
                colourMutex.release();
            }
        }

    public void releaseSem(char type) {
        if(type == 'M') {
            monoMultiplex.release();
        } else {
            colourMultiplex.release();
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
