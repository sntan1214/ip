import java.util.Scanner;

public class Lebron {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Lebron");
        System.out.println("What can I do for you?");

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            System.out.println(input);
            input = scanner.nextLine();
        }

        System.out.println("That's game. See you next time!");
    }
}