package hw3;

/**
 * This class extends Exception and is thrown whenever a new Cargo
 * is to be loaded on top of an existing Cargo with a weaker
 * strength level/value
 * @author Jason Chen
 *      112515450
 *      Recitation 4
 *      Homework 3
 */
public class CargoStrengthException extends Exception{
    public CargoStrengthException(String message){
        super(message);
    }
}