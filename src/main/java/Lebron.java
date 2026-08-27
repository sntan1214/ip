import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Lebron {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        Storage storage = new Storage("data", "lebron.txt");

        // LOAD SAVED TASKS
        try {
            taskCount = storage.loadTasks(tasks);
        } catch (IOException e) {
            System.out.println(
                    "Lebron: I couldn't load your saved tasks."
            );
        } catch (DateTimeParseException e) {
            System.out.println(
                    "Lebron: One of your saved dates is invalid."
            );
        }

        System.out.println("Hello! I'm Lebron");
        System.out.println("What can I do for you?");

        String input = scanner.nextLine();

        while (!input.equals("bye")) {

            // LIST
            if (input.equals("list")) {

                for (int i = 0; i < taskCount; i++) {
                    System.out.println(
                            (i + 1) + ". " + tasks[i]
                    );
                }

                // MARK
            } else if (input.equals("mark")
                    || input.startsWith("mark ")) {

                if (input.equals("mark")) {

                    System.out.println(
                            "Lebron: Tell me which task number to mark!"
                    );

                } else {

                    try {

                        int taskNumber =
                                Integer.parseInt(
                                        input.substring(5)
                                );

                        if (taskNumber < 1
                                || taskNumber > taskCount) {

                            System.out.println(
                                    "Lebron: That task number doesn't exist!"
                            );

                        } else {

                            tasks[taskNumber - 1].markAsDone();

                            System.out.println(
                                    "Nice! I've marked this task as done:"
                            );

                            System.out.println(
                                    tasks[taskNumber - 1]
                            );

                            try {
                                storage.saveTasks(
                                        tasks,
                                        taskCount
                                );
                            } catch (IOException e) {
                                System.out.println(
                                        "Lebron: I couldn't save your tasks."
                                );
                            }
                        }

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Lebron: Please give me a valid task number!"
                        );
                    }
                }

                // DELETE
            } else if (input.equals("delete")
                    || input.startsWith("delete ")) {

                if (input.equals("delete")) {

                    System.out.println(
                            "Lebron: Tell me which task number to delete!"
                    );

                } else {

                    try {

                        int taskNumber =
                                Integer.parseInt(
                                        input.substring(7)
                                );

                        if (taskNumber < 1
                                || taskNumber > taskCount) {

                            System.out.println(
                                    "Lebron: That task number doesn't exist!"
                            );

                        } else {

                            Task deletedTask =
                                    tasks[taskNumber - 1];

                            for (int i = taskNumber - 1;
                                 i < taskCount - 1;
                                 i++) {

                                tasks[i] = tasks[i + 1];
                            }

                            tasks[taskCount - 1] = null;
                            taskCount--;

                            System.out.println(
                                    "Alright, I've removed this task:"
                            );

                            System.out.println(deletedTask);

                            System.out.println(
                                    "Now you have "
                                            + taskCount
                                            + " tasks in the list."
                            );

                            try {
                                storage.saveTasks(
                                        tasks,
                                        taskCount
                                );
                            } catch (IOException e) {
                                System.out.println(
                                        "Lebron: I couldn't save your tasks."
                                );
                            }
                        }

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Lebron: Please give me a valid task number!"
                        );
                    }
                }

                // TODO
            } else if (input.equals("todo")
                    || input.startsWith("todo ")) {

                String description;

                if (input.length() > 4) {
                    description =
                            input.substring(4).trim();
                } else {
                    description = "";
                }

                if (description.isEmpty()) {

                    System.out.println(
                            "Lebron: You gotta tell me what the todo is!"
                    );

                } else {

                    tasks[taskCount] =
                            new Todo(description);

                    System.out.println(
                            "Got it. I've added this task:"
                    );

                    System.out.println(
                            tasks[taskCount]
                    );

                    taskCount++;

                    try {
                        storage.saveTasks(
                                tasks,
                                taskCount
                        );
                    } catch (IOException e) {
                        System.out.println(
                                "Lebron: I couldn't save your tasks."
                        );
                    }
                }

                // DEADLINE
            } else if (input.equals("deadline")
                    || input.startsWith("deadline ")) {

                int byIndex =
                        input.indexOf(" /by ");

                if (byIndex == -1) {

                    System.out.println(
                            "Lebron: A deadline needs a /by date!"
                    );

                } else {

                    String description =
                            input.substring(
                                    9,
                                    byIndex
                            ).trim();

                    String by =
                            input.substring(
                                    byIndex + 5
                            ).trim();

                    if (description.isEmpty()) {

                        System.out.println(
                                "Lebron: A deadline needs a description!"
                        );

                    } else if (by.isEmpty()) {

                        System.out.println(
                                "Lebron: You gotta tell me when it's due!"
                        );

                    } else {

                        try {

                            tasks[taskCount] =
                                    new Deadline(
                                            description,
                                            by
                                    );

                            System.out.println(
                                    "Got it. I've added this task:"
                            );

                            System.out.println(
                                    tasks[taskCount]
                            );

                            taskCount++;

                            try {
                                storage.saveTasks(
                                        tasks,
                                        taskCount
                                );
                            } catch (IOException e) {
                                System.out.println(
                                        "Lebron: I couldn't save your tasks."
                                );
                            }

                        } catch (DateTimeParseException e) {

                            System.out.println(
                                    "Lebron: Please enter the date as yyyy-MM-dd!"
                            );
                        }
                    }
                }

                // EVENT
            } else if (input.equals("event")
                    || input.startsWith("event ")) {

                int fromIndex =
                        input.indexOf(" /from ");

                int toIndex =
                        input.indexOf(" /to ");

                if (fromIndex == -1
                        || toIndex == -1
                        || toIndex < fromIndex) {

                    System.out.println(
                            "Lebron: An event needs both /from and /to!"
                    );

                } else {

                    String description =
                            input.substring(
                                    6,
                                    fromIndex
                            ).trim();

                    String from =
                            input.substring(
                                    fromIndex + 7,
                                    toIndex
                            ).trim();

                    String to =
                            input.substring(
                                    toIndex + 5
                            ).trim();

                    if (description.isEmpty()) {

                        System.out.println(
                                "Lebron: An event needs a description!"
                        );

                    } else if (from.isEmpty()
                            || to.isEmpty()) {

                        System.out.println(
                                "Lebron: Tell me when the event starts and ends!"
                        );

                    } else {

                        tasks[taskCount] =
                                new Event(
                                        description,
                                        from,
                                        to
                                );

                        System.out.println(
                                "Got it. I've added this task:"
                        );

                        System.out.println(
                                tasks[taskCount]
                        );

                        taskCount++;

                        try {
                            storage.saveTasks(
                                    tasks,
                                    taskCount
                            );
                        } catch (IOException e) {
                            System.out.println(
                                    "Lebron: I couldn't save your tasks."
                            );
                        }
                    }
                }

                // UNKNOWN COMMAND
            } else {

                System.out.println(
                        "Lebron: I don't know that command."
                );
            }

            input = scanner.nextLine();
        }

        System.out.println(
                "That's game. See you next time!"
        );

        scanner.close();
    }
}