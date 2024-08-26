package hw2;

/**
 * This class creates a Slide object with a final int MAX_BULLETS,
 * double duration, String title, and String array bullets
 * @author Jason Chen
 *      jason.chen.5@stonybrook.edu
 *      112515450
 *      Recitation 4
 *      Homework 2
 */
public class Slide {
    public static final int MAX_BULLETS = 5;
    private String title;
    private String[] bullets = new String[MAX_BULLETS];
    private double duration;

    /**
     * Custom Slide constructor
     * @param newTitle
     *      The new title for the Slide
     * @param newBullets
     *      The new list of bullets for the Slide
     * @param newDuration
     *      The new duration for the Slide
     */
    public Slide(String newTitle, String[] newBullets, double newDuration){
        this.title = newTitle;
        this.bullets = newBullets;
        this.duration = newDuration;
    }

    /**
     * Default Slide constructor, sets initial values to null or 0 or an array of null
     */
    public Slide(){
        this(null, new String[MAX_BULLETS], 0.0);
    }

    /**
     * @return
     *      Returns the title of the Slide
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Sets the new title of the Slide
     * @param newTitle
     *      The new title of the current title
     * @throws IllegalArgumentException
     *      When new title is null
     */
    public void setTitle(String newTitle){
        if(newTitle == null){
            throw new IllegalArgumentException("Invalid entry");
        }
        this.title = newTitle;
    }

    /**
     * @return
     *      The duration of the Slide
     */
    public double getDuration(){
        return this.duration;
    }

    /**
     * Sets the new duration of the Slide
     * @param newDuration
     *      The new duration of the Slide
     * @throws IllegalArgumentException
     *      When duration is <= 0
     */
    public void setDuration(double newDuration){
        if(newDuration <= 0.0){
            throw new IllegalArgumentException("Invalid duration");
        }
        this.duration = newDuration;
    }

    /**
     * @return
     *      The number of bullets in the Slide
     */
    public int getNumBullets(){
        int numBullets = 1;
        while(this.getBullet(numBullets) != null){
            numBullets++;
            if(numBullets > 5){
                break;
            }
        }
        return numBullets - 1;
    }

    /**
     * This method retrieves the contents of a selected bullet from the Slide
     * @param i
     *      The index of the desired bullet (index starting from 1)
     * @return
     *      The contents of the bullet number
     * @throws IllegalArgumentException
     *      When the index is not in valid range (starting from 1)
     */
    public String getBullet(int i){     // 1 <= i <= MAX_BULLETS
        if(i < 1 || i > MAX_BULLETS){
            throw new IllegalArgumentException("Invalid Index");
        }
        return this.bullets[i - 1];      // may return null
    }

    /**
     * This method sets the contents of a selected bullet in the current Slide
     * @param bullet
     *      The new contents of the bullet
     * @param i
     *      The index of the desired bullet (starting from 1)
     * @throws IllegalArgumentException
     *      When the index is not in valid range (starting from 1)
     */
    public void setBullets(String bullet, int i){       // null string erases bullet and moves all bullets up
        if(i < 1 || i > MAX_BULLETS){
            throw new IllegalArgumentException("Invalid index");
        }
        if(bullet == null){
            for(int n = 0; n < getNumBullets() - i; n++){
                this.bullets[n + i - 1] = this.bullets[n + i];
            }
            this.bullets[getNumBullets() - 1] = null;
        }
        else{
            this.bullets[i - 1] = bullet;
        }
    }

    /**
     * @return
     *      A formatted string of the Slide and its data fields neatly
     */
    public String toString(){
        String printout = "==============================================\n" +
                "\t" + this.getTitle() + "\n" +
                "==============================================\n";
        for(int i = 1; i < this.getNumBullets() + 1; i++){
            printout += String.format("%2d. ", i) + this.getBullet(i) + "\n";
        }
        return printout;
    }
}