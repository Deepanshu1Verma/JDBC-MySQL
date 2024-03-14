import java.util.Scanner; // Import the Scanner class

public class Test {
    public static void main(String[] args) {
        Scanner userWish = new Scanner(System.in); // Create a Scanner object

        String statement = "Enter number";

        System.out.print(statement);

        Integer userChoice = userWish.nextInt(); // Read user input
        System.out.println("userchoice is:" + userChoice);
    }
}