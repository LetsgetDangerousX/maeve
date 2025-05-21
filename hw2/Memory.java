package cs250.hw2;
import java.util.Random;
import java.util.LinkedList;
import java.util.TreeSet;

public class Memory{

    private static volatile long volatile_runningTotal = 0;

    
public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Must provide 3 arguments: size, experiments, seed");
            return;
        }

/**Task 1
            The first task involves contrasting the performance of programs with and without caching. In particular,
            you will be working with the volatile keyword in Java. The volatile keyword informs the compiler
            that the variable should not be cached and accesses should always go to main memory.
            To profile the impact of caching, you will be contrasting the performance of loops when the loop variable
            is marked volatile and when the volatile keyword is not used for the loop variable.
            Your loop will maintain a running total of the addition and subtraction operations using the loop variable.
            The choice of whether you perform an addition or subtraction to the runningTotal is based on whether
            the loop variable is odd or even for the given iteration. If the loop variable is even (e.g., 10) then you
            should add the loop variable to the runningTotal; if the loop variable is odd (e.g., 37) then you should
            subtract the loop variable from the runningTotal. To cope with potential overflows/underflows
            runningTotal should be a long variable type.
            For <experiments> times calculate the average time taken to perform runningTotal when the loop
            variable ranges from [0, <size>).
            Produce a short report (250-300 words) with graphs and/or tables describing the observed behavior
            when using the volatile keyword versus without */

//Parse command-line args

    int size = Integer.parseInt(args[0]);
    int experiments = Integer.parseInt(args[1]);
    int seed = Integer.parseInt(args[2]);

    double nonVTime = 0;
    double volatileTime = 0;
    long nonVSum = 0;
    long vSum = 0;

// Run <experiment> number of tests
    for (int i = 0; i < experiments; i++) {
    long[] nonVresult = calcNonV(size);
    nonVTime += nonVresult[0];
    nonVSum += nonVresult[1];

    long[] volatileResult = calcVolatile(size);
    volatileTime += volatileResult[0];
    vSum += volatileResult[1];
 }

//average computation
    nonVTime /= experiments;
    volatileTime /= experiments;
    nonVSum /= experiments;
    vSum /= experiments;



//output
/**OutPut Example:
     * Command: java cs250.hw2.Memory 25000000 1 42
    Task 1
    Regular: 0.02633 seconds
    Volatile: 0.16463 seconds
    Avg regular sum: -12500000.00
    Avg volatile sum: -12500000.00 */


    System.out.printf("Task 1\n");
    System.out.printf("Regular: %.5f seconds\n", nonVTime / 1e9);
    System.out.printf("Volatile: %.5f seconds\n", volatileTime / 1e9);
    System.out.printf("Avg regular sum: %.2f\n", (double) nonVSum);
    System.out.printf("Avg volatile sum: %.2f\n", (double) vSum);

// call remaining tasks:
    task2(size, experiments, seed);
    task3(size, experiments, seed);

} // end of main

/**method for Non-Volatile calculation for task 1*/
private static long[] calcNonV(int size){
    long runningTotal = 0;
    long startTime = System.nanoTime();

    //even or odd

    for ( int i = 0; i < size; i ++) {
        if (i % 2 == 0) {
            runningTotal += i; // adding if even
        }else{
            runningTotal -= i; // subtracting if odd
        }
    }

    long endTime = System.nanoTime();
    return new long[] {endTime - startTime, runningTotal};
}

//method for Volatile calculation for task 1
private static long[] calcVolatile(int size){

    volatile_runningTotal = 0; // restart variable 
    long startTime = System.nanoTime();

    for ( int i = 0; i < size; i++) {
        if (i % 2 == 0) {
            volatile_runningTotal += i; // adding if even
        } else {
            volatile_runningTotal -= i; // subtracting if odd
        }
    }
    long endTime = System.nanoTime();
    return new long[] {endTime - startTime, volatile_runningTotal};

}
//end of task 1
/***Task 2:
        Allocate an array with size <size> and fill it with random numbers using the <seed> to seed the random
        number generator. To get the most noticeable effect use the Integer type rather than the primitive int
        type. For <experiments> times do the following:
        Calculate the time to access each element in the first 10% of the array and a single random element in
        the last 10% of the array.
        Next, maintain a sum of each of the elements accessed and report the average across experiments for
        each of the following:
        1. Time to access a single element in the first 10% of the array
        2. Time to access a single random element in the last 10% of the array.
        3. Sum of the elements
        Produce a short report (250-300 words) with graphs and/or tables describing the observed behavior
        when accessing elements at the prescribed portions of the array */

private static void task2(int size, int experiments, int seed) {
    Random rand = new Random(seed);          //generate rand w/ seed
    Integer[] randArray = new Integer[size]; // allocate array w/size
    double first10PercTime =0 ;    
    double last10PercTime = 0;
    double sum = 0;

    // seed random number gen fill array til size max'd                                     
    for (int i = 0; i < size; i++) {randArray[i] = rand.nextInt();} 

    //Calculate time access each element first 10% of array &single random element in last 10%
    //maintain a sum of each of the elements accessed and report the average across experiments

    for ( int xprmt = 0; xprmt < experiments; xprmt++) {  //loop thru # of experiments
        long endTime;
        long startTime;
        int tenPercent = size / 10; // calc size of first 10%
        int last10Percent = size - tenPercent; // to calc first index of last 10% 

//Time to access a single element in the first 10% of the array
    for (int i = 0; i < tenPercent; i++) { // loop to begin adding the elements of the first ten percent
        startTime = System.nanoTime(); 
        sum += randArray[i].doubleValue();    // adds element to sum & unboxes Integer to double
        endTime = System.nanoTime();
        first10PercTime += (endTime - startTime);  //add elapsed time to total
    }

//Time to access a SINGLE random element in the last 10% of the array. [no for loop here]
        //Generate a random index in last 10%
        int randIndex = rand.nextInt(tenPercent) + last10Percent; // generates a rand index in last 10%
        //record start time
        startTime = System.nanoTime();
        // add random element to sum
        sum += randArray[randIndex].doubleValue(); // unbox Integer to double
        // record end time
        endTime = System.nanoTime();
        // add elapsed time to total time
        last10PercTime += (endTime - startTime);
    }

// Sum of the elements

    // calc avg time to first 10%
    first10PercTime /= (experiments * (size / 10.0));
    // calc avg time rand element
    last10PercTime /= experiments;
    // calc avg sum
    sum /= experiments;

//output
    /*output example: with Command: java cs250.hw2.Memory 25000000 1 42
    Task 2
    Avg time to access known element: 15.13 nanoseconds
    Avg time to access random element: 646.00 nanoseconds
    Sum: -1005470868.00
    */
    System.out.printf("Task 2\n");
    System.out.printf("Avg time to access known element: %.2f nanoseconds\n" , first10PercTime);
    System.out.printf("Avg time to access random element: %.2f nanoseconds\n", last10PercTime);
    System.out.printf("Sum: %.2f\n", sum);
}// end of task 2
/**Task 3:
        Allocate a TreeSet and LinkedList both with size <size> and fill both structures with the range of
        numbers [0, size).
        For <experiments> times do the following:
        • Calculate a random number in the range [0, size) and time how long the .contains() method
        takes to find if the element exists in the structure.
        Report the average time for each of the structures to find if the element exists.
        Produce a short report (250-300 words) with graphs and/or tables describing the observed behavior
        when using TreeSet versus a LinkedList. */

private static void task3(int size, int experiments, int seed) {
        
        // create variables
        double timeTreeSet = 0;
        double timeLL = 0;
        // create a new treeset of Integers
        TreeSet<Integer> treeSet = new TreeSet<>(); 
        // create new linkedlist of Integers
        LinkedList<Integer> linkedList = new LinkedList<>();
        // create random object with given seed
        Random randomNum = new Random(seed);

        

        // loop; .add each number to TreeSet and LinkedList
        for (int i = 0; i < size; i++){
            treeSet.add(i);
            linkedList.add(i);
        }
        // loop thru each experiment
        for (int xprmt = 0; xprmt < experiments; xprmt++){
            int randNum = randomNum.nextInt(size); // generate random number with range [0, size)
       
        

// Report the average time for each of the structures to find if the element exists
            // begin TreeSet timing

                // start time
                long startTime = System.nanoTime();
                // check if TreeSet.contains(rand)
                treeSet.contains(randNum);
                // end time
                long endTime = System.nanoTime();
                // set Tree time += end time - start time => add elapsed time to total time
                timeTreeSet += (endTime - startTime);

            //begin linkedlist timing
                //start time
                startTime = System.nanoTime();
                //check if LinkedList.contains(rand) 
                linkedList.contains(randNum);
                // end time
                endTime = System.nanoTime();
                //linkedlist time += endtime - start time => add elapsed time to total time
                timeLL += (endTime - startTime);
         }

//Calculate the aveerages
            //TreeTime /= experiments
            timeTreeSet /= experiments;
            //timeLL /= experiments
            timeLL /= experiments;

/**Example of task 3 output
 *  Task 3
    Avg time to find in set: 69055.00 nanoseconds
    Avg time to find in list: 83721555.00 nanoseconds
*/
//Output
System.out.printf("Task 3\n");
System.out.printf("Avg time to find in set: %.2f nanoseconds\n", timeTreeSet);
System.out.printf("Avg time to find in list: %.2f nanoseconds\n", timeLL);
}
// end of task 3


}// end of class Memory
