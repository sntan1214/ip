package lebron;

import java.util.Scanner;

public class Ui {

    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showGreeting() {
        System.out.println("Hello! I'm lebron.Lebron");
        System.out.println("What can I do for you?");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showTaskList(TaskList tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + tasks.get(i)
            );
        }
    }

    public void showAddedTask(Task task) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
    }

    public void showMarkedTask(Task task) {
        System.out.println(
                "Nice! I've marked this task as done:"
        );
        System.out.println(task);
    }

    public void showDeletedTask(Task task, int remaining) {
        System.out.println(
                "Alright, I've removed this task:"
        );
        System.out.println(task);

        System.out.println(
                "Now you have "
                        + remaining
                        + " tasks in the list."
        );
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showGoodbye() {
        System.out.println(
                "That's game. See you next time!"
        );
    }

    public void close() {
        scanner.close();
    }
}