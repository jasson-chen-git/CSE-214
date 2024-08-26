package hw1;

/**
 * This class implements a Player object containing a name, number of hits and errors
 * @author Jason Chen
 *      jason.chen.5@stonybrook.edu
 *      112515450
 *      Recitation 4
 *      Homework 1
 */
public class Player implements Cloneable{
    private String name;
    private int numHits;
    private int numErrors;

    /**
     * Default player constructor
     */
    Player(){}

    /**
     * This constructor method creates a player object with the passed values
     * @param name
     *      Player object's name
     * @param hits
     *      Player object's hit statistic
     * @param errors
     *      Player object's error statistic
     */
    Player(String name, int hits, int errors){
        this.setName(name);
        this.setNumHits(hits);
        this.setNumErrors(errors);
    }

    /**
     * This method creates a new Player object with similar data fields
     * @return
     *      Player object with similar data fields, must be typecast to Player
     */
    public Object clone(){
        return new Player(this.name, this.numHits, this.numErrors);
    }

    /**
     * Getter method for the variable name
     * @return
     *      Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for the player's name
     * @param name
     *      Player's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the player's hits
     * @return
     *      Player's hits
     */
    public int getNumHits() {
        return numHits;
    }

    /**
     * Setter method for the player's hits
     * @param numHits
     *      Player's hits
     * @throws IllegalArgumentException
     *      When hits is negative
     */
    public void setNumHits(int numHits) {
        if(numHits < 0){
            throw new IllegalArgumentException("Invalid number");
        }
        this.numHits = numHits;
    }

    /**
     * Getter method for the player's errors
     * @return
     *      Player's errors
     */
    public int getNumErrors() {
        return numErrors;
    }

    /**
     * Setter method for the player's errors
     * @param numErrors
     *      Player's errors
     * @throws IllegalArgumentException
     *      When errors is negative
     */
    public void setNumErrors(int numErrors) {
        if(numErrors < 0){
            throw new IllegalArgumentException("Invalid number");
        }
        this.numErrors = numErrors;
    }

    /**
     * This method formats the player's data fields into a single string
     * @return
     *      String containing player's name, hits and errors
     */
    public String toString(){
        return this.name + " - " + this.numHits + " hits, " + this.numErrors + " errors";
    }
}