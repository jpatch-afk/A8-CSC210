import java.io.*;
import java.util.*;


public class AnimalGuess {


    public static boolean getUserAnswer(String prompt, Scanner input){
        while(true){
            System.out.println(prompt + ": yes or no?");
            String answer = input.nextLine().toLowerCase().trim();
            if(answer == "y" || answer == "yes"){
                return true;
            }
            else if(answer == "n" || answer == "no"){
                return false;
            }
            else {
                System.out.println("I do not understand, try using either yes or no.");
            }
        }
    }



        @SuppressWarnings(value = { "", "unused" })
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String filename = "AnimalTree.txt";

        DecisionTree tree = null;

        //keeps track if the user went left 
        boolean correct = false;

        try {
            File file = new File(filename);
            if(file.exists()){
                tree = DecisionTree.read(filename);
            }
            else {
                //create a starting tree, so the game doesn't end
                 tree = new DecisionTree("Cat");
            }
        } catch (Exception e) {
            System.out.println("Exception found: " + e);
        }

        boolean playGame = true;

        //Playing the game
        while(playGame) {
            System.out.println("Think of an animal and I'll try to guess!");

            //create a copy of tree to help it learn if need be
            DecisionTree copy = tree;
            //create a new parent for the expanding tree
            DecisionTree parent = null;

            //Transverses through the tree until we reach a leaf
            if(copy.getLeft() != null || copy.getRight() != null){
             boolean answer = getUserAnswer(copy.getData(), input);
            if(answer){
                copy.getLeft();
            }
            else {
                copy.getRight();
            }
        }

        //After finding the leaf
        boolean guess = getUserAnswer("Is your animal " + copy.getData() + "?", input);
        if(guess){
            System.out.println("Yay! I guessed your animal :)");
        }
        else {
            System.out.println("Oh no, I got it wrong. Help me get better at guessing?");
            System.out.println("What was your animal?");

            //Creates a new animal from the user 
            String newAnimal = input.nextLine().trim();
            //Creates a new node for this animal
            DecisionTree animalNode = new DecisionTree(newAnimal);

            System.out.println("Write a yes or no question that would differentiate between a " + newAnimal + " and a " + copy.getData());
            //Creates a new question and a new node for this question
            String newQuestion = input.nextLine().trim();
            DecisionTree questionNode = new DecisionTree(newQuestion);

            boolean yesQuestion = getUserAnswer("Would you answer yes for " + newAnimal + " to the question you just wrote?", input);

            if(yesQuestion){
                questionNode.setLeft(animalNode);
                questionNode.setRight(copy);
            }
            else{
                questionNode.setLeft(copy);
                questionNode.setRight(animalNode);
            }

            //Attaches to the parent or creates new node from the question
            if(parent == null){
                tree = questionNode;
            }
            else {
                if(correct){
                    parent.setLeft(questionNode);
                }
                else {
                    parent.setRight(questionNode);
                }
            }   
        }

        playGame = getUserAnswer("Play again?", input);

        }

        //Write tree and rewrite the file 

        try {
            tree.write(filename);
        } catch (IOException e) {
            System.out.println("Exception found: " + e.getMessage());
        }
    
        input.close();

    }
}