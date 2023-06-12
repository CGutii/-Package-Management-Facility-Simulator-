# [**_Package Management Facility Simulator Documentation_**]()

## **[Overview]()**

This project simulates a package management facility where there are several routing stations. Each routing station has a certain amount of workload to process. The routing stations have two conveyors, input and output. A station must first secure a lock on both the input and output conveyor before processing a unit of workload. If a station cannot secure both locks, it releases the locks and waits for a random amount of time before trying again.

## **[Step by Step Thought Process]()**

**Define the project problem**: The first step in solving this problem was understanding the requirements and constraints. It was clear that each routing station must lock both its input and output conveyors before it can process a unit of workload. If it can't lock both, it must wait and try again.

**Identify the necessary components**: The project required a RoutingStation class and a main method to create and start each routing station. The RoutingStation class required fields for the station's ID, its workload, and the locks for its input and output conveyors.

**Implement the RoutingStation class**: The RoutingStation class implements the Runnable interface, which means it can be run in a separate thread. The run method describes the actions a routing station takes when it is running. The log method allows for writing output to both the console and a text file.

**Implement the main method**: The main method reads the configuration from a file, creates the RoutingStation objects, and starts them in separate threads.

## **[Understanding the Source Files]()**

**main.java**: This file contains the main method for running the simulation. The main method reads from a configuration file, 'config.txt', to determine the number of routing stations and their respective workloads. It then creates the routing stations, starts their threads, and waits for them to finish. At the end of the simulation, it outputs a termination message.

**RoutingStation.java**: This file defines a routing station. Each station has an ID, a workload, and locks for its input and output conveyors. It also includes a 'log' method for outputting messages to the console and to a file. The run method describes how a station operates, trying to lock both conveyors, processing a unit of workload, and then unlocking the conveyors.

## **[How to Run the Program]()**

Before running the program, make sure you have the configuration file 'config.txt' in the same directory as your source code. This file should contain the number of stations on the first line and then the workload for each station on the following lines.

To compile the program, use the following command in the terminal:

**_javac main.java RoutingStation.java_**

To run the program, use the following command in the terminal:

**_java main_**

The output will be displayed in the console and also written to 'output.txt'.
