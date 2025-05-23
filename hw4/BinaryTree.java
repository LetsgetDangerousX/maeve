package cs250.hw4;

import java.io.*;
import java.util.*;



public class BinaryTree implements TreeStructure {

    private class Node  {
        int value;
        long timestamp;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            this.timestamp = System.nanoTime();
            this.left = null;
            this.right = null;
        }
    }
    private Node root;
    public BinaryTree(){
   
    }


    /**
     * @param num number to insert
     *  this method will assign root to a recursive method
     *  insertion starts @ root and the result of recursion
     *  will ensure that the root updates correctly 
     */
    public void insert(Integer num){
       //helper method will reduce clutter
        root = recursiveInsert(root,num);
    } 

    /**helper method
     *  checks if the spot is empty
     * @param current the current node in the binary search to start with
     * @param num value to insert into tree
     * @return current node- representing subtree after insertion completes
     */
    private Node recursiveInsert(Node current, int num) {
        if (current == null) {return new Node(num);}

        if (num < current.value) {
            current.left = recursiveInsert(current.left, num);
        }else if (num > current.value){
            current.right = recursiveInsert(current.right, num);
        }
        return current;
    }

    /**
     * removes number from bst
     * if found will be removed
     *  if no children -> remove
     *  if one child -> replace node with child
     *  if 2 chldren -> replace node with minimum value, false if not found
     * @param num
     * @return true if successful
     */
    public Boolean remove(Integer num){
        // needs 2 helper functions to find the integer to see if it exists & another to actually remove starting at current node
        // node has 1 child replace it with its only child, no child just delete it, 2 children replace with smallest value in right subtree
        // this will check if empty or num is missing otherwise it calls recursive function
        if(root == null) {return false;} // empty
        if(find(num) == null){return false;} // nonexistent
        root = recursiveRemove(root, num); 
        return true;
        }



    /**helper method 
     * removes node from tree
     * method will recursively find node to remove 
     * houses remove ops based on # of children
     * @param current current node to review
     * @param num value of node to remove
     * @return node after removal
     */
    private Node recursiveRemove(Node current, int num) {
            if(current == null) {return null;}

            //less go left
            if(num < current.value) {
                current.left = recursiveRemove(current.left, num);
            
            // more go right    
            }else if (num > current.value) {
                current.right = recursiveRemove(current.right, num);
            
            // if current == num then we need to remove and decide what happens to children    
            }else{

                // no child - just remove
                if(current.left == null && current.right == null){
                    return null; // set the found node as null
                }

                // one child not left- promote the one right child
                if(current.left == null) {
                    return current.right; 
                }
                // one child not right-  promote the one left child
                if(current.right == null) {
                    return current.left;
                }

                // two children - needs helper method to find minimum value successor
                Node successor = findMin(current.right);

                // copy successor and timestamp to current node
                current.value = successor.value;
                current.timestamp = successor.timestamp;

                // delete successor node
                current.right = recursiveRemove(current.right, successor.value);
            }
            return current;
    }

    /**helper method
     * finds minimum node left most+
     * 
     * @param node - starting point
     * @return node with smallest vvalue in subtree
     */
    private Node findMin(Node node){
        while(node.left != null) {
            node = node.left;
        }
        return node;
    }

    /***helper function
     * finds node by value in bst
     * method traverses tree to find node with specified value
     * @param num - number to find
     * @return found value or null
     */
    private Node find(int num) {
        Node current = root;
        while(current != null) {
            if (num == current.value) {
                return current;
            }
            if(num < current.value) {
                current = current.left;
            }else {
                current = current.right;
            }
        }
        return null;
    }


    
    /**retrieves timestamp of node in binary search tree
     * timestamp is time when node was inserted
     * @param num the value of node to retrieve timestamp for
     * return timestamp of node if found, if not-null
     */
    public Long get(Integer num){
        //TODO
        /**start at root
         * compare num to current 
         *      if equal - return timestamp
         *       smaller - go left
         *        bigger - go right
         * loop until found 
         */
        Node current = root;
        while(current != null) {
            if (num.equals(current.value)) { // num is Integer object not int primitive
                return current.timestamp;
            }else if (num < current.value) {
                current = current.left;
            }else{current = current.right;}
        }
        return -1L;
    }


    /**uses recursive method to find the longest path from root to leaf
     * @return max depth of tree
     */
    public Integer findMaxDepth() {
    /*TODO
        find deepest leaf node from root
         return number of edges to get there
         empty tree is depth 0
        recursion will be more efficient - helper method?*/
        
        return recursiveMaxDepth(root);
    }

    /**helper recrusive method for find max depth 
     * @return maximum depth of tree from current node
     */
    private int recursiveMaxDepth(Node node){
            if (node == null) {return 0;} // emptry tree
            int leftDepth = recursiveMaxDepth(node.left);
            int rightDepth = recursiveMaxDepth(node.right);
            return Math.max(leftDepth, rightDepth) + 1; // includes current node edge by adding one
    }

    /**finds minimum depth of tree - shortest path
     * depth: # of edges from root to leaf
     * uses recursive method
     * a tree with no nodes has depth 0
     * 
     * @return minimum depth of tree from current node
     * with use of helper method
     */
    public Integer findMinDepth() {
        // find the shortest route to a leaf
        // almost exactly like the maxdepth structure
        //TODO
        return recursiveMinDepth(root);
    }


    /**helper method
     * @param node current node to start depth calc
     * @return minimum depth of tree from current node
     */
    private int recursiveMinDepth(Node node){
        if (node == null) {return 0;} //empty tree
        
        // if the node only has right subtree
        if (node.left == null) {
            return recursiveMinDepth(node.right) + 1; 
        }
        // if the node only has left subtree
        if (node.right == null) {
            return recursiveMinDepth(node.left) + 1; 
        }
        // both children exist
        return Math.min(recursiveMinDepth(node.left), recursiveMinDepth(node.right)) +1;
    }


    public static void main(String[] args) throws FileNotFoundException, IOException { 

        File file = new File(args[0]); 
        FileReader fReader = new FileReader(file); 
        BufferedReader bufferedReader = new BufferedReader(fReader); 
        TreeStructure tree = new BinaryTree(); 
        Random rng = new Random(42); 
        ArrayList<Integer> nodesToRemove = new ArrayList<>(); 
        ArrayList<Integer> nodesToGet = new ArrayList<>(); 
        String line = bufferedReader.readLine(); 

        while (line != null) { 
            Integer lineInt = Integer.parseInt(line); 
            tree.insert(lineInt); 
            Integer rand = rng.nextInt(10); 
            if (rand < 5) nodesToRemove.add(lineInt); 
            else if (rand >= 5) nodesToGet.add(lineInt); 
            line = bufferedReader.readLine(); 
        } 

        bufferedReader.close(); 
        for (int i = 0; i < 10; i++) { 
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i))); 
        } 

        System.out.println("Max depth: " + tree.findMaxDepth()); 
        System.out.println("Min depth: " + tree.findMinDepth()); 
        for (Integer num : nodesToRemove) { 
            tree.remove(num); 
        } 

        for (int i = 0; i < 10; i++) { 
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i))); 
        } 

        System.out.println("Max depth: " + tree.findMaxDepth()); 
        System.out.println("Min depth: " + tree.findMinDepth()); 

        // I tried really hard to make this one my best of the course since it was the last one- crossing fingers

    }

}