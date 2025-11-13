import java.io.*;
import java.util.*;

public class DecisionTree extends BinaryTree<String>{

    public DecisionTree(String e) {
        super(e);    
    }

    public DecisionTree(String data, DecisionTree left, DecisionTree right){
        super(data, left, right);
    }

    public void setLeft(DecisionTree left){
        if(left instanceof DecisionTree){
            super.setLeft(left);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    public void setRight(DecisionTree right){
        super.setRight(right);
    }

    public DecisionTree getLeft(){
        return (DecisionTree) super.getLeft();
    }

    public DecisionTree getRight(){
        return (DecisionTree) super.getRight();
    }


    public DecisionTree followPath(String input){

        String[] path = input.split("");
        DecisionTree tree = this; 

        for(String s: path){
            if(s == ""){
                return tree;
            }
            else if(s == "Y"){
                if(tree.getLeft() != null) {
                    tree = (DecisionTree) tree.getLeft();
                }
            }
            else if(s == "N"){
                if(tree.getRight() != null) {
                    tree = (DecisionTree) tree.getRight();
                }
            }
            else {
                throw new IllegalArgumentException("Invalid Input: " + s);
            }
            if(tree == null){
                throw new IllegalArgumentException("Invalid Input. Node doesn't exist.");
            }
        }
        return tree;
    }

    public void write(String filename) throws IOException{
        PrintWriter print = new PrintWriter(new FileWriter(filename));

        //Keeps track of nodes
        Queue<DecisionTree> nodes = new LinkedList<>();

        //Keeps track of the paths
        Queue<String> paths = new LinkedList<>();

        //Initiate the queues 
        nodes.add(this);
        paths.add("");

        //while nodes is not empty
        while(!nodes.isEmpty()){

            //takes the first node from the queues
            DecisionTree first = nodes.remove();
            String path = paths.remove();
            print.println(path + "" + first.getData());

            //Goes to the first node and adds its children to the queue
            if(first.getLeft() != null){
                nodes.add(first.getLeft());
                paths.add(path + "Y"); //add a yes because there exists a left child 
            }
            if(first.getRight() != null){
                nodes.add(first.getRight());
                paths.add(path + "N"); //add a no because there exists a right child 
            }
        }
        print.close(); //closes PrintWriter
    }

    public static DecisionTree read(String filename) throws IOException {
        Scanner scanner = new Scanner(new File(filename));
        DecisionTree tree = null; //initializes the empty Decision Tree to write the file on 

        while(scanner.hasNextLine()){
            String temp = scanner.nextLine();
            // Split the line at the first space character of the path string
            int index = temp.indexOf(' ');
            String path = temp.substring(0, index);
            String data = temp.substring(index + 1);
      
            // If no path exists, new node becomes root
            if (path.equals("")) {
                tree = new DecisionTree(data);
            } 
            else {
                DecisionTree parent = tree.followPath(path.substring(0, path.length() - 1));
                char last = path.charAt(path.length() - 1);
                DecisionTree node = new DecisionTree(data);

                if (last == 'Y') {
                    parent.setLeft(node);
                } else {
                    parent.setRight(node);
                }
            }
        }
      
        scanner.close();
        return tree;
    }









}