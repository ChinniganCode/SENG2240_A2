package P2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class P2 {
    public static Printer printer = new Printer();

    public static void main(String[] args) {
        int numJobs;
        String jobID;
        int pageCount;
        ArrayList<Job> jobList = new ArrayList<>();
        try {
            File myObj = new File(args[0]); //file input
            Scanner myReader = new Scanner(myObj);
            numJobs = myReader.nextInt(); //Read number of jobs
            printer.setNumJobs(numJobs);
            while (myReader.hasNext()) {
                jobID = myReader.next();
                pageCount = Integer.parseInt(myReader.next());
                Job newJob = new Job(jobID, pageCount, printer);
                jobList.add(newJob);
                Thread.sleep(100); //inserts delay to enforce serialization
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        printer.setJobList(jobList);
        for(int i = 0; i < jobList.size(); i++) {
            jobList.get(i).start();
        }
    }
}



 