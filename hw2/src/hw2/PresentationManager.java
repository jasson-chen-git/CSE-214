package hw2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class creates a PresentationManager object and contains
 * a new List and the main method
 * @author Jason Chen
 *      jason.chen.5@stonybrook.edu
 *      112515450
 *      Recitation 4
 *      Homework 2
 */
public class PresentationManager {
    private static SlideList slideshow = new SlideList();
    public static Scanner in = new Scanner(System.in);

    /**
     * The main method:
     *      Continuously asks the user for an input until quitted
     */
    public static void main(String[] args){
        char c;
        System.out.println("Welcome to PresentationManager!\n");
        do{
            System.out.print(menu());
            c = in.nextLine().toLowerCase().charAt(0);
            redirect(c);
            System.out.println();
        }while(c != 'q');
        System.out.println("Program terminating normally...");
    }

    /**
     * @return
     *      A formatted string of the main menu
     */
    public static String menu(){
        return "Please select an option:\n" +
                "F)\tMove cursor forward\n" +
                "B)\tMove cursor backward\n" +
                "D)\tDisplay cursor slide\n" +
                "E)\tEdit cursor slide\n" +
                "P)\tPrint presentation summary\n" +
                "A)\tAppend new slide to tail\n" +
                "I)\tInsert new slide before cursor\n" +
                "R)\tRemove slide at cursor\n" +
                "H)\tReset cursor to head\n" +
                "Q)\tQuit\n\n" +
                "Select a menu option: ";
    }

    /**
     * Performs a function of the program depending on the user input
     * and handles exceptions
     * @param c
     *      The user inputted option from the main menu
     */
    public static void redirect(char c){
        switch (c){
            case 'f':
                try{
                    slideshow.cursorForward();
                    System.out.println("The cursor moved forward to slide \"" +
                            slideshow.getCursorSlide().getTitle() + "\"");
                }
                catch(EndOfListException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'b':
                try{
                    slideshow.cursorBackwards();
                    System.out.println("The cursor moved backward to slide \"" +
                            slideshow.getCursorSlide().getTitle() + "\"");
                }
                catch(EndOfListException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'd':
                try{
                    System.out.println(slideshow.getCursorSlide().toString());
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'e':
                try{
                    editSlide();
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'p':
                System.out.println(slideshow.toString());
                break;
            case 'a':
                try{
                    slideshow.appendToTail(slideDetails());
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'i':
                try{
                    slideshow.inertBeforeCursor(slideDetails());
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'r':
                try{
                    System.out.println("Slide \"" + slideshow.removeCursor().getTitle() +
                            "\" has been removed");
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'h':
                try{
                    slideshow.resetCursorToHead();
                    System.out.println("Cursor has been reset to the head");
                }
                catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 'q':
                break;
            default:
                System.out.println("Invalid option");

        }
    }

    /**
     * This static method assists in editing a Slide
     * when option 'e' is selected
     * @throws IllegalArgumentException
     *      When the Slide is empty
     * @throws IllegalArgumentException
     *      When the user input is not one of the accepted options
     *      for editing a Slide
     */
    public static void editSlide(){
        if(slideshow.getCursorSlide() == null){
            throw new IllegalArgumentException("Empty slideshow");
        }
        System.out.print("Edit title, duration or bullets? (t/d/b) ");
        char c = in.nextLine().toLowerCase().charAt(0);
        if(c == 't'){
            System.out.print("Title: ");
            editTitle(in.nextLine());
        }
        else if(c == 'd'){
            System.out.print("Duration: ");
            double newDuration = in.nextDouble();
            in.nextLine();
            editDuration(newDuration);
        }
        else if(c == 'b'){
            editBullet();
        }
        else{
            throw new IllegalArgumentException("Invalid option");
        }
    }

    /**
     * This static method updates the title of the Slide
     * of the Node that the cursor points to
     * @param newTitle
     *      The new title of the Slide
     */
    public static void editTitle(String newTitle){
        slideshow.getCursorSlide().setTitle(newTitle);
    }

    /**
     * This static method updates the duration of the Slide
     * of the Node that the cursor points to
     * @param newDuration
     *      The new duration of the Slide
     */
    public static void editDuration(double newDuration){
        slideshow.getCursorSlide().setDuration(newDuration);
    }

    /**
     * This static method assists in editing a specified bullet point
     * @throws IllegalArgumentException
     *      When the index of the bullet point is invalid (index starting from 1)
     * @throws IllegalArgumentException
     *      When the user input is not one of the accepted option
     *      for deleting or editing a bullet point
     */
    public static void editBullet() {
        System.out.print("Bullet index: ");
        int bulletNumber = in.nextInt();
        in.nextLine();
        if(bulletNumber < 1 || bulletNumber > slideshow.getCursorSlide().getNumBullets()){
            throw new IllegalArgumentException("Invalid index");
        }
        try{
            slideshow.getCursorSlide().getBullet(bulletNumber);
            System.out.print("Delete or edit? (d/e) ");
            char c = in.nextLine().toLowerCase().charAt(0);
            if(c == 'd'){
                slideshow.getCursorSlide().setBullets(null, bulletNumber);
                System.out.println("Bullet " + bulletNumber + " has been deleted");
            }
            else if(c == 'e'){
                System.out.println("Bullet " + bulletNumber + ": ");
                slideshow.getCursorSlide().setBullets(in.nextLine(), bulletNumber);
            }
            else{
                throw new IllegalArgumentException("Invalid option");
            }
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * This static method assists in creating a new Slide by asking
     * the user for various inputs and Slide details
     * @return
     *      The newly created Slide
     * @throws IllegalArgumentException
     *      When the duration of the Slide is invalid
     * @throws IllegalArgumentException
     *      When the user input is not one of the accepted option
     *      for adding a new bullet point
     */
    public static Slide slideDetails(){
        Slide page = new Slide();
        System.out.print("Enter the slide title: ");
        page.setTitle(in.nextLine());
        System.out.print("Enter the slide duration: ");
        try{
            double newDuration = in.nextDouble();
            in.nextLine();
            page.setDuration(newDuration);
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        boolean maxBulletsReached = false;
        for(int i = 1; i < 6; i++){
            System.out.print("Bullet " + i + ": ");
            page.setBullets(in.nextLine(), i);
            if(i == 5){
                maxBulletsReached = true;
                break;
            }
            System.out.print("Add another bullet point? (y/n) ");
            char c = in.nextLine().toLowerCase().charAt(0);
            if(c == 'n'){
                break;
            }
            else if(c != 'y'){
                throw new IllegalArgumentException("Invalid option");
            }
        }
        if(maxBulletsReached){
            System.out.println("No more bullets allowed. Slide is full.\n");
        }
        System.out.println("Slide \"" + page.getTitle() + "\" added to presentation");
        return page;
    }
}