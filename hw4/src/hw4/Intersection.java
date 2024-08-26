package hw4;

public class Intersection {
    private TwoWayRoad[] roads;
    private int lightIndex;
    private int countdownTimer;
    private final int MAX_ROADS = 4;
    public Intersection(TwoWayRoad[] initRoads){
        if(initRoads == null || initRoads.length > MAX_ROADS){
            throw new IllegalArgumentException("From Intersection constructor.");
        }
        for(int i = 0; i < initRoads.length; i++){
            if(initRoads[i] == null){
                throw new IllegalArgumentException("From Intersection constructor initRoad entry is null");
            }
        }
        roads = initRoads;
        lightIndex = 0;
        roads[lightIndex].toggleLightValue();
        countdownTimer = roads[lightIndex].getGreenTime();
    }
    public TwoWayRoad[] getRoads() {
        return roads;
    }
    public int getCountdownTimer(){
        return countdownTimer;
    }
    public Vehicle[] timeStep(){
//        if(roads[lightIndex].getLightValue() == LightValue.RED){
//            lightIndex++;
//            lightIndex %= roads.length;
//            roads[lightIndex].toggleLightValue();
//            countdownTimer = roads[lightIndex].getGreenTime();
//        }
//        else{
//            countdownTimer--;
//        }
        Vehicle[] temp = roads[lightIndex].proceed(countdownTimer);
        countdownTimer--;
        return temp;
    }
    public void enqueueVehicle(int roadIndex, int wayIndex, int laneIndex, Vehicle vehicle){
        if(roadIndex < 0 || roadIndex > roads.length || wayIndex < 0 || wayIndex > 2 ||
                laneIndex < 0 || laneIndex > 3 || vehicle == null){
            throw new IllegalArgumentException("Intersection.enqueueVehicle error");
        }
        roads[roadIndex].enqueueVehicle(wayIndex, laneIndex, vehicle);
        System.out.println("\t\tCar " + vehicle.toString() + " entered " + roads[roadIndex].getName() + ", going "
                + roads[roadIndex].getWay(wayIndex) + " in " + roads[roadIndex].getLane(laneIndex) + ".");
    }
    public boolean isEmpty(){
        boolean allRoadsEmpty = true;
        for(TwoWayRoad TWR: roads){
            allRoadsEmpty = TWR.isRoadEmpty();
            if(!allRoadsEmpty){
                break;
            }
        }
        return allRoadsEmpty;
    }
    public void updateStatus(){
        if(roads[lightIndex].getPreemptLightChange()){
            roads[lightIndex].toggleLightValue();
        }
        if(roads[lightIndex].getLightValue() == LightValue.RED){
            lightIndex = ++lightIndex % roads.length;
            roads[lightIndex].toggleLightValue();
            countdownTimer = roads[lightIndex].getGreenTime();
        }
    }
    public String getStatus(){
        return roads[lightIndex].getLightValue().getSignal() + " for " + roads[lightIndex].getName() + ".";
    }
    public String toString(){
        String printout = "";
        for(TwoWayRoad TWR: roads){
            printout += TWR.toString() + "\n";
        }
        return printout;
    }
}