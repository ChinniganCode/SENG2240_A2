package P1;

public class WAR extends Thread {
    private String id; // i.e. WAR-X
    private String destination; // e.g. Dock1, Dock2, Storage1, Storage2
    private String location; // e.g. Dock1, Dock2, Storage1, Storage2
    private String loaded; //whether the WAR is loaded or not, can be determined by location + destination
    private Intersection intersection;

    public WAR(String id, char startDirection, Intersection intersection) {
        this.id = id;
        this.intersection = intersection;
        setInitLocation(startDirection);
        updateWAR();
    }

    @Override
    public void run() {
        while (!(intersection.getTrack1Crossed() >= 150 && intersection.getTrack2Crossed() >= 150))  {
            System.out.println(id + " (" + loaded + ") " + "Waiting at the intersection. Going towards " + destination);
            intersection.acquireSem();
            if((intersection.getTrack1Crossed() >= 150 && intersection.getTrack2Crossed() >= 150)) {
                intersection.releaseSem();
                break;
            }
            System.out.println(id + " (" + loaded + ") " + "Crossing intersection Checkpoint 1.");
            try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(id + " (" + loaded + ") " + "Crossing intersection Checkpoint 2.");
            try { Thread.sleep(200); } catch (InterruptedException e) {e.printStackTrace(); }
            System.out.println(id + " (" + loaded + ") " + "Crossing intersection Checkpoint 3.");
            try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(id + " (" + loaded + ") " + "Crossed The Intersection");
            intersection.incrementCount(this);
            turnAround();
            intersection.printCount();
            intersection.releaseSem();
        }
    }


    public void updateWAR() {
        switch(location) {
            case "Storage1":
                destination = "Dock1";
                loaded = "LOADED";
                break;
            case "Storage2":
                destination = "Dock2";
                loaded = "LOADED";
                break;
            case "Dock1":
                destination = "Storage1";
                loaded = "UNLOADED";
                break;
            case "Dock2":
                destination = "Storage2";
                loaded = "UNLOADED";
                break;
        }
    }
    public void setInitLocation(char direction) {
        switch(direction) {
            case 'N':
                location = "Storage2";
                break;
            case 'E':
                location = "Dock1";
                break;
            case 'S':
                location = "Dock2";
                break;
            case 'W':
                location = "Storage1";
                break;
        }
    }
    public void turnAround() {
        switch (location) {
            case "Storage1":
                destination = "Storage1";
                location = "Dock1";
                loaded = "UNLOADED";
                break;
            case "Storage2":
                destination = "Storage2";
                location = "Dock2";
                loaded = "UNLOADED";
                break;
            case "Dock1":
                destination = "Dock1";
                location = "Storage1";
                loaded = "LOADED";
                break;
            case "Dock2":
                destination = "Dock2";
                location = "Storage2";
                loaded = "LOADED";
                break;
        }
    }

    public String getLocation() {
        return location;
    }
}
