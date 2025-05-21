package cs250Past.hw3;

import java.io.*;    
import java.net.*;
import java.util.Random;
import java.io.IOException;


public class TCPClient {
    static DataInputStream din;
    static DataOutputStream dout;
    static Socket clientSocket;

    public static int receiveNum(){
        try { 
            return din.readInt();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return -1; // if an incorrect value is read, the EXIT_NUM will be returned
    }

    public static void sendNumber(int numToSend){
        try {
            dout.writeInt(numToSend); // Writes an int to the output stream
            dout.flush(); // By flushing the stream, it means to clear the stream of any element that may be or maybe not inside the stream
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    
    // Below method cleans up all of the connections by closing them and then exiting. 
    // This prevents a lot of problems, so its good practice to always make sure the connections close. 

    public static void cleanUp(){
        try {
            clientSocket.close();
            dout.close();
            din.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args){
        final String server_ip = args[0]; 
        final int port =  Integer.parseInt(args[1]);
        

        try{

            // Initialize Necessary Objects
            clientSocket = new Socket(server_ip, port); // Establishes a connection to the server
            dout = new DataOutputStream(clientSocket.getOutputStream()); // Instantiates out so we can then use it to send data to the client
            din = new DataInputStream(clientSocket.getInputStream()); // Instantiates in so we can then use it to receive data from the client
            
            //Receive config
            int numMsg = receiveNum();
            int seed = receiveNum();

            // ouput 
            System.out.println("Received config");
            System.out.println("number of messages = " + numMsg);
            System.out.println("seed = " + seed);

           
            //  random number generator to produce # of messages
            Random rand = new Random(seed);
            // maintain sum of all int sent
            long senderSum = 0;
            
            //  tracking # of msgs sent by client
            int numOfSentMessages = 0;

            // sum of received ints
            long receiverSum = 0;

            // track # of messages received (count)
            int numOfReceivedMessages = 0;

            System.out.println("Starting to send messages to server...");
            // loop to send messages
            for (int i =0; i < numMsg; i++){
                int randomInt = rand.nextInt(); // generator
                sendNumber(randomInt);      // send with method
                senderSum += randomInt;    // add to total
                numOfSentMessages++;       // count the messages
            }

            // Print statements
            System.out.println("Finished sending messages to server.");
            System.out.println("Total messages sent: " + numOfSentMessages);
            System.out.println("Sum of messages sent: " + senderSum);

        /* keep the client live after sending data
           listen for data coming back from server
           track that data with counters and sums
           print the results

           @param
           long receiver sum
           int numOfReceivedMessages

           create a loop to receive data i < numMsg
           receiverSum will add received
           num of received ++

           print statements
            Finished listening for messages from server.
            Total messages received: 
            Sum of messages received: 
        */     
       System.out.println("Starting to listen for messages from server...");
        for(int i = 0; i < numMsg; i++) {
            int received = receiveNum();
            receiverSum += received;
            numOfReceivedMessages++;
        }
        System.out.println("Finished listening for messages from server.");
        System.out.println("Total messages received: "+ numOfReceivedMessages);
        System.out.println("Sum of messages received: "+ receiverSum);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        } 
        cleanUp();
    }
}
