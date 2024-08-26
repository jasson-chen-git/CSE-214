package hw6;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The <code>StorageManager</code> class implements an interface to
 * <code>StorageTable</code> object.
 *
 * @author Jason Chen
 *      112515450
 *      jason.chen.5@stonybrook.edu
 *      Recitation 4
 *      Homework 6
 */
public class StorageManager {
    private static Scanner in = new Scanner(System.in);
    private static StorageTable storage = new StorageTable();

    /**
     * The main method of the <code>StorageManager</code>.
     *
     * Calls two subroutines to execute the function of the program.
     *
     * @param args
     *      Command line arguments
     */
    public static void main(String[] args){
        welcome();
        console();
    }

    /**
     * Prints out the welcome message to the console.
     *
     * Calls a subroutine to import previous data if it exists.
     */
    public static void welcome() {
        try{
            load();
            System.out.println("Previous storage data found.");
        } catch (IOException e){
            System.out.println("No previous data found.");
        } catch (ClassNotFoundException e) {
            System.out.println("Storage not found.");
        }
        System.out.println("Hello, and welcome to Rocky Stream Storage Manager\n");
    }

    /**
     * Loads previous data from the file.
     *
     * @throws IOException
     *      Indicates that the file is not found
     *
     * @throws ClassNotFoundException
     *      Indicates that the file contains no <code>StorageTable</code> object
     */
    public static void load() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream("storage.obj");
        ObjectInputStream inStream  = new ObjectInputStream(file);
        storage = (StorageTable) inStream.readObject();
        inStream.close();
    }

    /**
     * Continuously surveys the user's input from the console.
     *
     * Calls two subroutines to print a message to the console
     * and execute from the user's input.
     */
    public static void console() {
        char user;
        do {
            printMenu();
            user = in.nextLine().toLowerCase().charAt(0);
            execute(user);
        } while(user != 'q' & user != 'x');
    }

    /**
     * Prints the menu options to the console.
     */
    public static void printMenu() {
        System.out.print("P - Print all storage boxes\n" +
                "A - Insert into storage box\n" +
                "R - Remove contents from a storage box\n" +
                "C - Select all boxes owned by a particular client\n" +
                "F - Find a box by ID and display its owner and contents\n" +
                "Q - Quit and save workspace\n" +
                "X - Quit and delete workspace\n" +
                "\n" +
                "Please select an option: ");
    }

    /**
     * Carries out a task from the given parameter.
     *
     * Different tasks may require further inputs.
     *
     * @param user
     *      The user's input
     */
    public static void execute(char user) {
        System.out.println();
        switch (user) {
            case 'p':
                storage.printStorageTableAll();
                break;
            case 'a':
                try{
                    System.out.print("Please enter id: ");
                    int id = in.nextInt();
                    in.nextLine();
                    System.out.print("Please enter client: ");
                    String client = in.nextLine();
                    System.out.print("Please enter contents: ");
                    String contents = in.nextLine();
                    System.out.println();
                    storage.putStorage(id, new Storage(id, client, contents));
                    System.out.println("Storage " + id + " set!");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Returning to main menu.");
                    if(in.hasNextLine()){
                        in.nextLine();
                    }
                }
                break;
            case 'r':
                try {
                    System.out.print("Please enter ID: ");
                    int id = in.nextInt();
                    in.nextLine();
                    System.out.println();
                    storage.removeStorage(id);
                    System.out.println("Box " + id + " is now removed.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Returning to main menu.");
                    if(in.hasNextLine()){
                        in.nextLine();
                    }
                }
                break;
            case 'c':
                try {
                    System.out.print("Please enter the name of the client: ");
                    String client = in.nextLine();
                    System.out.println();
                    storage.printStorageTableSelect(client);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 'f':
                try {
                    System.out.print("Please  enter ID: ");
                    int id = in.nextInt();
                    in.nextLine();
//                    if(storage.getStorage(id) == null) {
//                        System.out.println("Box " + id + " not found in storage.");
//                    }
//                    else{
//                        System.out.printf("Box %d\nContents: %s\nOwner: %s%n",
//                                storage.getStorage(id).getId(),
//                                storage.getStorage(id).getContent(),
//                                storage.getStorage(id).getClient());
//                    }
                    System.out.printf("Box %d\nContents: %s\nOwner: %s%n",
                            storage.getStorage(id).getId(),
                            storage.getStorage(id).getContent(),
                            storage.getStorage(id).getClient());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Returning to main menu.");
                    if(in.hasNextLine()){
                        in.nextLine();
                    }
                }
                break;
            case 'q':
                try {
                    export();
                } catch (IOException e){
                    System.out.println("Error saving workspace.");
                }
                break;
            case 'x':
                try {
                    delete();
                } catch (IOException e) {
                    System.out.println("Error deleting workspace.");
                }
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
        System.out.println();
    }

    /**
     * Exports the current state of <code>storage</code> to a file.
     *
     * @throws IOException
     *      Indicates an error with creating/saving to the file.
     */
    public static void export() throws IOException {
        FileOutputStream file = new FileOutputStream("storage.obj");
        ObjectOutputStream outStream = new ObjectOutputStream(file);
        outStream.writeObject(storage);
        outStream.close();
        System.out.println("Storage Manager is quitting, current storage is saved for next session.");
    }

    /**
     * Deletes the save file.
     *
     * @throws IOException
     *      Indicates an error locating the file.
     */
    public static void delete() throws IOException {
        File file = new File("storage.obj");
        if(file.delete()) {
            System.out.println("Storage Manager is quitting, all data is being erased.");
        } else {
            System.out.println("Failed to delete file.");
        }
    }
}
