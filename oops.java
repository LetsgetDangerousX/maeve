package cs250.hw4;

import java.io.*;
import java.util.*;

public class BinaryTree implements TreeStructure {

    private class Node {
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

    public BinaryTree(){
        //TODO
        private Node root;

    }

    public void insert(Integer num){
        //TODO
        
    } 

    public Boolean remove(Integer num){
        //TODO
        return false;
    }

    public Long get(Integer num){
        //TODO
        return -1L;
    }

    public Integer findMaxDepth() {
        //TODO
        return -1;
    }

    public Integer findMinDepth() {
        //TODO
        return -1;
    }

}