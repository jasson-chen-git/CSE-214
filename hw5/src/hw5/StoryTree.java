package hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class StoryTree {
    private StoryTreeNode root;
    private StoryTreeNode cursor;
    private GameState state;
    public StoryTree(){
        root = new StoryTreeNode();
        cursor = root;
        state = null;
    }
    public static StoryTree readTree(String filename) throws FileNotFoundException, DataFormatException, NodeNotPresentException {
        if(filename == null || filename == "") {
            throw new IllegalArgumentException("Invalid file name.");
        }
        Scanner reader = new Scanner(new File(filename));
        StoryTree tree = new StoryTree();
        while(reader.hasNextLine()){
            String[] line = read_line(reader.nextLine());
            StoryTreeNode temp = new StoryTreeNode(line[0], line[1], line[2]);
            tree.readTree_helper(temp);
        }
        reader.close();
        return tree;
    }
    private void readTree_helper(StoryTreeNode node) throws DataFormatException, NodeNotPresentException {
        String[] parse = read_positions(node.getPosition());
        int i;
        resetCursor();
        for(i = 0; i < parse.length - 1; i++){
            selectChild(parse[i]);
        }
        if(parse[i].compareTo("1") == 0){
            cursor.setLeftChild(node);
        }
        else if(parse[i].compareTo("2") == 0){
            cursor.setMiddleChild(node);
        }
        else{
            cursor.setRightChild(node);
        }
    }
    private static String[] read_line(String line) throws DataFormatException {
        String parse[] = line.split("\\|", 0);
        if(parse.length != 3){
            throw new DataFormatException("Invalid Line.");
        }
        for(int i = 0; i < parse.length; i++){
            parse[i] = parse[i].trim();
        }
        return parse;
    }
    private static String[] read_positions(String position) throws DataFormatException {
        String[] parse = position.split("-", 0);
        for(String s : parse){
            if(!s.contains("1") && !s.contains("2") && !s.contains("3")){
                throw new DataFormatException("Invalid Position.");
            }
        }
        return parse;
    }
    public static void saveTree(String filename, StoryTree tree) throws FileNotFoundException {
        if(filename == null || filename == "") {
            throw new IllegalArgumentException("Invalid file name.");
        }
        PrintWriter writer = new PrintWriter(filename);
        tree.resetCursor();
        tree.writeFile(writer);
        writer.close();
    }
    public GameState getGameState() {
        return state;
    }
    public String getCursorPosition() {
        return cursor.getPosition();
    }
    public String getCursorMessage() {
        return cursor.getMessage();
    }
    public String[][] getOptions() {
        String[][] paths = new String[cursor.getNumChildren()][2];
        for(int i = 0; i < paths.length; i++){
            paths[i] = new String[] {cursor.getPosition(), cursor.getOption()};
        }
        return paths;
    }
    public void setCursorMessage(String message) {
        cursor.setMessage(message);
    }
    public void setCursorOption(String option) {
        cursor.setOption(option);
    }
    public void resetCursor() {
        cursor = root;
    }
    public void selectChild(String position) throws IllegalArgumentException, DataFormatException, NodeNotPresentException {
        if(position == null || position == "" || Integer.valueOf(position) < 0){
            throw new IllegalArgumentException("Invalid position.");
        }
        if(cursor.isLeaf() || Integer.valueOf(position) > cursor.getNumChildren()){
            throw new NodeNotPresentException("Child node does not exist.");
        }
        if(position.compareTo("1") == 0){
            cursor = cursor.getLeftChild();
        }
        else if(position.compareTo("2") == 0){
            cursor = cursor.getMiddleChild();
        }
        else{
            cursor = cursor.getRightChild();
        }
    }
    public void returnToParent() {
        if(cursor != root || cursor.getParent() != root){
            throw new IllegalArgumentException("Return not allowed.");
        }
        cursor = cursor.getParent();
    }
    public void addChild(String option, String message) throws FullTreeException {
        if(cursor.isFull()){
            throw new FullTreeException("Subtree is full.");
        }
        String insertPosition = String.format("&d", cursor.getNumChildren() + 1);
        String newPosition = cursor.getPosition() + "-" + insertPosition;
        StoryTreeNode newChild = new StoryTreeNode(newPosition, option, message);
        newChild.setParent(cursor);
        if(insertPosition.compareTo("1") == 0){
            cursor.setLeftChild(newChild);
        }
        else if(insertPosition.compareTo("2") == 0){
            cursor.setMiddleChild(newChild);
        }
        else{
            cursor.setRightChild(newChild);
        }
    }
    public StoryTreeNode removeChild(String position) {
        if(Integer.valueOf(position) > cursor.getNumChildren()){
            throw new IllegalArgumentException("Invalid child position");
        }
        StoryTreeNode temp;
        if(position.compareTo("1") == 0){
            temp = cursor.getLeftChild();
            if(cursor.getMiddleChild() != null) {
                cursor.setLeftChild(cursor.getMiddleChild());
            }
            if(cursor.getRightChild() != null) {
                cursor.setMiddleChild(cursor.getRightChild());
            }
        }
        else if(position.compareTo("2") == 0){
            temp = cursor.getMiddleChild();
            if(cursor.getRightChild() != null) {
                cursor.setMiddleChild(cursor.getRightChild());
            }
        }
        else{
            temp = cursor.getRightChild();
        }
        cursor.setRightChild(null);
        return temp;
    }
    public String toStringPlay() {
        String printout = cursor.getMessage() + "\n";
        if(cursor.getLeftChild() != null){
            printout += "1) " + cursor.getLeftChild().getOption();
        }
        if(cursor.getMiddleChild() != null){
            printout += "2 " + cursor.getMiddleChild().getOption();
        }
        if(cursor.getRightChild() != null){
            printout += "3) " + cursor.getRightChild().getOption();
        }
        return printout;
    }
    public String view() {
        return "Position: " + cursor.getPosition() +
                "\nOption: " + cursor.getOption() +
                "\nMessage: " + cursor.getMessage();
    }
    public void printTree(){
        root.getLeftChild().preorder();
    }
    public void writeFile(PrintWriter writer){
        root.getLeftChild().preorderSave(writer);
    }
}
