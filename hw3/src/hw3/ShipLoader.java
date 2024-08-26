package hw3;

import java.util.EmptyStackException;
import java.util.Scanner;

/**
 * This class creates a ShipLoader object with a Cargo Ship, Dock
 * and contains the main method.
 * @author Jason Chen
 *      112515450
 *      Recitation 4
 *      Homework 3
 */
public class ShipLoader {
    private static CargoShip Ahoy;
    private static CargoShip Dock = new CargoShip(1, 999999, 999999);
    private static Scanner in = new Scanner(System.in);

    /**
     * Main method
     *      Continuously asks the user for an input until terminated
     */
    public static void main(String args[]){
        startup();
        char user_input;
        do{
            printAllContainers();
            System.out.println();
            System.out.print(menu());
            user_input = in.nextLine().toLowerCase().charAt(0);
            redirect(user_input);
            System.out.println();
        }while(user_input!= 'q');
        System.out.println("Program terminating normally...");
    }

    /**
     * This method will ask for specifications of the Ship and initialize it
     */
    public static void startup(){
        char user_input;
        boolean ship_created = false;
        while(!ship_created){
            try{
                System.out.println("Welcome to ShipLoader!\n" +
                        "Cargo Ship Parameters\n" +
                        "--------------------------------------------------");
                System.out.print("Number of stacks: ");
                int initStack = in.nextInt();
                in.nextLine();
                System.out.print("Maximum height of stacks: ");
                int initMaxHeight = in.nextInt();
                in.nextLine();
                System.out.print("Maximum total cargo weight: ");
                double initMaxWeight = in.nextDouble();
                in.nextLine();
                Ahoy = new CargoShip(initStack, initMaxHeight, initMaxWeight);
                System.out.println("Cargo ship created.\n" +
                        "Pulling ship in to dock...\n" +
                        "Cargo ship ready to be loaded.\n");
                ship_created = true;
            }
            catch(IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Error creating ship. Please try again.");
            }
        }
    }

    /**
     * This method redirects the user's input and selects an operation to be
     * performed or carried out with additional inputs
     * @param user_input
     *      The user's menu selection
     */
    public static void redirect(char user_input){
        switch(user_input){
            case 'c':
                System.out.print("Enter the name of the cargo: ");
                String cargoName = in.nextLine();
                System.out.print("Enter the weight of the cargo: ");
                double cargoWeight = in.nextDouble();
                in.nextLine();
                System.out.print("Enter the container strength (F/M/S): ");
                char cargoStrengthAbbrev = in.nextLine().toUpperCase().charAt(0);
                CargoStrength cargoStrength;
                if(cargoStrengthAbbrev == 'F'){
                    cargoStrength = CargoStrength.FRAGILE;
                }
                else if(cargoStrengthAbbrev == 'M'){
                    cargoStrength = CargoStrength.MODERATE;
                }
                else if(cargoStrengthAbbrev == 'S'){
                    cargoStrength = CargoStrength.STURDY;
                }
                else{
                    cargoStrength = null;
                }
                try{
                    Dock.pushCargo(new Cargo(cargoName, cargoWeight, cargoStrength), 1);
                    System.out.println("Cargo '" + cargoName + "' pushed onto the dock.");
                }
                catch(IllegalArgumentException | FullStackException | ShipOverweightException |
                      CargoStrengthException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'l':
                try{
                    System.out.print("Select the load destination stack index: ");
                    int stackIndex = in.nextInt();
                    in.nextLine();
                    Cargo temp = Dock.getCargoStack(1).peek();
                    Ahoy.pushCargo(temp, stackIndex);
                    System.out.println("Cargo '" + Dock.popCargo(1).getName() +
                            "' moved from dock to stack " + stackIndex + ".");
                }
                catch(IllegalArgumentException | FullStackException | ShipOverweightException |
                      CargoStrengthException e){
                    System.out.println(e.getMessage());
                }
                catch(EmptyStackException e){
                    System.out.println("Dock is empty.");
                }
                break;
            case 'u':
                try{
                    System.out.print("Select the unload source stack index: ");
                    int stackIndex = in.nextInt();
                    in.nextLine();
                    Cargo temp = Ahoy.getCargoStack(stackIndex).peek();
                    Dock.pushCargo(temp, 1);
                    Ahoy.popCargo(stackIndex);
                    System.out.println("Cargo '" + Dock.getCargoStack(1).peek().getName() +
                            "' moved from stack " + stackIndex + " to dock.");
                }
                catch(IllegalArgumentException | FullStackException | ShipOverweightException |
                      CargoStrengthException e){
                    System.out.println(e.getMessage());
                }
                catch(EmptyStackException e){
                    System.out.println("Stack is empty.");
                }
                break;
            case 'm':
                try{
                    System.out.print("Source stack index: ");
                    int srcStackIndex = in.nextInt();
                    in.nextLine();
                    System.out.print("Destination stack index: ");
                    int destStackIndex = in.nextInt();
                    in.nextLine();
                    Cargo temp = Ahoy.getCargoStack(srcStackIndex).peek();
                    Ahoy.pushCargo(temp, destStackIndex);
                    Ahoy.popCargo(srcStackIndex);
                    System.out.println("Cargo '" + Ahoy.getCargoStack(destStackIndex).peek().getName() +
                            "' moved from stack " + srcStackIndex +
                            " to stack " + destStackIndex + ".");
                }
                catch(IllegalArgumentException | FullStackException | ShipOverweightException |
                      CargoStrengthException e){
                    System.out.println(e.getMessage());
                }
                catch(EmptyStackException e){
                    System.out.println("Stack is empty.");
                }
                break;
            case 'k':
                while(Dock.currentSize() != 0){
                    Dock.popCargo(1);
                }
                System.out.println("Dock cleared.");
                break;
            case 'p':
                System.out.print(Ahoy.toString());
                break;
            case 's':
                System.out.print("Enter the name of the cargo: ");
                String search = in.nextLine().toLowerCase();
                Ahoy.findAndPrint(search);
                break;
            case 'q':
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    /**
     * This method will print out each Cargo stack on the Ship and the Dock
     * as well as the current weight of Cargo aboard the Ship
     */
    public static void printAllContainers(){
        System.out.print(Ahoy.toString());
        System.out.println(Dock.toStringDock());
        System.out.println(Ahoy.weightToString());
    }

    /**
     * @return
     *      Formatted string of the options menu
     */
    public static String menu(){
        return "Please select an option:\n" +
                "C) Create new cargo <name> <weight> <strength>\n" +
                "L) Load cargo from dock <stackIndex>\n" +
                "U) Unload cargo from ship <srcStackIndex>\n" +
                "M) Move cargo between stacks <srcStackIndex> <dstStackIndex>\n" +
                "K) Clear dock\n" +
                "P) Print ship stacks\n" +
                "S) Search for cargo <name>\n" +
                "Q) Quit" +
                "\nSelect a menu option: ";
    }
}