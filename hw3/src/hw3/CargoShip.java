package hw3;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class creates a Ship to allocate a specified number of Cargo stacks,
 * which is limited by a maximum stack height. The Ship also contains weight
 * counter and limit
 * @author Jason Chen
 *      112515450
 *      Recitation 4
 *      Homework 3
 */
public class CargoShip {
    private Stack<Cargo>[] CargoStack;
    private int numStack;
    private int maxHeight;
    private double maxWeight;
    private double currentWeight;

    /**
     * This constructor creates a ship with the specified limitations
     * @param initNumStack
     *      Number of stacks available on the Ship
     * @param initMaxHeight
     *      Maximum height of each Cargo stack
     * @param initMaxWeight
     *      Maximum weight of Cargo the Ship can support
     */
    public CargoShip(int initNumStack, int initMaxHeight, double initMaxWeight){
        if(initNumStack <= 0 || initMaxHeight <= 0 || initMaxHeight <= 0){
            throw new IllegalArgumentException("Invalid values.");
        }
        this.numStack = initNumStack;
        this.maxHeight = initMaxHeight;
        this.maxWeight = initMaxWeight;
        this.CargoStack = new Stack[initNumStack];
        for(int i = 0; i < initNumStack; i++){
            CargoStack[i] = new Stack <Cargo>();
        }
        this.currentWeight = 0;
    }

    /**
     * This method will create a temporary stack to pop all Cargo containers ot the
     * specified Cargo stack before pushing them back to their original stack. While
     * being popped out, the size will increment.
     * @param pile
     *      Cargo stack to be counted
     * @return
     *      Number of Cargo at the current stack
     */
    public int currentSize(Stack<Cargo> pile){  // to replace the .size() method of Vector
        int size = 0;
        Stack<Cargo> temp = new Stack<Cargo>();
        while(!pile.empty()){
            temp.push(pile.pop());
            size ++;
        }
        while(!temp.empty()){
            pile.push(temp.pop());
        }
        return size;
    }

    /**
     * This method is designed specifically for the Dock since it
     * has only one Cargo stack
     * @return
     *      Number of Cargo of the first stack
     */
    public int currentSize(){  //   custom size for Dock stack
        return currentSize(CargoStack[0]);
    }

    /**
     * This method retrieves the entire Cargo stack specified by the index
     * @param stackIndex
     *      The index of the desired stack
     * @return
     *      The Cargo stack at the index
     */
    public Stack <Cargo> getCargoStack(int stackIndex){
        return this.CargoStack[stackIndex - 1];
    }

    /**
     * This method will push a Cargo container onto a stack specified by the index
     * and increases the current weight if successful.
     * @param cargo
     *      The Cargo to be pushed
     * @param stack
     *      The index of the stack
     * @throws IllegalArgumentException
     *      When the Cargo is null
     *      When the stack does not exist
     * @throws FullStackException
     *      When the stack is currently full
     * @throws ShipOverweightException
     *      When the Ship would exceed weight limit
     * @throws CargoStrengthException
     *      When the Cargo strength is not supported by the container underneath
     */
    public void pushCargo(Cargo cargo, int stack) throws FullStackException, ShipOverweightException,
            CargoStrengthException{
        if(cargo == null){
            throw new IllegalArgumentException("Cargo does not exist.");
        }
        else if(stack > this.numStack || stack < 1){
            throw new IllegalArgumentException("Stack does not exist.");
        }
        else if(currentSize(CargoStack[stack - 1]) >= this.maxHeight){  // removed "!CargoStack[stack - 1].empty() &&"
            throw new FullStackException("Operation failed! Cargo stack is at maximum height.");
        }
        else if(this.currentWeight + cargo.getWeight() > this.maxWeight){
            throw new ShipOverweightException("Operation failed! Cargo would put ship overweight.");
        }
        else if(!CargoStack[stack - 1].empty() &&
                CargoStack[stack - 1].peek().getStrength().strengthLevel < cargo.getStrength().strengthLevel){
            throw new CargoStrengthException("Operation failed! Cargo at top of stack cannot support weight.");
        }
        this.currentWeight += cargo.getWeight();
        CargoStack[stack - 1].push(cargo);
    }

    /**
     * This method will pop a Cargo container off a stack specified by the index
     * and decreases the current weight if successful
     * @param stack
     *      The index of the stack
     * @return
     *      The Cargo being popped
     * @throws EmptyStackException
     *      When the stack is empty
     */
    public Cargo popCargo(int stack) throws EmptyStackException{
        if(CargoStack[stack - 1].isEmpty()){
            throw new EmptyStackException();
        }
        currentWeight -= CargoStack[stack - 1].peek().getWeight();
        return CargoStack[stack - 1].pop();
    }

    /**
     * This method will print the details of all Cargo with the same name
     * that is specified by storing the details of the Cargo in a temporary
     * Cargo stack and the location at which the Cargo was found in
     * separate integer stacks
     * @param name
     *      The name of the Cargo
     */
    public void findAndPrint(String name){
        Stack <Cargo> temp = new Stack <Cargo>();
        Stack <Cargo> foundStack = new Stack <Cargo>();
        Stack <Integer[]> stack_depth = new Stack <Integer[]>();    //  {stack_number, depth}
        Stack <Integer[]> temp_intStack = new Stack <Integer[]>();  //  {stack_number, depth}
        int found = 0;
        double found_weight = 0;
        // to keep track of all necessary information for display
        for(int i = 0; i < this.numStack; i++){
            int depth = 0;
            while(!CargoStack[i].empty()){      //  check current stack by popping into temp
                if(CargoStack[i].peek().getName().toLowerCase().equals(name)){  //  if found
                    foundStack.push(CargoStack[i].peek());                      //  push a copy into foundStack
                    stack_depth.push(new Integer[]{i, depth});                  //  save the location Cargo was found
                    found++;
                    found_weight += foundStack.peek().getWeight();
                }
                temp.push(CargoStack[i].pop());
                depth++;
            }                                   //  once fully checked,
            while(!temp.empty()){               //  push the entire stack back from temp
                CargoStack[i].push(temp.pop());
            }                                   //  repeat for the next stack
        }
        while(!foundStack.empty()){     //  to reverse the order in which found
            temp.push(foundStack.pop());
        }
        while(!stack_depth.empty()){    //  to reverse the order in which found
            temp_intStack.push(stack_depth.pop());
        }
        if(found > 0){
            System.out.println(String.format(" %-7s %-7s %-8s %-8s\n" +
                    "=======+=======+========+==========", "Stack", "Depth", "Weight", "Strength"));
            while(!temp.empty()){
                System.out.println(String.format("   %-4s|   %-4s|  %-6.0f|  %-8s",
                        stack_depth.push(temp_intStack.pop())[0] + 1,
                        stack_depth.peek()[1],
                        foundStack.push(temp.pop()).getWeight(),
                        foundStack.peek().getStrength()));
            }
            System.out.println("Total Count: " + found);
            System.out.println("Total Weight: " + found_weight);
        }
        else{
            System.out.println("Cargo '" + name + "' could not be found on the ship.");
        }
    }

    /**
     * This method formats all the Cargo of the Ship as a string
     * @return
     *      Formatted string of all Cargo aboard the Ship
     */
    public String toString(){
        String printout = "";
        for(int i = 0; i < this.numStack; i++){
            String printStack = "Stack " + (i + 1) + ":";
            Stack <Cargo> temp = new Stack <Cargo>();
            while(!CargoStack[i].empty()){
                temp.push(CargoStack[i].pop());
            }
            while(!temp.empty()){
                printStack += " " + CargoStack[i].push(temp.pop()).getStrength().abbrev;
                if(!temp.empty()){
                    printStack +=  ", ";
                }
            }
            printStack += "\n";
            printout += printStack;
        }
        return printout;
    }

    /**
     * This method formats all the Cargo of the Dock as a string
     * @return
     *      Formatted string of all Cargo on the Dock
     */
    public String toStringDock(){
        Stack <Cargo> temp = new Stack <Cargo>();
        String printout = "Dock: ";
        while(!CargoStack[0].empty()){
            temp.push(CargoStack[0].pop());
        }
        while(!temp.empty()){
            printout += CargoStack[0].push(temp.pop()).getStrength().abbrev;
            if(!temp.empty()){
                printout += ", ";
            }
        }
        return printout + "\n";
    }
    public String weightToString(){
        return String.format("Total Weight = %.0f / %.0f", this.currentWeight, this.maxWeight);
    }
}