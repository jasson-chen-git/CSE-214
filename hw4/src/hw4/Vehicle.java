package hw4;

public class Vehicle {
    private static int serialCounter = 0;
    private int serialID;
    private int timeArrived;
    Vehicle(int initTimeArrived){
        if(initTimeArrived <= 0){
            throw new IllegalArgumentException("Initial time cannot be <= 0");
        }
        serialID = ++serialCounter;
        timeArrived = initTimeArrived;
    }
    public int getSerialID() {
        return serialID;
    }
    public int getTimeArrived() {
        return timeArrived;
    }
    public String toString(){
        return String.format("[%03d]", serialID);
    }
}