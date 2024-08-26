package hw2;

/**
 * This class is a custom exception to handle when a cursor
 * cannot traverse past the head or tail of a Linked List
 * @author Jason Chen
 *      jason.chen.5@stonybrook.edu
 *      112515450
 *      Recitation 4
 *      Homework 2
 */
public class EndOfListException extends Exception {
    public EndOfListException(String message){
        super(message);
    }
}
