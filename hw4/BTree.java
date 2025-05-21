package cs250.hw4;

import java.io.*;
import java.util.*;

/**
 * BTree Implementation for Homework 4 : CS250 : Amber Ferrell
 * Implements insert, remove, get, findMaxDepth, findMinDepth operations
 * Internal methods were created to maintain the balance of BTree
 *
 
 * 
 */
public class BTree implements TreeStructure {

    private static final int ORDER = 64;
    private Node root;

    /**
     * Internal class for BTree Nodes
     * this holds the keys, children, timestamps 
     * and leaf identity of the nodes and keys are stored/created
     */
    private static class Node {
        ArrayList<Integer> keys = new ArrayList<>(); 
        ArrayList<Node> children = new ArrayList<>();
        ArrayList<Long> timestamps = new ArrayList<>();
        boolean isLeaf = true;

        /**
         * Constructor for Node class
         * @param isLeaf true if node is a leaf node
         */
        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
            keys = new ArrayList<>();
            children = new ArrayList<>();
            timestamps = new ArrayList<>();
        }
    }


    /**
     * Constructor for Btree
     * Initializes empty BTree
     */

    public BTree(){
        root = new Node(true);
    }




        
    /**
     * inserts a number into BTree
     * if root is full split it
     * insert number into node
     * 
     * @param num number to insert
     */

    @Override
    public void insert(Integer num){
        /**
         * insert value into correct position
         * if node is full (63 keys) split into 2 nodes
         * when inserting- store system.nanotime alongside each key
         */

            //if root is full => split -> create new node
        if(root.keys.size() == ORDER -1) {
            Node newRoot = new Node(false); // not a leaf

            // old root becomes 1st child of new root
            newRoot.children.add(root);

            // split old root and reorganize 
            split(newRoot, 0, root);

            // new root becomes root
            root = newRoot;
        }

        // insert the new number into a node that has vacancy
        insertWithVacancy(root, num);
    } 

    /**helper method
     * 
     * 
     * Finds the position to insert/search for a key within a node
     *  -use binary search to find key or where it should be inserted
     * 
     * @param keys list of keys
     * @param num number to search
     * @param return index if found, else insert point is (index + 1)
     */
    private int findPosition(ArrayList<Integer> keys, Integer num) {
        int left = 0;
        int right = keys.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (keys.get(mid).equals(num)){
                return mid;
            }else if (keys.get(mid) < num) {
                left = mid + 1;
            }else{right = mid - 1;
            }
            }
            return -(left+1);
            }



    
    /**helper method
     * 
     * insert into a node that has vacancy
     * find correct child to insert at
     * if child is full split it
     * recurse into subtree
     * 
     * @param node node to insert into
     * @param num number to insert
     */
    private void insertWithVacancy(Node node, Integer num){
            // find correct index to insert the number
            int position = findPosition(node.keys, num); 
            if(position >= 0) return; // this is a duplicate key
            position = -position -1; //  reverses findPosition return -(insertion point + 1) 

            // if its a leaf find where to inser the number in sorted order
            if(node.isLeaf) {
                    // insert the key and its timestamp at the correct position
                   node.keys.add(position, num); // insert into leaf
                   node.timestamps.add(position, System.nanoTime()); // stor timestamp
                    
            }else{ 
                // if its not leaf find child to descend to
                Node child = node.children.get(position);

                // if child is full
                if(child.keys.size() == ORDER -1){
                   split(node, position, child);

                    // if new number is > than the middle key move right one child before recursion step

                   if(num.compareTo(node.keys.get(position)) > 0) {
                    position++;
                   }

                }
                // recursion will allow this to keep going until it is inserted properly
                insertWithVacancy(node.children.get(position), num); // recurse into correct child
                }
    }

    /**helper method
     * splits a full node
     * @param parent parent node
     * @param index index where child exists
     * @param child child node to split
     */
    private void split(Node parent, int index, Node child){
        /**create new sibling node
         * move the right half of keys from full node into sibling
         * promote the middle key up to the parent
         * if the split has children, split children between the nodes too
         * insert new sibling into parents list of children
         */
        Node sibling = new Node(child.isLeaf);
        int middle = (ORDER - 1) / 2; 
        Integer middleKey = child.keys.get(middle);
        Long middleTS = child.timestamps.get(middle);

        /**ugh i dont know how to word this
         * take right half of keys after midle one sublist()
         * make new list of right half (sublist)
         * dump all into new sibling node (addAll)
         */
        sibling.keys.addAll(new ArrayList<>(child.keys.subList(middle + 1, child.keys.size())));
        // same with timestamps! super easy :D
        sibling.timestamps.addAll(new ArrayList<>(child.timestamps.subList(middle + 1, child.timestamps.size())));

        if(!child.isLeaf){
            sibling.children.addAll(new ArrayList<>(child.children.subList(middle + 1, child.children.size())));
            
        }
        child.keys.subList(middle, child.keys.size()).clear();
        child.timestamps.subList(middle, child.timestamps.size()).clear();
        if(!child.isLeaf) {
            child.children.subList(middle + 1, child.children.size()).clear();
        }
        // insert the middle key up to parent
        parent.keys.add(index, middleKey);
        parent.timestamps.add(index, middleTS);
        // insert new sibling node into parent's children 
        parent.children.add(index + 1, sibling);
    }
    

    /**
     * 
     * remove a number from the btree
     * delete number
     * if root becomes empty move root down
     * 
     * 
     * @param num number to remove
     * @return true if succesful false if not found
     */
    @Override
    public Boolean remove(Integer num){
        if(!delete(root, num)) return false;
        if(!root.isLeaf && root.keys.size() == 0) {
            root = root.children.get(0);
        }
        return true;
    }

    /**helper method
     * 
     * delete from a subtree
     * if child has too few keys, prepare it
     * the proceed to delete
     * 
     * 
     * @param node current node
     * @param num number to delete
     * @return true
     */
    private Boolean delete(Node node, Integer num){
        int position = findPosition(node.keys, num);
            if(position >= 0) {
                if(node.isLeaf){
                    node.keys.remove(position);
                    node.timestamps.remove(position);
                    return true;
                }else{
                    return deleteInternal(node, position);} // need helper method !!   : deleteInternalNode
                
                }
                position = -position - 1;
                if(node.isLeaf) return false;
                Node child = node.children.get(position);
                if(child.keys.size() < (ORDER/2)){
                    prepareChild(node, position);        // need helper function!! : prepareChild
                    if(position >= node.children.size()) position--;
                }
                return delete(node.children.get(position), num);
    }


    /**helper method
     * deletes from internal node
     * @param node parent node
     * @param position index of key to delete
     * @return true if successful
     */
    private Boolean deleteInternal(Node node, int position){
        Node predecessor = node.children.get(position);
        if(predecessor.keys.size() >= (ORDER / 2)) {
            
            Integer pKey = getMax(predecessor);
            Long pTimestamp = getTimestamp(predecessor, pKey);
            node.keys.set(position, pKey);
            node.timestamps.set(position, pTimestamp);
            delete(predecessor, pKey);
            return true;
        }
        
         Node successor = node.children.get(position + 1);
        if(successor.keys.size() >= (ORDER / 2)) {
            
            Integer sKey = getMin(successor);
            Long sTimestamp = getTimestamp(successor, sKey);
            node.keys.set(position, sKey);
            node.timestamps.set(position, sTimestamp);
            delete(successor, sKey);
            return true;
    }
        Integer deleteKey = node.keys.get(position);
        merge(node, position);
        return delete(predecessor, deleteKey);
    }

    /**helper method
     * 
     * @param node
     * return minimum
     */
    private Integer getMin(Node node){
        while(!node.isLeaf) {
            node = node.children.get(0);
        }
        return node.keys.get(0);
    }

    /**helper method
     * 
     * @param node
     * return maximum
     */
    private Integer getMax(Node node){
        while(!node.isLeaf) {
            node = node.children.get(node.children.size() -1);
        }
        return node.keys.get(node.keys.size() - 1);
    }
    /**helper method
     * helper method
     * @param node
     * @param key
     * @return Long timestamp for the node,key
     */
    private Long getTimestamp(Node node, Integer key) {
        int position = node.keys.indexOf(key);
        return position >= 0 ? node.timestamps.get(position) : -1L;
    }

    /**helper method
     * child with not enough keys
     *  borrow from siblings or merge (other helper method)
         * if position > 0 and parents children(position -1)key size >= order/2 the borrow(parent, position) 
         * will need another helper function!!
         * else if pos < paret childrent size - 1 && parent children get(pos -1)keys suze >= order/2
         * else
         * if position < parentchildrensize -1 merge parent at position
         * else merge parent position -1
         
     * @param parent
     * @param position
     */

    private void prepareChild(Node parent, int position){ 

       
        if (position > 0 && parent.children.get(position-1).keys.size() >= (ORDER/2)) {
             borrow(parent, position);
        }else if (position < parent.children.size() -1 && parent.children.get(position + 1 ).keys.size() >= (ORDER/2)){
            borrowFromOther(parent, position);
        }else{
            if(position < parent.children.size()-1) {
                merge(parent, position);
            }else{
                merge(parent, position - 1);
            }
        }
    }

    /**helper method
     * 
     * borrow from last sibling
     * get child and sibling at position
     * add parent keys
     * add timestamps
     * 
     * if child is not a leaf the ad the sibling children as child children and remove the sibling children
     * set parent keys to sibling keys and remove them
     * set parent timestamps to sibling timestamps and remove them
     * 
     * @param parent node that needs to borrow
     * @param position index of child needing to borrow
     */
    private void borrow(Node parent, int position){
        Node child = parent.children.get(position);
        Node sibling = parent.children.get(position -1);

        child.keys.add(0, parent.keys.get(position -1));
        child.timestamps.add(0, parent.timestamps.get(position -1));
        
        if(!child.isLeaf)
        {child.children.add(0, sibling.children.remove(sibling.children.size() -1));}

        parent.keys.set(position -1, sibling.keys.remove(sibling.keys.size() -1));
        parent.timestamps.set(position -1,sibling.timestamps.remove(sibling.timestamps.size() -1));
    }

    /**helper method
     * borrow from the right side
     * 
     * @param parent
     * @param position
     */
    private void borrowFromOther(Node parent, int position){ 
        Node child = parent.children.get(position);
        Node sibling = parent.children.get(position + 1);

        child.keys.add(parent.keys.get(position));
        child.timestamps.add(parent.timestamps.get(position));
        
        if(!child.isLeaf)
        {child.children.add(sibling.children.remove(0));}

        parent.keys.set(position, sibling.keys.remove(0));
        parent.timestamps.set(position, sibling.timestamps.remove(0));

        // postion + 1 this time
    }

    /**helper method
     * 
     * merges a child with next sibling
     *  move parents separating key down to child
     *  move all key and all childrent from sibling to child
     * 
     * @param parent
     * @param position
     */
    private void merge(Node parent, int position){ 
        Node child = parent.children.get(position);
        Node sibling = parent.children.get(position + 1);

        child.keys.add(parent.keys.remove(position));
        child.timestamps.add(parent.timestamps.remove(position));
        
        child.keys.addAll(sibling.keys);
        child.timestamps.addAll(sibling.timestamps);

        if(!child.isLeaf)
        {child.children.addAll(sibling.children);}

        parent.children.remove(position + 1 );

    }

    /**
     * 
     * Search for a number in the BTree and return timestamp
     *      use search for keys
     *          if found return timestamp
     *          if not recurse into children
     * @param num
     * return timestamp if found, -1 if not
     */
    @Override 
    public Long get(Integer num){
        //find number and return timestamp when it was inserted
        /**start at root
         * search within current node
         * if not found follow correct child pointer
         * repeat until found or null leaf
         */

        return search(root, num);
    }

    /**helper method - search inside a node
     * search starting at root
     * find position 
     * if the position is >= 0 return timestamps
     * 
     * @param node
     * @param num
     * @return timestamp
     */
    private Long search(Node node, Integer num){
        int position = findPosition(node.keys, num);
        if(position >= 0){
            return node.timestamps.get(position);
        }
        position = -position - 1;
        if(node.isLeaf) return -1L;
        return search(node.children.get(position), num);
    
    }


    /**
     * find maximum depth of the BTree
     *  follow rightmost child until reaching a leaf
     * 
     * @return maximum depth (root is depth 0!)
     */
    @Override 
    public Integer findMaxDepth() {

        /**traverse tree from root to leaf
         * track edges
         * maxdepth = deepest leaf path 
         */
        int depth = 0;
        Node node = root;
        while(!node.isLeaf) {
            node = node.children.get(node.children.size() -1);
            depth++;
        }


        return depth;
    }

    /**
     * find minimum depth of BTree
     * follow leftmost child til leaf
     * @return minimum depth
     */
    @Override 
    public Integer findMinDepth() {
        /**same but find lowest leaf depth */
        int depth = 0;
        Node node = root;
        while(!node.isLeaf){node = node.children.get(0); depth++;
        }
        return depth;
    }


    public static void main(String[] args) throws FileNotFoundException, IOException { 

        File file = new File(args[0]); 
        FileReader fReader = new FileReader(file); 

        BufferedReader bufferedReader = new BufferedReader(fReader); 
        TreeStructure tree = new BTree(); 
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

        //TODO implement your own test cases.


    } 
}
