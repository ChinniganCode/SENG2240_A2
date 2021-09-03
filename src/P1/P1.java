package P1;

import java.io.File;
import java.util.Scanner;

public class P1 {
    public static Intersection intersection = new Intersection();
    public static void main(String[] args) {
        String input;
        int numWars;
        int warCount = 1;
        try {
            File myObj = new File(args[0]); //file input
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNext()) {
                input = myReader.next();
                numWars = Integer.parseInt(input.substring(input.lastIndexOf("=")+1));
                for (int i = 0; i < numWars; i++) {
                    WAR newWar = new WAR("WAR-" + warCount,input.charAt(0), intersection);
                    warCount++;
                    newWar.start();
                }
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
