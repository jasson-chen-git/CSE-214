package hw4;

import java.util.Enumeration;
import java.util.Scanner;

public class IntersectionSimulator {
    private static Scanner in = new Scanner(System.in);
    private static Intersection intersection;
    public static void main(String[] args){
        int simulationTime;
        double arrivalProb;
        int numRoads;
        String[] roadNames;
        int[] maxGreenTimes;
        TwoWayRoad[] crossroads;

        System.out.println("Welcome to IntersectionSimulator 2021\n");
        if(args.length > 1){
            simulationTime = Integer.parseInt(args[0]);
            arrivalProb = Double.parseDouble(args[1]);
            numRoads = Integer.parseInt(args[2]);
            roadNames = new String[numRoads];
            maxGreenTimes = new int[numRoads];

            for (int i = 0; i < numRoads; ++i){
                roadNames[i] = args[3 + i];
                maxGreenTimes[i] = Integer.parseInt(args[3 + numRoads + i]);
            }
            // process args in simTime, prob, numRoads, names, times.
        }
        else{
            System.out.print("Input the simulation time: ");
            simulationTime = in.nextInt();
            in.nextLine();
            System.out.print("Input the arrival probability: ");
            arrivalProb = in.nextDouble();
            in.nextLine();
            System.out.print("Input number of Streets: ");
            numRoads = in.nextInt();
            in.nextLine();

            roadNames = new String[numRoads];
            maxGreenTimes = new int[numRoads];
            boolean isUniqueRoad;
            String streetName;

            for(int i = 0; i < numRoads; i++){
                do{
                    isUniqueRoad = true;
                    System.out.print("Input Street " + (i + 1) + " name: ");
                    streetName = in.nextLine();
                    for(int j = 0; j < i; j++){
                        if(streetName.compareToIgnoreCase(roadNames[j]) == 0){
                            isUniqueRoad = false;
                            System.out.println("Duplicate Detected.");
                            break;
                        }
                    }
                }while(!isUniqueRoad);
                roadNames[i] = streetName;
            }

            for(int i = 0; i < numRoads; i++){
                System.out.print("Input max green time for " + roadNames[i] + ": ");
                maxGreenTimes[i] = in.nextInt();
                in.nextLine();
            }
        }
        crossroads = new TwoWayRoad[numRoads];
        for(int i = 0; i < numRoads; i++){
            crossroads[i] = new TwoWayRoad(roadNames[i], maxGreenTimes[i]);
        }
        intersection = new Intersection(crossroads);
        simulate(simulationTime, arrivalProb, roadNames, maxGreenTimes);
    }
    public static void simulate(int simulationTime, double arrivalProb, String[] roadNames, int[] maxGreenTimes){
        BooleanSourceHW4 vGenerator = new BooleanSourceHW4(arrivalProb);
        int time = 1;
        Vehicle[] arrivingVehicles = new Vehicle[100];
        int vehicleCounter = 0;
        int totalWaitTime = 0;
        int longestWaitTime = 0;
        double averageWaitTime = 0;
        int totalVehiclesPassed = 0;
        int vehiclesWaiting = 1;

        System.out.println("\nStarting simulation...\n");
        while(time < simulationTime || !intersection.isEmpty()){
            System.out.println("################################################################################"
                    + "\n\nTime Step: " + time + "\n");
            System.out.println("\t" + intersection.getStatus());
            System.out.println("\tTimer = " + intersection.getCountdownTimer() + "\n");
            System.out.println("\tARRIVING CARS:");
            if(time <= simulationTime){
                for(int roadIndex = 0; roadIndex < intersection.getRoads().length; roadIndex++){
                    for(int wayIndex = 0; wayIndex < 2; wayIndex++){
                        for(int laneIndex = 0; laneIndex < 3; laneIndex++){
                            if(vGenerator.occursHW4()){
                                Vehicle tempVehicle = new Vehicle(time);
                                arrivingVehicles[vehicleCounter++] = tempVehicle;
                                //System.out.println(tempVehicle.toString());
                                intersection.enqueueVehicle(roadIndex, wayIndex, laneIndex, tempVehicle);
                                //System.out.println(intersection.toString());
                            }
                        }
                    }
                }
            }
            System.out.println("\n\tPASSING CARS:");
            if(!intersection.isEmpty()){
                Vehicle[] passingVehicles = intersection.timeStep();
                for(Vehicle temp : passingVehicles){
                    int waitTime = time - temp.getTimeArrived();
                    if(waitTime > longestWaitTime){
                        longestWaitTime = waitTime;
                    }
                    totalWaitTime += waitTime;
                    totalVehiclesPassed++;
                    System.out.println("\t\tCar " + temp.toString() + " passes through. Wait time of " + waitTime + ".");
                }
            }
            if(totalVehiclesPassed == 0){
                averageWaitTime = 0;
            }
            else{
                averageWaitTime = (double) totalWaitTime / totalVehiclesPassed;
            }
            System.out.println("\n\n" + intersection.toString());
            intersection.updateStatus();
            System.out.println("\tSTATISTICS:\n" +
                    "\t\tCars currently waiting:\t" + vehiclesWaiting + " cars\n" +
                    "\t\tTotal cars passed:\t\t" + totalVehiclesPassed + " cars\n"+
                    "\t\tTotal wait time:\t\t" + totalWaitTime + " turns\n" +
                    String.format("\t\tAverage wait time:\t\t%.2f turns\n", averageWaitTime));
            time++;
        }
        System.out.println("\n\n################################################################################\n" +
                "################################################################################\n" +
                "################################################################################\n\n" +
                "SIMULATION SUMMARY:\n\tTotal Time:\t\t\t" + time + " steps\n" +
                "\tTotal Vehicles:\t\t" + totalVehiclesPassed + " vehicles\n" +
                "\tLongest Wait Time:\t" + longestWaitTime + " turns\n" +
                "\tTotal Wait Time:\t" + totalWaitTime + " turns\n" +
                String.format("\tAverage Wait Time:\t%.2f turns\n", averageWaitTime) +
                "\n End simulation.\n");
    }
//    public static boolean isIntersectionEmpty(){
//        boolean allRoadsEmpty = true;
//        int i = 0;
//        do{
//            allRoadsEmpty = intersection.getRoads()[i].isRoadEmpty();
//        }while(i < intersection.getRoads().length && allRoadsEmpty);  //  will break when it finds non-empty road
//        return allRoadsEmpty;                                         //  and return false if so
//    }
}