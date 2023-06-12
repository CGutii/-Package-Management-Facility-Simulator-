/*
Name: Cristian Gutierrez
Course: CNT 4714 Summer 2023
Assignment title: Project 1 – Multi-threaded programming in Java
Date: June 11, 2023
Class: C001
*/

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.io.*;

public class RoutingStation implements Runnable {
    private int stationID;
    private int workload;
    private ReentrantLock inputConveyor;
    private ReentrantLock outputConveyor;
    private Random random;

    // Constructor for the routing station
    public RoutingStation(int stationID, int workload, ReentrantLock inputConveyor, ReentrantLock outputConveyor) {
        this.stationID = stationID;
        this.workload = workload;
        this.inputConveyor = inputConveyor;
        this.outputConveyor = outputConveyor;
        this.random = new Random();
    }

    // Main operation of the routing station
    @Override
    public void run() {
        while (workload > 0) {
            try { // Try to lock input conveyor
                if (inputConveyor.tryLock()) {
                    log("Routing Station S" + stationID + ": Currently holds lock on input conveyor C" + stationID
                            + ".");
                    // Try to lock output conveyor
                    if (outputConveyor.tryLock()) {
                        log("Routing Station S" + stationID + ": Currently holds lock on output conveyor C"
                                + ((stationID + 1) % 10) + ".");
                        log("\n* * Routing Station S" + stationID
                                + ": * * CURRENTLY HARD AT WORK MOVING PACKAGES. * *");

                        Thread.sleep(random.nextInt(5000)); // simulate random processing time

                        // Log the progress after work done
                        log("Routing Station S" + stationID + ": Package group completed - " + --workload
                                + " package groups remaining to move");
                        // Unlock the output conveyor after work done
                        outputConveyor.unlock();
                        log("Routing Station S" + stationID + ": Unlocks/releases output conveyor C"
                                + ((stationID + 1) % 10) + ".");
                    } else {
                        // Log message if unable to lock output conveyor
                        log("Routing Station S" + stationID + ": UNABLE TO LOCK OUTPUT CONVEYOR C"
                                + ((stationID + 1) % 10) + ".          \nSYNCHRONIZATION ISSUE: Station S"
                                + ((stationID + 1) % 10) + " currently holds the lock on output conveyor C"
                                + ((stationID + 1) % 10) + " – Station S" + stationID
                                + " releasing lock on input conveyor C" + stationID + ".");
                    }

                    // Unlock the input conveyor after each iteration
                    inputConveyor.unlock();
                    log("Routing Station S" + stationID + ": Unlocks/releases input conveyor C" + stationID + ".");
                    Thread.sleep(random.nextInt(5000)); // simulate random sleep time
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Log the message when station goes offline
        log("\n# # Station S" + stationID + ": Workload successfully completed. - - - Station S" + stationID
                + " preparing to go offline. # #");
    }

    // Utility method for logging messages to console and file
    private void log(String message) {
        System.out.println(message);

        try (FileWriter fw = new FileWriter("output.txt", true);
                PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
