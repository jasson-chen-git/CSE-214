package hw5;

import java.io.PrintWriter;

public class StoryTreeNode {
    private static final String WIN_MESSAGE = "YOU WIN", LOSE_MESSAGE = "YOU LOSE";
    private String position, option, message;
    private StoryTreeNode leftChild, middleChild, rightChild, parent;
    public StoryTreeNode(String position, String option, String message){
        setPosition(position); setOption(option); setMessage(message);
        setLeftChild(null); setMiddleChild(null); setRightChild(null);
        setParent(null);
    }
    public StoryTreeNode(){     //  special use case
        this("root", "root", "Hello, and Welcome to Zork!.");
    }
    public boolean isLeaf(){
        return leftChild == null && middleChild == null && rightChild == null;
    }
    public boolean isWinningNode(){
        return isLeaf() && message.contains(WIN_MESSAGE);
    }
    public boolean isLosingNode(){
        return isLeaf() && message.contains(LOSE_MESSAGE);
    }
    public String getPosition() {
        return position;
    }
    public String getOption() {
        return option;
    }
    public String getMessage() {
        return message;
    }
    public StoryTreeNode getLeftChild() {
        return leftChild;
    }
    public StoryTreeNode getMiddleChild() {
        return middleChild;
    }
    public StoryTreeNode getRightChild() {
        return rightChild;
    }
    public StoryTreeNode getParent() {
        return parent;
    }
    public void setPosition(String position) {
        this.position  = position;
    }
    public void setOption(String option) {
        this.option = option;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setLeftChild(StoryTreeNode newChild) {
        this.leftChild = newChild;
    }
    public void setMiddleChild(StoryTreeNode newChild) {
        this.middleChild = newChild;
    }
    public void setRightChild(StoryTreeNode newChild) {
        this.rightChild = newChild;
    }
    public void setParent(StoryTreeNode parent) {
        this.parent = parent;
    }
    public int getNumChildren() {
        if(rightChild != null) {
            return 3;
        }
        else if(middleChild != null) {
            return 2;
        }
        else if(leftChild != null) {
            return 1;
        }
        else{
            return 0;
        }
    }
    public boolean isFull(){
        return getNumChildren() == 3;
    }
    public void preorder() {
        System.out.println(this);
        if(leftChild != null){
            leftChild.preorder();
        }
        if(middleChild != null){
            middleChild.preorder();
        }
        if(rightChild != null){
            rightChild.preorder();
        }
    }
    public void preorderSave(PrintWriter writer) {
        writer.write(toString());
        if(leftChild != null){
            writer.write("\n");
            leftChild.preorderSave(writer);
        }
        if(middleChild != null){
            writer.write("\n");
            middleChild.preorderSave(writer);
        }
        if(rightChild != null){
            writer.write("\n");
            rightChild.preorderSave(writer);
        }
    }
    public String toString() {
        return position + " | " + option + " | " + message;
    }
}