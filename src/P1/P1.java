package P1;

import java.io.File;
import java.util.Scanner;

public class P1 {
    public static void main(String[] args) {
        String input;
        int numWars = 0;
        int warCount = 1;
        String direction;
        try {
            File myObj = new File(args[0]); //file input
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNext()) {

                input = myReader.next();
                numWars = Integer.parseInt(input.substring(input.lastIndexOf("=" )+1));
                for (int i = 0; i < numWars; i++) {
                    WAR newWar = new WAR("WAR-" + warCount,input.charAt(0));
                    System.out.println("WAR-" + warCount + " Direction: " +input.charAt(0));
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
