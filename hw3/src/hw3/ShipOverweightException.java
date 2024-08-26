package hw3;

/**
 * This class extends Exceptions and is thrown whenever the total
 * weight of all Cargo on the ship will exceed the weight limit once
 * a new Cargo is loaded on
 * @author Jason Chen
 *      112515450
 *      Recitation 4
 *      Homework 3
 */
public class ShipOverweightException extends Exception{
    public ShipOverweightException(String message){
        super(message);
    }
}