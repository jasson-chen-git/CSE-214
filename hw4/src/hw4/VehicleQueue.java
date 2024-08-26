package hw4;

import java.util.LinkedList;
import java.util.Stack;

public class VehicleQueue extends LinkedList<Vehicle> {
    private LinkedList<Vehicle> vQueue;
    public VehicleQueue() {
        vQueue = new LinkedList<Vehicle>();
    }
    public void enqueue(Vehicle vehicle) {
        vQueue.addLast(vehicle);
    }
    public Vehicle dequeue() {
        if (isEmpty()) {
            return null;
        }
        return vQueue.removeFirst();
    }
    public boolean isEmpty() {
        return vQueue.isEmpty();
    }
    public int size(){
        return vQueue.size();
    }
    public String toString(){
        String printout = "";
        VehicleQueue tempQueue = new VehicleQueue();
        Vehicle tempVehicle;
        while(!this.isEmpty()){
            tempQueue.enqueue(dequeue());
        }
        while(!tempQueue.isEmpty()){
            tempVehicle = tempQueue.dequeue();
            printout += tempVehicle.toString();
            this.enqueue(tempVehicle);
        }
        return printout;
    }
    public String toStringBackward(){
        String printout = "";
        Stack<Vehicle> tempStack = new Stack<>();
        Stack<Vehicle> tempStack2 = new Stack<>();
        Vehicle tempVehicle;
        while(!this.isEmpty()){
            tempStack.push(dequeue());
        }
        while(!tempStack.empty()){
            tempVehicle = tempStack.pop();
            printout += tempVehicle.toString();
            tempStack2.push(tempVehicle);
        }
        while(!tempStack2.empty()){
            this.enqueue(tempStack2.pop());
        }
        return printout;
    }
}