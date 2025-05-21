package cs250.hw1;
import java.util.Arrays;

public class Operations{

//-------------------------------------------------TASK 1: PARSING 3 ARGS n MAIN-------------------------------------------------
/** Parsing 2 Args  
     * parse 3 args decimal, binary, hex - print error if not 3 args  
     * Output: Task 1 Correct number of arguments given.  
     * Expected output: Task 1
     * caps and spaces wont matter for strings
     * Correct number of arguments given
     */

public static void main(String[] args) {
        System.out.println("Task 1");

        if (args.length !=3) 
        {
        System.out.println("Incorrect number of arguments have been provided. Program Terminating!"); System.exit(0);}
        else{System.out.println("Correct number of arguments given");
        System.out.println();}

        System.out.println("Task 2");       //Task 2 Identify Number Systems
        Identifier(args);
        System.out.println();

        System.out.println("Task 3");       //Task 3: Error Checking
        Verification(args);
        System.out.println();

        System.out.println("Task 4");      //Task 4: Convert Args Number Sys

        String[] argsArr = 
        new String[args.length]; 
        
         // original arg array
        if (args.length == 3) {
            for (int i =0; i < args.length; i++) {
                argsArr[i] = args[i];}}
            
       
        String[] binaries; // binary storage
        binaries = Convert_BinStorage(args); 
        
        System.out.println();
        System.out.println("Task 5");        //Task 5 Ones Complement
        onesComp(binaries, argsArr);
        System.out.println();
        String[] onesCompArr = onesComp(binaries, argsArr);
       
        System.out.println();
        System.out.println("Task 6");          //Task 6 Twos Complement
        twosComp(onesCompArr, argsArr, binaries);

        System.out.println();
        System.out.println("Task 7");           //Task 7 Compute Bitwise
        printBitWise(binaries);

        System.out.println();
        System.out.println("Task 8");           //Task 8 Bitwise LR shift x2
        bitWiseShift(binaries);

} //end of main

//-------------------------------------------------TASK 2 IDENTIFY NUMBER SYSTEMS-------------------------------------------------

/** Identify Number Systems: logic
 *      Output: 
        Task 2 
        15=Decimal 
        0b1011=Binary 
        0xhello=hexadecimal
        */

public static void Identifier(String[] args){
    for (String arg : args) {

    // checking first 2 digits of args for 0b
    if (arg.charAt(0) == '0' && arg.charAt(1) == 'b') { 
        System.out.println(arg + "=Binary");
         }
    // checking if first 2 digits are 0x
    else if (arg.charAt(0) == '0' && arg.charAt(1) == 'x') { 
        System.out.println(arg + "=hexadecimal");

    // all else are decimals 
    }else{
        System.out.println(arg + "=Decimal");
    }
    }
    }


//----------------------------------------------------TASK 3: ERROR CHECKING------------------------------------------------------


/**Error Checking: quick logic
 *  bin# nothing but 0-1; dec should be 0-9; hex should be 0-9 n a-f upper/lower
    print error if all validations fail
    Task 3
    15=true
    0b1011=true
    0xfa=tru
    */
public static void Verification(String[] args){
    boolean areValid = true;

    for(String arg : args) {
        boolean argValid = true; 
    // for binary; checking first that it qualifies as binary and then the following digits 0-1
    if (arg.charAt(0) == '0' && arg.charAt(1) == 'b') {

        for (int i =2; i < arg.length(); i++) {

            if (arg.charAt(i) != '0' && arg.charAt(i) != '1') /* it will check at every index after 2*/
            {argValid = false;}
    } 
    }
    // for hexadecimal; checking first it qualifies as hex and then checking 0-9  a-f
    else if (arg.charAt(0) == '0' && arg.charAt(1) == 'x') {

        for (int i = 2; i < arg.length(); i++) {

            char h = arg.charAt(i);

            if(!(Character.isDigit(h) || (h >= 'A' && h <='F') || (h >= 'a' && h <= 'f')))
            {argValid = false;}
        }

    // checking the decimal
    }else {
        for (int i =0; i < arg.length(); i++) {
            if(!Character.isDigit(arg.charAt(i)))
            {argValid = false;}
            
            }
        }
        if(!argValid) { System.out.println(arg + "=false"); areValid = false;}
         else {System.out.println(arg + "=true"); }

    } //end of for
        if(!areValid){System.exit(0); }
    }// end of method

        


//-------------------------------------------------==TASK 4: CONVERT ARGS NUMBER SYS-----------------------------------------------


/** Convert Args to Other Number Systems: Logic
 *  This method will create an array to hold the binary converted numbers
 * it will iterate over the length of the args and store them as an imutable string
 * with the index i. IF statements look for necessary prefixes indicating
 * binary or hex and are saved as strings under var: binary dec hex
 * To keep the code clean, each calculation has its own helper method plus
 * 2 more that they all share for converting to int back to strings. once the 
 * calculations are complete, all converted binary solutions are saved to an array 
 * and the proper string is printed.
 * finally the array is then returned for later use. 
 * output:
 * 
                Task 4
                Start=15,Binary=0b1111,Decimal=15,hexadecimal=0xf
                Start=0b1011,Binary=0b1011,Decimal=11,hexadecimal=0xb
                Start=0xfa,Binary=0b11111010,Decimal=250,hexadecimal=0xfa
                */

public static String[] Convert_BinStorage(String[] args){
    /* create an array first to hold binary results for later 
        create FOR loop: each Stirng arg = args[i]; arg binary = ""; decimal =0; hexa= "";
    */
   String[] binaries = new String[args.length];

   for (int i =0; i < args.length; i++) {
    String arg = args[i];
    String binary = "";
    String dec = "";  
    String hex = "";
   
        
      /* IF statements- since verification has already been passed to get to this point
        could make a simple string check for the ifs instead of using characters

        if arg starts with "0b":
         starting at 2 so arg.substring(2);
        dec = call binToDec
        hex = call decTohex

        else if arg starts with "0x":
        hex=starting at 2 so arg.substring(2);
        dec = call hexToDec
        binary = call decToBin

        else: its a decimal
        dec = arg;
        binary = call decToBin
        hex = call decTohex

        series of if statements to identify what each arg is:
        
        ifs
        .. binary
        bin to dec -- 1 create binToDec method  call
        dec to hex .. 2 create decTohex method  call

        .. hex
        hex to dec -- 3 create hexToDec method  call
        dec to bin ..

        .. dec
        dec to bin  .. 4 create decToBin method  call
        dec to hex  .. 

            only need the four methods - no need for hextobin or bintohex+
       
    */

   //binary conversion for Binary Entry

   // saving binary for storage
   if (arg.startsWith("0b")) {
    binary = arg.substring(2);
    dec = binToDec(binary);
    hex = decTohex(dec); // this will take the dec and put it into this method
   }
   
   // hexadecimal conversion to bin and dec
   // saving binary for storage
    else if (arg.startsWith("0x")) {
        hex = arg.substring(2);
        dec = hexToDec(hex);    // hex to dec - make sure to provide a return val in hexToDec for next conversion
        binary = decToBin(dec); // since hex is converted to dec, we can just input that into decToBin
   

    }else{// decimal entry
    dec = arg; // dont need to worry about what it starts with
    binary = decToBin(dec);
    hex = decTohex(dec);
    }

   binaries[i] = binary;
   System.out.println
   ("Start=" + arg + ",Binary=0b" + binary + ",Decimal=" + dec + ",hexadecimal=0x"+hex);

   }
   return binaries;
}

//------------------------------------------------------HELPER METHODS--------------------------------------------------------

/*Helpers Logic 
    these helpers Should allow me to bounce back and forth between strings and integers 
    this should allow me to calculate when needed and then return a string value when needed
    to do math with these strings i need to convert them to int and the toString again for proper return
    method for converting String to integer needed first
    there will be two sets of conversions: Type and Number Systems*/

//------------------------------------------------------TYPE CONVERSIONS---------------------------------------------

public static int StringToInteger(String str){
    int number = 0;
    for(int i = 0; i < str.length(); i++) {
        int element = str.charAt(i)- '0';
        number = number * 10 + element;
    }
    return number;
}

//---------------------------------------------------

public static String intToString(int number){
    if (number == 0 ) {
        return "0";
    }
    StringBuilder string = new StringBuilder();

    while(number > 0) {
        string.insert(0, number % 10);
        number /= 10;
    }
    return string.toString();


} // will need this to change back to string

//------------------------------------------------NUMBER SYSTEMS CONVERSION---------------------------------------------

public static String decToBin(String dec){
    int decimal = StringToInteger(dec); // hope this conversion works 

    StringBuilder binary = new StringBuilder(); // needs an if statement 
    if (decimal == 0) {return "0";}
    // create a while loop until decimal value becomes zero
    while(decimal > 0){
    binary.insert(0, decimal % 2);
    decimal /=2;
    }
    return binary.toString();
    } // reminding myself to create proper return

    // this one works so far :) Binaries are printing as expected

//-------------------------------------------

public static String binToDec(String binary){
    int decimal = 0; // initialize to zero
    int power = 0;

    for ( int i = binary.length() - 1; i >= 0; i--) { // right to left looping
        if (binary.charAt(i) == '1') { // if char is 1
            int power2 = 1;     // power of 2 initialized to 1
            for(int j = 0; j < power; j++){
             power2 *= 2; // this should multiply each power2 by 2 for each iteration
            }
            decimal += power2; // power2 will be added to decimal val
        }
        power++;//.. i am not sure about this; goal: increment power of 2 for every iteration
    }

    return intToString(decimal); // converting integer to string for later input
    }



/*conversion  helper logic
    input is a string so i will need to convert the decimal string to integer with my new helper
    will need another stringbuilder here to build the hex string
     hex characters are needed; an array 
     base case zero
     looping until decimal is zero
     calculations:
        hex insert :: remainder when decimal / 16 to prepend hex character
        the decimal /= 16; divide decimal by 16 here too
        output: return hex toString
    
    decimal = convert dec string to integer with helper 
    hexString = ""      
    hexArray = ['hexcharacters'];
    if decimal is 0:
    return "0" make sure its string!
    while loop - decimal is > 0:
    remainder = decimal n 16
    hex = hexArray[remainder]
    hexString = prepend hexdig to string at beginning
    decimal = decimal / 16
    My logic:
    if input is "26" its converted to 26
    loop:
    26 % 16 = 10 => hexArray[10] = 'A'; hex becomes "A"
    26 / 16 = 1
    loop:
    1 % 16 = 1 => hexArray[1] is '1'; hex is '1A'
    1/16 = 0
    end
    return '1A'
    its 4am nap time :')
*/
//--------------------------------------------

public static String decTohex(String dec){
    int decimal = StringToInteger(dec);
    if(decimal == 0){
    return "0";
    }

    StringBuilder hex = new StringBuilder();
    char[] hexArray = 
    {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    while(decimal > 0) {
    hex.insert(0, hexArray[decimal % 16]);
    decimal/=16;
    }

    return hex.toString();
}

//---------------------------------------

public static String hexToDec(String hex){
        int decimal = 0;
        int power = 0;

        for(int j = hex.length() - 1; j >= 0; j--) {

            char chex = hex.charAt(j);

            int current;

                if(chex >= '0' && chex <= '9') {
                    current = chex - '0';
                }else if (chex >= 'a' && chex <= 'f') {
                    current = chex - 'a' + 10;
                }else {return "-1";}
            
            int pwr16 = 1;

            for(int i = 0; i < power; i++) {
            pwr16 *= 16;
                }
            decimal += current * pwr16; power++;

        } // end of for loop

        return intToString(decimal);
        } // end of helper

//------------------------------------------------------TASK 5 ONES COMPLEMENT---------------------------------------------------
/**Task 5: Convert all numbers to binary then compute 1s complement of each number
    the following tasks 6-8 -good to save these binary results as a var/array to call back later
    accomp by flipping each bit in binary rep 0 to 1 and 1 to 0
    output:
    Task 5
    15=1111=>0000
    0b1011=1011=>0100
    0xfa=11111010=>00000101
    */
    //Use binary array to complete ones complement

public static String[] onesComp(String[] binaries, String[] argsArr)  {
    
    // store the results for task 6:
    String[] onesCompResult = new String[binaries.length];      //new string array same length as binaries

    for(int i = 0; i < binaries.length; i++) {          // create index that ends when the array ends
                 String binary = binaries[i];   
                String argsStr = argsArr[i];
               // set index for the converted binaries array
                StringBuilder onesCompStr = new StringBuilder();       // build the string to print

                     //  conversion processFor Loop 
            for(char perBit : binary.toCharArray()) {              
            // moving char of binary string to array to convert per bit
                
                if (perBit == '0') {onesCompStr.append('1');}
                else if (perBit == '1') {onesCompStr.append('0');} //end if loop

            } //end inner for loop
            onesCompResult[i] = onesCompStr.toString(); 
            //Print new Strings
            
            System.out.println(argsArr[i]+ "=" + binary + "=>" + onesCompResult[i]); 

    } //end for loop
    return onesCompResult;

}//end task 

//-----------------------------------------------------TASK 6 TWOS COMPLEMENT----------------------------------------------------

/**Task 6: Compute the 2s complement in binary of every number
    acomp by applying 1s comp then adding 1
   

    logic plan:
    *let onesCompResults :: OCR
    *and all var abbreviated after named thru this note

    going to need an iteration print process
    twosComp(String[] onesCompresult, String[] argsArr, binaries[]): 
   
        ..For loop 1:
            >iterated over OCR length : increment i
            ..Create string onesComp = OCR result[i] to print
            ..Create argStr with arg012[i] to print
            ..Create mutable stringBuilder  twoscomptStr: onescomp
            ..set carry : 1

                 .. for Loop 2: set index (k) to the length of twosCompStr -1 : index decrements
                        set perBit character = twosCompStr at index (k)
                            .. if cases:
                            bit = 1 n carry = 1 => set char at index to 0
                            bit= 0 n carry = 1 => set char at index to 1 => set carry to 0
                            bit = 1 n carry = 1 => set char at index to 1 => set carru to 0
                            bit = 0 n carry = 1 => set char at index to 0 => set carry to 0
                            else return;
                end with print statement [argStr + onesComp + twosCompStr]

        print 
        output:
        Task 6
        15=1111=>0001
        0b1011=1011=>0101
        0xfa=11111010=>00000110
    */

public static void twosComp(String[] onesCompArr, String[] argsArr, String[] binaries) {
    for(int i =0; i < onesCompArr.length; i++){
        String onesComp = onesCompArr[i];
        String argsStr = argsArr[i];
        String binaryStr = binaries[i];
        StringBuilder twosCompStr = new StringBuilder(onesComp);
        int carry = 1;
            
            for(int k = twosCompStr.length() -1; k >= 0; k--){
                char perBit = twosCompStr.charAt(k);

                    if(perBit == '1' && carry == 1) {twosCompStr.setCharAt(k, '0');}
                   else if(perBit == '0' && carry == 1) {twosCompStr.setCharAt(k, '1'); carry = 0;}
                   else if(perBit == '1' && carry == 0) {twosCompStr.setCharAt(k, '1'); carry = 0;}
                   else if(perBit == '0' && carry == 0) {twosCompStr.setCharAt(k, '0'); carry = 0;}
                   else{return;} //error msg not req in hw

            }//end innner for loop
            System.out.println(argsStr + "=" + binaryStr + "=>" + twosCompStr);
    }//end outer for loop
}// end method

//-----------------------------------------------------TASK 7 COMPUTE BITWISE----------------------------------------------------
/**Task 7: Compute bitwise OR AND XOR of 3 numbers
    OR: if either val is 1 the result is 1 else its 0
    AND: if both vals are 1 the result is 1 else its 0
    XOR: if either vals are 1 and both not 1 the val is 1 else 0

    output:
    Task 7
    1111/1011/11111010=11111111
    1111n1011n11111010=00001010
    1111/1011/11111010=11111110

    take in binaries
    split each entry into individual var for printing
    Create char array to hold forbidden bitwise ops as characters for print 
    find max lengths and pad smaller binaries with zeros for comparison
    Use stringbuilder to create mutable strings to hold the padded entries
    assign char var for each index of padded entries
    to compare each entry 
    
    OR : 
        check each entry for a 1 n zero
        if any are 1: set char at index to 1
        else set char at index to 0

    AND: 
        check if each entry for a 1 n 0
        if they are all 1: set char at index to 1
        else: set char at index to 0

    XOR:
        initialize count to 0
        if any char at any index is 1; increment count by 1
        use the count to determine if its odd or even
        using mudolo 
        if its even: set char at index to 1
        if its odd : set char at index to 0
    
    Print

    end

    */
public static void printBitWise(String[] binaries){
       //string array for bitwise operators
       String[] stringArray = {"|","&","^"};
            String bitOR  = stringArray[0];
            String bitAND = stringArray[1];
            String bitXOR = stringArray[2];


        // each index of binaries is labeled 
            String bin1 = binaries[0];      //      1111
            String bin2 = binaries[1];      //      1011
            String bin3 = binaries[2];      //  11111010
        
        //finding the max length for padding later
            int binMax = bin1.length();
            
            if (bin2.length() > binMax) {
                binMax = bin2.length();
            }
            if (bin2.length() > binMax) {
                binMax = bin2.length();
            }
            if (bin3.length() > binMax) {
                binMax = bin3.length();
            }

        //Zero Padding for Adding
        //Building mutable strings with each indexed value
        // while each length is less than max val, 0 will append at index 0

                                                                //Padded strings:
            StringBuilder padded1 = new StringBuilder(bin1);       //00001111
            while(padded1.length() < binMax) {
                padded1.insert(0, "0");
            }   

            StringBuilder padded2 = new StringBuilder(bin2);      //00001011
            while(padded2.length() < binMax) {
                padded2.insert(0, "0");
            }
        
            StringBuilder padded3 = new StringBuilder(bin3);      //11111010
            while(padded3.length() < binMax) {
                padded3.insert(0, "0");
            }
           
            // for as long as the index is < max length
            // iterate over each char of each mutable string
            // each assigned a var to compare

                    for(int i = 0; i < binMax; i++) {
                        char op1 = padded1.charAt(i);
                        char op2 = padded2.charAt(i);
                        char op3 = padded3.charAt(i);
                        

                       //OR
                        if(op1 == '1' || op2 == '1' || op3 == '1') {
                            padded1.setCharAt(i, '1');
                        }else{padded1.setCharAt(i, '0');}

                        //AND
                        if(op1 == '1' && op2 == '1' && op3 == '1') {
                            padded2.setCharAt(i, '1');
                        }else{padded2.setCharAt(i, '0');}

                        //XOR
                        int cnt = 0;
                        if(op1 == '1'){cnt++;}
                        if(op2 == '1'){cnt++;}
                        if(op3 == '1'){cnt++;}

                        if(cnt % 2 != 0) { //odd
                                padded3.setCharAt(i, '1');}
                        else{padded3.setCharAt(i, '0');}
                    }

            System.out.println(bin1 + bitOR + bin2 + bitOR + bin3 + "=" + padded1);
            System.out.println(bin1 + bitAND + bin2 + bitAND + bin3 + "="+ padded2);
            System.out.println(bin1 + bitXOR + bin2 + bitXOR + bin3 + "=" + padded3);
            }
         

//-----------------------------------------------------TASK 8 BITWISE LR SHIFT X2------------------------------------------------
/**Task 8: BitWise LR Shift x2
 * Compute bitwise left and right shift of the 3 numbers for 2 shifts
    left: accomp by appending an x number of 0s
    right: accomp by moving each digit to the right x time

    Create method that takes in binaries again
    create an array of the binaries by index called bins
 
          String[] bins = binaries[0],     //      1111
                          binaries[1],     //      1011
                          binaries[2];     //  11111010
        
        create a stringbuilder array[3] :: leftshifts to store the left shifted strings
        create a string[] rightShifts [3] :: stores rightshifted strings

        FOR.. 3x
                ..leftshifts index = new stringbuilder(bins[i]) 
                    this will initialize SB for current binary string
                ..iterate over each mutuable string and append 0 
                    set the index to 2 so that it will only 
                    add 2 0's to each current binary string
                        

                ..right shift
                IF: binary length is less than 2, string is too short
                    right shift val = 0
                else:
                    rightshift index = bins[i].substring (0, bins[i].length() -2)
        exit for loop

    For loop 3x:
    print
    
    output:
    Task 8
    1111..2=111100,1111..2=11
    1011..2=101100,1011..2=10
    11111010..2=1111101000,11111010..2=111110
    */

public static void bitWiseShift(String[] binaries){

           
    String[] bins =   {binaries[0],     //      1111
                        binaries[1],     //      1011
                        binaries[2]};     //  11111010

    StringBuilder[] left= new StringBuilder[3];
    String[] right = new String[3];        
        
    for(int i = 0; i < 3; i++){
            left[i] = new StringBuilder(bins[i]);
                for(int j = 0; j < 2; j++){
                    left[i].append('0');}

                    if(bins[i].length() <=2){right[i] = "0";}
                    else{right[i] = bins[i].substring(0, bins[i].length()-2);}

                }//end outer for loop
                
                for(int i = 0; i < 3; i++){
                    System.out.println(bins[i] + '<' + '<' + "2=" + left[i]
                     + ',' + bins[i] + '>' + '>' + "2="+ right[i]);
                    }//end inner for loop

        }// end of class
        }//end of program


        //end: dont forget to:
        // check for errors
        // save as zip: Amber-Ferrell-hW1.zip
        //containing only cs250/hw1/Operations.java