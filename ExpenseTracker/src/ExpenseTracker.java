import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ExpenseTracker {       //class name has to be the same name as file name like in C#
    private static final Scanner ConsoleScanner = new Scanner(System.in); // Use one global scanner so things dont go wonky and crash
    public static void main(String[] args) {
        int runtime = MainMenu();
        while (runtime != 3){
            if (runtime == 1){
                BulkWriteToFile();
            }
            else if (runtime == 2){
                ReadFile();
            }
        runtime = MainMenu();
        }

    }
    //main menu function to make the program easier to use
    //will run until the user crashes it by putting in the wrong kind of data time or until the user enters 3
    public static int MainMenu(){
        System.out.println("Please select an option(1, 2, or 3)");
        System.out.println("1. Write a grocery list\n2. View a grocery list\n3. Quit Program");
        int choice = ConsoleScanner.nextInt();
        ConsoleScanner.nextLine(); // Eats the newline character so that it doesn't crash
        return(choice);
    }
    //Reads a file(in the form of a grocery list),and writes it to the console.
    //writes as many times as items on the list
    public static void ReadFile() { 
        String fileName = GetInput("Please Enter the File Name of your Expenses list: ");
        try (Scanner fileScanner = new Scanner(new File(fileName))) 
        {
            System.out.println("| Quantity | Cost | Price |");
            while (fileScanner.hasNextLine()) 
            {
                String[] grocout = fileScanner.nextLine().split(",");
                for (String item : grocout){
                    String outgo = " | " + item + " | ";
                    System.out.print(outgo);
                }
                System.out.println("\n");
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Could not find that file");
        }
    }

    //Easier to call this function for basic string inputs than calling the entirety of the scnner each time
    //Might be lazy but it keeps the code looking more readable than otherwise.
    public static String GetInput(String inp) {
        System.out.println(inp);
        String response = ConsoleScanner.nextLine();
        return response;
    }

    //Only ever called by the bulk write function of the program, but asks the user 3 seprate times for what kind of grocieries, the amount they need, and the price of them
    //returns a list of that is formated to go to the list
    //The format is the same as what the ReadFile function uses.
    public static String GetGrocieries(){
        
        String typegroc = GetInput("What is the name of the groceries you need to buy?");
        System.out.println("Enter how many do you need: ");
        String gamount = String.valueOf(ConsoleScanner.nextInt());
        ConsoleScanner.nextLine();
        System.out.println("Enter the cost of the groceries: ");
        String amount = String.valueOf(ConsoleScanner.nextFloat()); //money
        ConsoleScanner.nextLine();
        return gamount +","+ typegroc +"," + "$"+amount;
    }

    //Probably the most cumbersome seciton of the program
    //Runs a loop as many times as there are items need
    //if anything breaks the error message prints
    public static void BulkWriteToFile(){
        String filename = GetInput("What is the name of the file you would like to write your list too?(If it is not found a new file will be created)");
        System.out.println("How many different kinds of groceries do you need?");
        int howmany = ConsoleScanner.nextInt();
        ConsoleScanner.nextLine();
        try (FileWriter myWriter = new FileWriter(filename)){
            
            int count = 0;
            while (count != howmany){
                System.out.println("______________________________");
                String thingsforlist = GetGrocieries();
                myWriter.write(thingsforlist+"\n");
                count ++;
            }

            System.out.println("Wrote to File succesfully");
        }
        catch (IOException e){
            System.out.println("An error occured");
        }
    }
}
