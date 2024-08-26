package hw4;

import java.util.Stack;

public class TwoWayRoad {
    public final int FORWARD_WAY = 0;
    public final int BACKWARD_WAY = 1;
    public final int NUM_WAYS = 2;
    public final int LEFT_LANE = 0;
    public final int MIDDLE_LANE = 1;
    public final int RIGHT_LANE = 2;
    public final int NUM_LANES = 3;
    private final String name;
    private final int greenTime;
    private final int leftSignalGreenTime;
    private VehicleQueue[][] lanes = new VehicleQueue[NUM_WAYS][NUM_LANES];
    private LightValue lightValue;
    private boolean preemptLightChange;
    public TwoWayRoad(String initName, int initGreenTime){
        if(initGreenTime <= 0 || initName == null){
            throw new IllegalArgumentException();
        }
        name = initName;
        greenTime = initGreenTime;
        leftSignalGreenTime = (initGreenTime / NUM_LANES);
        lightValue = LightValue.RED;
        preemptLightChange = false;
        for(int i = 0; i < NUM_WAYS; i++){
            for(int j = 0; j < NUM_LANES; j++){
                lanes[i][j] = new VehicleQueue();
            }
        }
        //leftSignalGreenTime = (int) Math.floor(1 / NUM_LANES * initGreenTime);
    }
    public String getWay(int wayIndex){
        if (wayIndex == 0) {
            return "FORWARD";
        }
        return "BACKWARD";
    }
    public String getLane(int laneIndex){
        if(laneIndex == 0){
            return "LEFT LANE";
        }
        else if(laneIndex == 1){
            return "MIDDLE LANE";
        }
        return "RIGHT LANE";
    }
    public String getName(){
        return name;
    }
    public int getGreenTime(){
        return greenTime;
    }
    public LightValue getLightValue(){
        return lightValue;
    }
    public void toggleLightValue(){
        if(lightValue == LightValue.RED){
            lightValue = LightValue.GREEN;
        }
        else if(lightValue == LightValue.GREEN){
            lightValue = LightValue.LEFT_SIGNAL;
        }
        else{
            lightValue = LightValue.RED;
        }
    }
    public boolean getPreemptLightChange(){
        return preemptLightChange;
    }
    public Vehicle[] proceed(int timerVal){
        preemptLightChange = false;
        if(timerVal <= 0){
            throw new IllegalArgumentException();
        }
        Vehicle[] dequeued = new Vehicle[4];
        int i = 0;
        if(lightValue == LightValue.GREEN){
            if(!lanes[FORWARD_WAY][RIGHT_LANE].isEmpty()){
                dequeued[i++] = lanes[FORWARD_WAY][RIGHT_LANE].dequeue();
            }
            if(!lanes[FORWARD_WAY][MIDDLE_LANE].isEmpty()){
                dequeued[i++] = lanes[FORWARD_WAY][MIDDLE_LANE].dequeue();
            }
            if(!lanes[BACKWARD_WAY][RIGHT_LANE].isEmpty()){
                dequeued[i++] = lanes[BACKWARD_WAY][RIGHT_LANE].dequeue();
            }
            if(!lanes[BACKWARD_WAY][MIDDLE_LANE].isEmpty()){
                dequeued[i++] = lanes[BACKWARD_WAY][MIDDLE_LANE].dequeue();
            }
            if(lanes[FORWARD_WAY][RIGHT_LANE].isEmpty() && lanes[FORWARD_WAY][MIDDLE_LANE].isEmpty() &&
                    lanes[BACKWARD_WAY][RIGHT_LANE].isEmpty() && lanes[BACKWARD_WAY][MIDDLE_LANE].isEmpty()){
                preemptLightChange = true;
            }
        }
        else if(lightValue == LightValue.LEFT_SIGNAL){
            if(!lanes[FORWARD_WAY][LEFT_LANE].isEmpty()){
                dequeued[i++] = lanes[FORWARD_WAY][LEFT_LANE].dequeue();
            }
            if(!lanes[BACKWARD_WAY][LEFT_LANE].isEmpty()){
                dequeued[i++] = lanes[BACKWARD_WAY][LEFT_LANE].dequeue();
            }
            if(lanes[FORWARD_WAY][LEFT_LANE].isEmpty() && lanes[BACKWARD_WAY][LEFT_LANE].isEmpty()){
                preemptLightChange = true;
            }
        }
        if(lightValue == LightValue.GREEN && timerVal - 1 <= leftSignalGreenTime){
            preemptLightChange = true;
        }
        else if(lightValue == LightValue.LEFT_SIGNAL && timerVal == 1){
            preemptLightChange = true;
        }
        Vehicle[] passed = new Vehicle[i];
        for(int j = 0; j < i; j++){
            passed[j] = dequeued[j];
        }
        return passed;
    }
    public boolean isLaneEmpty(int wayIndex, int laneIndex){
        if(wayIndex < 0 || wayIndex > NUM_WAYS - 1 || laneIndex < 0 || wayIndex > NUM_LANES - 1){
            throw new IllegalArgumentException("thrown from TwoWayRoad.isLaneEmpty()");
        }
        return lanes[wayIndex][laneIndex].isEmpty();
    }
    public boolean isRoadEmpty(){
        return isLaneEmpty(FORWARD_WAY, RIGHT_LANE) && isLaneEmpty(BACKWARD_WAY, RIGHT_LANE) &&
                isLaneEmpty(FORWARD_WAY, LEFT_LANE) && isLaneEmpty(BACKWARD_WAY, LEFT_LANE) &&
                isLaneEmpty(FORWARD_WAY, MIDDLE_LANE) && isLaneEmpty(BACKWARD_WAY, MIDDLE_LANE);
    }
    public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle){
        if(wayIndex < 0 || wayIndex > NUM_WAYS - 1 || laneIndex < 0 || wayIndex > NUM_LANES - 1 || vehicle == null){
            throw new IllegalArgumentException("thrown from TwoWayRoad.enqueueVehicle()");
        }
        lanes[wayIndex][laneIndex].enqueue(vehicle);
    }
    public String toString(){
        String printout = "";
        printout += String.format("\t" + name + ":\n" +
                "\t                       FORWARD               BACKWARD\n" +
                "\t==============================               ===============================\n" +
                "\t" + "%30s [L] %5s [R] %-30s\n" +
                "\t------------------------------               -------------------------------\n" +
                "\t" + "%30s [M] %5s [M] %-30s\n" +
                "\t------------------------------               -------------------------------\n" +
                "\t" + "%30s [R] %5s [L] %-30s\n" +
                "\t==============================               ===============================\n",
                laneString(FORWARD_WAY, LEFT_LANE), laneMarkers(1), laneString(BACKWARD_WAY, RIGHT_LANE),
                laneString(FORWARD_WAY, MIDDLE_LANE), laneMarkers( 2), laneString(BACKWARD_WAY, MIDDLE_LANE),
                laneString(FORWARD_WAY, RIGHT_LANE), laneMarkers( 3), laneString(BACKWARD_WAY, LEFT_LANE));
        return printout;
    }
    private String laneString(int way,int lane){
        if(way == FORWARD_WAY){
            return lanes[way][lane].toStringBackward();
        }
        return lanes[way][lane].toString();
    }
    private String laneMarkers(int row){
        switch (lightValue.getValue() * 10 + row){
            case 21, 11:
                return "x    ";
            case 22:
                return "     ";
            case 23, 13:
                return "    x";
            default:
                return "x   x";
        }
    }
}