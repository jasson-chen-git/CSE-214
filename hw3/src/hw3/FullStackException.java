package hw3;

/**
 * This class extends Exceptions and is thrown whenever the number
 * of Cargo will exceed the maximum allowed in a single stack once
 * a new Cargo is loaded on
 * @author Jason Chen
 *      112515450
 *      Recitation 4
 *      Homework 3
 */
public class FullStackException extends Exception {
    public FullStackException(String message) {
        super(message);
    }
}