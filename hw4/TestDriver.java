package cs250.hw4;

import java.util.ArrayList;
import java.util.Random;

public class TestDriver {
    // This is by no means an exhaustive test, but rather is intended to confirm you will be compatible with the autograder.
    public static void main(String args[]){
        TreeStructure btree = new BTree();
        TreeStructure bst = new BinaryTree();
        
        TreeStructure[] trees = new TreeStructure[2];
        trees[0] = bst;
        trees[1] = btree;

        for (TreeStructure tree : trees){

            System.out.print("Starting test with ");
            if (tree instanceof BinaryTree){
                System.out.println("Binary Tree");
            }
            if (tree instanceof BTree){
                System.out.println("Btree");
            }

            System.out.println("Depth before adding anything:");
            System.out.println("Max depth: " + tree.findMaxDepth()); 
            System.out.println("Min depth: " + tree.findMinDepth()); 

            System.out.println("Adding some numbers...");
            tree.insert(10);
            tree.insert(5);
            tree.insert(8);
            tree.insert(13);
             tree.insert(14);
            tree.insert(55);
            tree.insert(85);
            tree.insert(152);
             tree.insert(150);
            tree.insert(555);
            tree.insert(85);
            tree.insert(124);

            System.out.println("Depth after adding some numbers:");
            System.out.println("Max depth: " + tree.findMaxDepth()); 
            System.out.println("Min depth: " + tree.findMinDepth()); 

            
            System.out.println("8 has timestamp " + tree.get(8));
            boolean removed = tree.remove(5);
            System.out.println("Remove a few numbers and check timestamp again:");
            System.out.println("5 was removed: "+removed);
            removed = tree.remove(10);
            System.out.println("10 was removed: "+removed);
            System.out.println("8 has timestamp " + tree.get(8));

            System.out.println("Depth after some numbers were removed:");
            System.out.println("Max depth: " + tree.findMaxDepth()); 
            System.out.println("Min depth: " + tree.findMinDepth()); 
            System.out.println();
        }
    }
}