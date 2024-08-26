package hw2;

/**
 * This class creates a Node which wraps the contents of a Slide object
 * and includes a reference to the next or previous Node
 * @author Jason Chen
 *      jason.chen.5@stonybrook.edu
 *      112515450
 *      Recitation 4
 *      Homework 2
 */
public class SlideListNode {
    private Slide data;
    private SlideListNode next;
    private SlideListNode prev;

    /**
     * This Node constructor wraps the specified Slide object
     * @param initData
     *      The Slide to be wrapped
     */
    public SlideListNode(Slide initData){
        if(initData == null){
            throw new IllegalArgumentException("Error: initData is null from SlideListNode()");
        }
        this.data = initData;
        this.next = null;
        this.prev = null;
    }

    /**
     * @return
     *      The Slide that is wrapped
     */
    public Slide getData(){
        return this.data;
    }

    /**
     * Sets the new Slide to be wrapped
     * @param newData
     *      The new Slide to be wrapped
     */
    public void setData(Slide newData){
        if(newData == null){
            throw new IllegalArgumentException("Error: newData is null from setData()");
        }
        this.data = newData;
    }

    /**
     * @return
     *      Reference to the next Node
     */
    public SlideListNode getNext(){
        return this.next;
    }

    /**
     * Sets the reference to the next Node
     * @param newNext
     *      Reference to the next Node
     */
    public void setNext(SlideListNode newNext){
        this.next = newNext;
    }

    /**
     * @return
     *      Reference to the previous Node
     */
    public SlideListNode getPrev(){
        return this.prev;
    }

    /**
     * Sets the reference to the previous Node
     * @param newPrev
     *      Reference to the previous Node
     */
    public void setPrev(SlideListNode newPrev){
        this.prev = newPrev;
    }
}