package P3;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class P3 {
    public static Printer printer = new Printer();
    public static ArrayList<Job> jobList = new ArrayList<Job>();
    public static void main(String[] args) {
        String input;
        int numJobs;
        String jobID;
        int pageCount;
        try {
            File myObj = new File(args[0]); //file input
            Scanner myReader = new Scanner(myObj);
            numJobs = myReader.nextInt(); //Read number of jobs
            printer.setNumJobs(numJobs);
            while(myReader.hasNext()) {
                jobID = myReader.next();
                pageCount = Integer.parseInt(myReader.next());
                Job newJob = new Job(jobID, pageCount,printer);
                newJob.start();
                }
            Thread.sleep(10);
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


}
 