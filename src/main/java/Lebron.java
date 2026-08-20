import java.util.Scanner;

public class Lebron {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Hello! I'm Lebron");
        System.out.println("What can I do for you?");

        String input = scanner.nextLine();

        while (!input.equals("bye")) {

            if (input.equals("lipaidst")) {

                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }

            } else if (input.startsWith("mark ")) {

                int taskNumber = Integer.parseInt(input.substring(5));
                tasks[taskNumber - 1].markAsDone();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[taskNumber - 1]);

            } else {

                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("added: " + input);
            }

            input = scanner.nextLine();
        }

        System.out.println("That's game. See you next time!");
    }
}