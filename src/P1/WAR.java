package P1;

/*TODO:
- Read each WAR from the text file
- Have each WAR run on a thread using implement Runnable or extends Thread
- try to run indefinetely
1. Announce e.g. "WAR-X (LOADED) Wating at the Intersection. Going towards destination
2. attempt to get access to semaphore
3. Once access aquired, announce Checkpoint 1, Sleep(200), Checkpoint 2, Sleep(200), Checkpoint 3, Sleep(200)
4. Announce crossed e.g. "WAR-X (LOADED) cross the intersection"
5. Change loaded, destination and location
6. Print updated count (not in this class)
*/

public class WAR extends Thread {
    private String id; // i.e. WAR-X
    private String destination; // e.g. Dock1, Dock2, Storage1, Storage2
    private String location; // e.g. Dock1, Dock2, Storage1, Storage2
    private String loaded; //whether the WAR is loaded or not, can be determined by location + destination

    public WAR(String id, char startDirection) {
        this.id = id;
        setInitLocation(startDirection);
        updateWAR();
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("WAR-" + id +" (" + loaded + ") "+"Waiting at the intersection. Going towards " + destination);
            //Semaphore
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
                location = "Dock1";
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
}
