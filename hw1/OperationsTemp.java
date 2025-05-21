package cs250.hw1;




public class OperationsTemp{

//Task 1: parse 3 args decimal, binary, hex - print error if not 3 args
//Output: Task 1 Correct number of arguments given.
// Expected output(caps and spaces wont matter for strings):
// Task 1
// Correct number of arguments given

    public static void main(String[] args) {
        System.out.println("Task 1");

        if (args.length !=3)
        {System.out.println("Task 1");
        System.out.println("Incorrect number of arguments given. Exiting..");}

        else
        {
        System.out.println("Correct number of arguments given");
        System.out.println();}

        System.out.println("Task 2");
        Identifier(args);
        System.out.println();

        System.out.println("Task 3");
        Verification(args);
        System.out.println();

        System.out.println("Task 4");
        String bin1 = "";
        String bin2 = "";
        String bin3 = "";

        if (args.length == 3) {
            String[] binaries = Convert_BinStorage(args);
            bin1 = binaries[0];
            bin2 = binaries[1];
            bin3 = binaries[2]; // this will hold my binaries after they have been converted in my method
        }
        System.out.println();

} //end of main

//Task 2: Identify binary, hex, and decimal
/* Output: 
Task 2 
15=Decimal 
0b1011=Binary 
0xhello=Hexadecimal
*/

        public static void Identifier(String[] args){
        for (String arg : args) {
            
            // checking first 2 digits of args for 0b
            if (arg.charAt(0) == '0' && arg.charAt(1) == 'b') { 
                System.out.println(arg + "=Binary");
            }
            // checking if first 2 digits are 0x
            else if (arg.charAt(0) == '0' && arg.charAt(1) == 'x') { 
                System.out.println(arg + "=Hexadecimal");
            
            // all else are decimals 
            }else{
                System.out.println(arg + "=Decimal");
            }
        }
        }
        


//Task 3: Error Checking: bin# nothing but 0-1; dec should be 0-9; hex should be 0-9 & a-f upper/lower
/* print error if all validations fail
Task 3
15=true
0b1011=true
0xfa=tru
*/
        public static void Verification(String[] args){
        for(String arg : args) {
            boolean ArgValid = true;
        
        // for binary; checking first that it qualifies as binary and then the following digits 0-1
        if (arg.charAt(0) == '0' && arg.charAt(1) == 'b') {
            for (int i =2; /* starting at index 2*/ i < arg.length(); i++) {
                if (arg.charAt(i) == '0' || arg.charAt(i) == '1') /* it will check at every index after 2*/
                {ArgValid = true; } else {ArgValid = false;}
        } 
        }
        // for hexadecimal; checking first it qualifies as hex and then checking 0-9 && a-f
        else if (arg.charAt(0) == '0' && arg.charAt(1) == 'x') {
            for (int i = 2; i < arg.length(); i++) {
                char H = arg.charAt(i);
                if((H <= '9' && H >= '0') || ( H >= 'A' && H <='F') || ( H >= 'a' && H <= 'f'))
                {ArgValid = true;}else{ArgValid = false;}
            }
        
        // checking the decimal
        }else {
            for (int i =0; i < arg.length(); i++) {
                if(arg.charAt(i) > '0' || arg.charAt(i) < '9')
                {ArgValid = true;}else{ArgValid = false;}
            }
        }
    if(!ArgValid){
        System.out.println("Invalid number format; check entries and try again");
        break;
    }else{System.out.println(arg + "=true");}
    // if any of the if statements set the boolean to false, the program will cease
    // whichever remains true will be printed here. :) finally got this one jeez
    }
        }



//Task 4: Convert each number into the other 2 number systems
/*output:
Task 4
Start=15,Binary=0b1111,Decimal=15,Hexadecimal=0xf
Start=0b1011,Binary=0b1011,Decimal=11,Hexadecimal=0xb
Start=0xfa,Binary=0b11111010,Decimal=250,Hexadecimal=0xfa
*/
public static String[] Convert_BinStorage(String[] args){
    /* create an array first to hold binary results for later use in the following tasks
        create FOR loop: each Stirng arg = args[i]; arg binary = ""; decimal =0; hexa= "";
    */
   String[] binaries = new String[args.length];

   for (int i =0; i < args.length; i++) {
    String arg = args[i];
    String binary = "";
    String dec = "";  
    String hex = "";

    dec = arg; // dont need to worry about what it starts with
    binary = decToBin(dec);
    //hex = decToHex(dec);
    
   
   /* binaries[i] = binary;
        System prints here not in main
        } after these statements return the binaries;
        wont have to repeat this process with the stored binaries so strings shouldnt matter */
   binaries[i] = binary;
   System.out.println
   ("Start=" + arg + ",Binary=0b" + binary + ",Decimal=" + dec + ",Hexadecimal=0x"+hex);

   }
   return binaries;
}

// to do math with these strings i need to convert them to int and the toString again for proper return
// method for converting String to integer needed first
public static int StringToInteger(String str){
    int num = 0;
    for(int i = 0; i < str.length(); i++) {
        int element = str.charAt(i)- '0';
        num = num * 10 + element;
    }
    return num;
}


public static String decToBin(String dec){
    int decimal = StringToInteger(dec); // hope this conversion works 
    StringBuilder binary = new StringBuilder();
    if (decimal == 0) {return "0";}
    while (decimal > 0){
    binary.insert(0, decimal % 2);
    decimal /=2;
    }
    return binary.toString();
    } // reminding myself to create proper return

    
}// end of class

