/*
Name: Cristian Gutierrez
Course: CNT 4714 Summer 2023
Assignment title: Project 1 – Multi-threaded programming in Java
Date: June 11, 2023
Class: C001
*/

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class main {
    // Main entry point for the simulation
    public static void main(String[] args) throws IOException, InterruptedException {
        int temp[] = new int[10];

        // Read configuration from file
        File file = new File("config.txt");
        Scanner scanner = new Scanner(file);

        // Print and log simulation start message
        log("Summer 2023- Project 1 - Package Management Facility Simulator");
        log("\n********* PACKAGE MANAGEMENT FACILITY SIMULATION BEGINS **********\n");
        log("\nThe parameters or this simulation run are:");

        // Initialize stations and locks
        int numberOfStations = scanner.nextInt();
        ReentrantLock[] locks = new ReentrantLock[numberOfStations];
        for (int i = 0; i < numberOfStations; i++) {
            locks[i] = new ReentrantLock();
        }

        // Create routing stations
        RoutingStation[] stations = new RoutingStation[numberOfStations];
        for (int i = 0; i < numberOfStations; i++) {
            int workload = scanner.nextInt();
            temp[i] = workload;
            stations[i] = new RoutingStation(i, workload, locks[i], locks[(i + 1) % numberOfStations]);
        }

        // Print and log station workloads
        for (int i = 0; i < numberOfStations; i++) {
            log("Routing Station S" + i + " has total workload of " + temp[i] + " package groups.");
        }
        log("");

        scanner.close();

        // Use an ExecutorService to manage the threads
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfStations);
        for (int i = 0; i < numberOfStations; i++) {
            executorService.execute(stations[i]);
        }

        executorService.shutdown();
        if (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
            // timeout handling if necessary
        }

        // Print and log simulation end message
        log("\n**ALL WORKLOADS COMPLETE PACKAGE MANAGEMENT FACILITY SIMULATION TERMINATES***********");
        log("\n****** SIMULATION ENDS ******");
    }

    // Utility method for logging messages to console and file
    public static void log(String message) {
        System.out.println(message);

        try (FileWriter fw = new FileWriter("output.txt", true);
                PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
