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

            if (input.equals("list")) {

                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }

            } else if (input.startsWith("mark ")) {

                int taskNumber = Integer.parseInt(input.substring(5));

                tasks[taskNumber - 1].markAsDone();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[taskNumber - 1]);

            } else if (input.startsWith("todo ")) {

                String description = input.substring(5);

                tasks[taskCount] = new Todo(description);

                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount]);

                taskCount++;

            } else if (input.startsWith("deadline ")) {

                String[] parts = input.substring(9).split(" /by ", 2);

                String description = parts[0];
                String by = parts[1];

                tasks[taskCount] = new Deadline(description, by);

                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount]);

                taskCount++;

            } else if (input.startsWith("event ")) {

                String eventInfo = input.substring(6);

                String[] fromParts = eventInfo.split(" /from ", 2);

                String description = fromParts[0];

                String[] toParts = fromParts[1].split(" /to ", 2);

                String from = toParts[0];
                String to = toParts[1];

                tasks[taskCount] = new Event(description, from, to);

                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount]);

                taskCount++;
            }

            input = scanner.nextLine();
        }

        System.out.println("That's game. See you next time!");
    }
}