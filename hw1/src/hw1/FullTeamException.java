package hw1;

/**
 * This class is a custom exception class to handle cases when the team is full
 * @author Jason Chen
 *      jason.chen.5@stonybrook.edu
 *      112515450
 *      Recitation 4
 *      Homework 1
 */
public class FullTeamException extends Exception {
    /**
     * @param message
     *      Message to the console notifying of full team
     */
    public FullTeamException(String message) {
        super(message);
    }
}