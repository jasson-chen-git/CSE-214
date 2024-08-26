package hw3;

/**
 * This class implements a Cargo container with private data fields name, weight,
 * and strength. This class is immutable for the purpose of this assignment and
 * will not have setter methods.
 * @author Jason Chen
 *      112515450
 *      Recitation 4
 *      Homework 3
 */
public class Cargo {
    private String name;
    private double weight;
    private CargoStrength strength;

    /**
     * This constructor creates a new Cargo with a name, initial weight and
     * initial strength
     * @param initName
     *      Initial name of the Cargo
     * @param initWeight
     *      Initial weight of the Cargo
     * @param initStrength
     *      Initial strength of the Cargo
     * @throws IllegalArgumentException
     *      When initName is null
     *      When initWeight is less than 0
     *      When initStrength is null
     */
    public Cargo(String initName, double initWeight, CargoStrength initStrength){
        if(initName == null){
            throw new IllegalArgumentException("Invalid cargo name.");
        }
        if(initWeight <= 0){
            throw new IllegalArgumentException("Invalid cargo weight.");
        }
        if(initStrength == null){
            throw new IllegalArgumentException("Invalid cargo strength.");
        }
        this.name = initName;
        this.weight = initWeight;
        this.strength = initStrength;
    }

    /**
     * @return
     *      Name of the Cargo
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return
     *      Weight of the Cargo
     */
    public double getWeight(){
        return this.weight;
    }

    /**
     * @return
     *      Strength of the Cargo
     */
    public CargoStrength getStrength(){
        return this.strength;
    }

    /**
     * Formats the contents of the Cargo as a string
     * @return
     *      Contents of the Cargo as a string
     */
    public String toString(){
        return this.name + ", " + this.weight + ", " + this.strength;
    }
}