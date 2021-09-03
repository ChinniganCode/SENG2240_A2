package P2;

import java.io.File;
import java.util.Scanner;

public class P2 {
    public static Printer printer = new Printer();
    public static void main(String[] args) {
        String input;
        int numJobs;
        String jobID;
        int pageCount;
        try {
            File myObj = new File(args[0]); //file input
            Scanner myReader = new Scanner(myObj);
            numJobs = myReader.nextInt(); //Read number of jobs
            System.out.println(numJobs);
            while(myReader.hasNext()) {
                jobID = myReader.next();
                pageCount = Integer.parseInt(myReader.next());
               //     Job newJob = new Job(jobID, pageCount);
              //     newJob.start();
                System.out.println(jobID + " " + pageCount);
                }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
