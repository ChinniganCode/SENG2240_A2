package P1;

import java.util.concurrent.Semaphore;

public class Intersection {
    private int track1Crossed; //to get to 150
    private int track2Crossed; //to get to 150
    private Semaphore sem;

    public Intersection() {
        track1Crossed = 0;
        track2Crossed = 0;
        sem = new Semaphore(1,true);
    }

    public void acquireSem() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void incrementCount(WAR input) {
        if(input.getLocation().contains("1")) {
            track1Crossed++;
        } else if(input.getLocation().contains("2")) {
            track2Crossed++;
        }
    }
    public void releaseSem()  {
        sem.release();
    }
    public void printCount() {
        System.out.println("Total crossed in Track1 " + track1Crossed + " Track2 " + track2Crossed);
    }
    public int getTrack1Crossed() {
        return track1Crossed;
    }
    public int getTrack2Crossed() {
        return track2Crossed;
    }

}
