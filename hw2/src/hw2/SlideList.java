package hw2;

/**
 * This class creates a List of SlideListNode and adds
 * a slide counter and references to the head, tail
 * and a cursor which will be null until a Node is added
 * @author Jason Chen
 *      jason.chen.5@stonybrook.edu
 *      112515450
 *      Recitation 4
 *      Homework 2
 */
public class SlideList {
    private SlideListNode head;
    private SlideListNode tail;
    private SlideListNode cursor;
    private int slideCount;

    /**
     * This List constructor creates an empty list and
     * initializes the head, tail and cursor to null and
     * slide counter to 0
     */
    public SlideList(){
        this.head = this.tail = this.cursor = null;
        this.slideCount = 0;
    }

    /**
     * @return
     *      The slide counter
     */
    public int size(){
        return this.slideCount;
    }

    /**
     * This method traverses the entire (existing) List and adds
     * up the total duration of each Slide Node
     * @return
     *      The total duration of all Slide Nodes in the List
     */
    public double duration(){
        SlideListNode current = head;
        double duration = 0.0;
        while(current != null){
            duration += current.getData().getDuration();
            current = current.getNext();
        }
        return duration;
    }

    /**
     * This method traverses the entire (existing) List and adds
     * up the total number of bullets of each Slide Node
     * @return
     *      The total number of bullets of all Slide Nodes in the List
     */
    public int numBullets(){
        SlideListNode current = head;
        int numBullets = 0;
        while(current != null){
            numBullets += current.getData().getNumBullets();
            current = current.getNext();
        }
        return numBullets;
    }

    /**
     * @return
     *      The Slide of the Node pointed to by the cursor
     * @throws IllegalArgumentException
     *      When the cursor points to null
     */
    public Slide getCursorSlide(){
        if(this.size() == 0){
            throw new IllegalArgumentException("Empty slideshow");
        }
        return this.cursor.getData();
    }

    /**
     * This method sets the cursor equal to the head
     * @throws IllegalArgumentException
     *      When the list is empty
     */
    public void resetCursorToHead(){
        if(this.size() == 0){
            throw new IllegalArgumentException("Empty slideshow");
        }
        this.cursor = this.head;
    }

    /**
     * Moves the cursor to the next Node
     * @throws EndOfListException
     *      When the cursor is at the tail
     */
    public void cursorForward() throws EndOfListException {
        if(this.cursor == this.tail) {
            throw new EndOfListException("End of list cannot move forward");
        }
        this.cursor = this.cursor.getNext();
    }

    /**
     * Moves the cursor to the previous Node
     * @throws EndOfListException
     *      When the cursor is at the head
     */
    public void cursorBackwards() throws EndOfListException {
        if(this.cursor == this.head) {
            throw new EndOfListException("End of list cannot move backward");
        }
        this.cursor = this.cursor.getPrev();
    }

    /**
     * Inserts a new Slide Node before the cursor and
     * increments the slide counter without moving the cursor
     * @param newSlide
     *      The Slide to be wrapped and inserted
     * @throws IllegalArgumentException
     *      When the contents of the Slide is empty
     */
    public void inertBeforeCursor(Slide newSlide){
        if(newSlide == null){
            throw new IllegalArgumentException("Empty slide");
        }
        SlideListNode newNode = new SlideListNode(newSlide);
        if(this.cursor == null){             //  empty list
            this.head = newNode;
            this.tail = newNode;
            this.cursor = newNode;
        }
        else if(this.cursor == this.head){        //  cursor at head
            newNode.setNext(cursor);
            this.cursor.setPrev(newNode);
            this.head = newNode;
            this.cursor = newNode;
        }
        else {                          //  cursor anywhere else up to tail
            newNode.setNext(this.cursor);
            newNode.setPrev(this.cursor.getPrev());
            this.cursor.getPrev().setNext(newNode);
            this.cursor.setPrev(newNode);
        }
        this.slideCount++;
    }

    /**
     * Appends a new Slide Node at the end of the current List
     * and increments the slide counter without moving the cursor
     * @param newSlide
     *      The Slide to be wrapped and appended
     * @throws IllegalArgumentException
     *      When the contents of the Slide is empty
     */
    public void appendToTail(Slide newSlide){
        if(newSlide == null){
            throw new IllegalArgumentException("Empty Slide");
        }
        if(this.tail == null){          //  empty list
            this.inertBeforeCursor(newSlide);
        }
        else{                           //  tail is not null
            SlideListNode newNode = new SlideListNode(newSlide);
            this.tail.setNext(newNode);
            newNode.setPrev(this.tail);
            this.tail = newNode;
            this.slideCount++;
        }
    }

    /**
     * Removes the Node that the cursor is pointing to,
     * moves the cursor to its previous element or the head
     * and decrements the slide counter
     * @return
     *      A temporary reference to the removed Node
     * @throws IllegalArgumentException
     *      When the List is empty
     */
    public Slide removeCursor(){
        if(this.cursor == null){        //  not pointing at (an existing) list
            throw new IllegalArgumentException("Empty slideshow");
        }
        SlideListNode temp = this.cursor;
        if(this.head == this.tail){     //  one object in list
            this.head = null;
            this.tail = null;
            this.cursor = null;
        }
        else if(this.cursor == this.head){      //  cursor at head
            this.head = this.cursor.getNext();
            this.cursor.getNext().setPrev(null);
            this.cursor.setNext(null);
            this.cursor = this.head;
        }
        else if(this.cursor == this.tail){      //  cursor at tail
            this.tail = this.cursor.getPrev();
            this.cursor.getPrev().setNext(null);
            this.cursor.setPrev(null);
            this.cursor = this.tail;
        }
        else{       //  anywhere else in an existing list
            this.cursor.getPrev().setNext(this.cursor.getNext());   //  redirect forward link
            this.cursor.getNext().setPrev(this.cursor.getPrev());   //  redirect backward link
            this.cursor.setNext(null);                              //  break forward link
            this.cursor = temp.getPrev();                    //  cursor points at previous node
            temp.setPrev(null);                              //  break backward link
        }
        this.slideCount--;
        return temp.getData();
    }

    /**
     * This method formats the List and its contents into a string
     * @return
     *      A formatted string of the List and its data fields
     */
    public String toString(){
        String printout = "Slideshow Summary:\n" +
                "==============================================\n" +
                String.format("  %-9s%-14s%-11s%-10s\n", "Slide",
                        "Title", "Duration", "Bullets") +
                "----------------------------------------------\n";
        SlideListNode current = head;
        int i = 1;
        while(current != null){
            if(current == this.cursor){
                printout += String.format("%-3s%-8d%-14s%-11s%-10d\n", "->", i,
                        current.getData().getTitle(), current.getData().getDuration(),
                        current.getData().getNumBullets());
            }
            else{
                printout += String.format("%-3s%-8d%-14s%-11s%-10d\n", " ", i,
                        current.getData().getTitle(), current.getData().getDuration(),
                        current.getData().getNumBullets());
            }
            i++;
            current = current.getNext();
        }
        printout += String.format("==============================================\n" +
                "Total: " + this.size() + " slide(s), " +
                String.format("%.1f", this.duration()) +
                " minute(s), " + this.numBullets() + " bullet(s)\n" +
                "==============================================\n");
        return printout;
    }
}