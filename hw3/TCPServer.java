package cs250Past.hw3;

import java.io.*;    
import java.net.*;
import java.io.IOException;
import java.util.Random;

public class TCPServer {
    static DataInputStream[] din = new DataInputStream[2];
    static DataOutputStream[] dout = new DataOutputStream[2]; 
    static Socket[] clientSocket = new Socket[2];
    static ServerSocket serverSocket;
    

    public static int receiveNum(int index){
        try {
            int response = din[index].readInt();
            return response;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return -1;
    }

    public static void sendNumber(int numToSend, int index){
        try {
            dout[index].writeInt(numToSend);
            dout[index].flush(); // By flushing the stream, it means to clear the stream of any element that may be or maybe not inside the stream
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static void cleanUp(){
        try {
            serverSocket.close();
            for (int i = 0; i <2; ++i){
            clientSocket[i].close();
            dout[i].close();
            din[i].close();
            }
            System.out.println("Connections Closed");
            System.exit(0);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public static void main(String[] args){

         // Validate
        if (args.length != 3) {
            System.err.println("Error: Expected 3 args <port-number> <seed> <number-of-messages>");
            System.exit(1);
        }
        
        final int port = Integer.parseInt(args[0]); 
        final int seed = Integer.parseInt(args[1]);
        final int numMsg = Integer.parseInt(args[2]);

        // port validation
        try{
          serverSocket = new ServerSocket(port);
        }catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        

        try{
            InetAddress local = InetAddress.getLocalHost();
            System.out.println("IP Address: " + local.getHostName() + "/" + local.getHostAddress());
            System.out.println("Port Number " + port);  

            // Initialize Necessary Objects
            System.out.println("waiting for client...");

            clientSocket[0] = serverSocket.accept(); // Blocking call --> waits here until a request comes in from a client
            clientSocket[1] = serverSocket.accept();
            System.out.println("Clients Connected!");

        //Initalize streams
            dout[0] = new DataOutputStream(clientSocket[0].getOutputStream()); // Instantiates dout so we can then use it to send data to the client
            din[0] = new DataInputStream(clientSocket[0].getInputStream());
            dout[1] = new DataOutputStream(clientSocket[1].getOutputStream());
            din[1] = new DataInputStream(clientSocket[1].getInputStream()); // Instantiates din so we can then use it to receive data from the client

        // Generate random numbers for each client
            Random rand = new Random(seed);
            int clientSeed0 = rand.nextInt();
            int clientSeed1 = rand.nextInt();

            // Send # of messages and rand seed

            //First Client
            System.out.println("Sending config to clients...");
            sendNumber(numMsg, 0);
            sendNumber(clientSeed0, 0);
            System.out.println(clientSocket[0].getInetAddress().getHostName() + " " + clientSeed0);

            //Second Client
            sendNumber(numMsg, 1);
            sendNumber(clientSeed1, 1);
            System.out.println(clientSocket[1].getInetAddress().getHostName() + " " + clientSeed1); 

            System.out.println("Finished sending config to clients.");

        /* create arrays to track meta data
        * loop for receiving => both clients and fowarding to other
        * receive from client(i)
        * track the sum 
        * track the count
        * relay to other client
        * so a for loop*/
        System.out.println("Starting to listen for client messages...");
        int[] msgCount = new int[2];        // counts messages
        long[] msgSum = new long[2];        // sums messages

           for(int j = 0; j < numMsg; j++) {
                for(int i = 0; i < 2; i++) {
                    int received = receiveNum(i);
                    msgSum[i] += received;
                    msgCount[i]++;
                    // 1-0 = 1 >> send to client 1 ; 1 - 1 = 0 >> send to client 0
                    sendNumber(received, 1 - i); 
                }
           }
           System.out.println("Finished listening for client messages.");

        // print meta data for clients
        for(int i = 0; i < 2; i++){
            System.out.println(clientSocket[i].getInetAddress().getHostName());
            System.out.println("Messages received: " + msgCount[i]);
            System.out.println("Sum received: " + msgSum[i]);
        }

        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
    }



}
