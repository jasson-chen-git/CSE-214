package hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Zork {
    private static Scanner in = new Scanner(System.in);
    private static StoryTree Game;
    public static void main(String args[]) {
        String filename = initialization();
        console();
        saving(filename);
        terminating();
    }
    public static String initialization(){
        System.out.print("Enter the file name: ");
        String filename = in.nextLine();
        try{
            Game = StoryTree.readTree(filename);
//            Game.printTree();
        } catch (DataFormatException | FileNotFoundException | NodeNotPresentException e) {
            System.out.println(e.getMessage());
            Game = new StoryTree();
            filename = null;
        }
        return filename;
    }
    public static void console() {
        char user;
        do{
            System.out.print("Would you like to edit (E), play (P) or quit (Q)?");
            user = in.nextLine().toLowerCase().charAt(0);
            if(user == 'e'){
                edit();
            }
            else if(user == 'p'){
                play();
            }
            else{
                System.out.println("Invalid input. Try again");
            }
        }while(user != 'q');
    }
    public static void edit() {
        char user;
        do {
            printMenu();
            user = in.nextLine().toLowerCase().charAt(0);
            switch (user){
                case 'v':
                    System.out.println(Game.view());
                    break;
                case 's':
                    try{
                        please_select_a_child();
                        Game.selectChild(in.nextLine());    //  might need to add trim()
                    } catch (DataFormatException | NodeNotPresentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'o':
                    System.out.print("Please enter a new option: ");
                    Game.setCursorOption(in.nextLine());
                    break;
                case 'm':
                    System.out.print("Please enter a new message: ");
                    Game.setCursorMessage(in.nextLine());
                    break;
                case 'a':
                    System.out.print("Enter an option: ");
                    String option = in.nextLine();
                    System.out.print("Enter a message: ");
                    String message = in.nextLine();
                    try{
                        Game.addChild(option, message);
                    } catch (FullTreeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'd':
                    System.out.print("Please select a child: ]");
                    break;
                case 'r':
                    Game.resetCursor();
                    System.out.println("Cursor moved to root.");
                    break;
                case 'q':
                    break;
                default:
                    System.out.println("Invalid input.");
            }
            System.out.println();
        }while(user != 'q');
    }
    public static void please_select_a_child() throws NodeNotPresentException {
        if(Game.getOptions().length == 0){
            throw new NodeNotPresentException("This node has no children.");
        }
        System.out.println("Please select a child: [");
        if(Game.getOptions().length > 0) {
            System.out.print("1");
        }
        if(Game.getOptions().length > 1) {
            System.out.print(", 2");
        }
        if(Game.getOptions().length > 2) {
            System.out.print(", 3");
        }
        System.out.println("] ");
    }
    public static void play() {
        char user;
        do{

        }while(Game.getGameState() == GameState.GAME_NOT_OVER);
    }
    public static void printMenu(){
        System.out.println("Zork Editor:\n" +
                "    V: View the cursor's position, option and message.\n" +
                "    S: Select a child of this cursor (options are 1, 2, and 3).\n" +
                "    O: Set the option of the cursor.\n" +
                "    M: Set the message of the cursor.\n" +
                "    A: Add a child StoryNode to the cursor.\n" +
                "    D: Delete one of the cursor's children and all its descendants.\n" +
                "    R: Move the cursor to the root of the tree.\n" +
                "    Q: Quit editing and return to main menu.");
    }
    public static void saving(String filename){
        if(filename == null){
            System.out.print("Enter the file name to save to: ");
            filename = in.nextLine();
        }
        try{
            StoryTree.saveTree(filename, Game);
            System.out.println("Save successful!");
        } catch (FileNotFoundException e) {
            System.out.println("!!! Save unsuccessful!");
        }
    }
    public static void terminating(){
        System.out.println();
        System.out.println("Program terminating normally.");
    }
}
