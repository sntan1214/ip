package lebron;

import java.io.IOException;
import java.time.format.DateTimeParseException;

/**
 * Represents the Lebron chatbot.
 */
public class Lebron {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Creates a Lebron chatbot and loads saved tasks.
     *
     * @param folderName folder containing the data file
     * @param fileName file used to store tasks
     */
    public Lebron(String folderName, String fileName) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(folderName, fileName);

        try {
            tasks = storage.loadTasks();
        } catch (IOException | DateTimeParseException e) {
            ui.showMessage("Lebron: I couldn't load your saved tasks.");
            tasks = new TaskList();
        }
    }

    /**
     * Runs the text-based version of Lebron.
     */
    public void run() {
        ui.showGreeting();

        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();
            String response = getResponse(input);

            ui.showMessage(response);

            if (parser.getCommand(input).equals("bye")) {
                isRunning = false;
            }
        }

        ui.close();
    }

    /**
     * Generates Lebron's response to a command.
     *
     * @param input command entered by the user
     * @return Lebron's response
     */
    public String getResponse(String input) {
        String command = parser.getCommand(input);

        switch (command) {
            case "bye":
                return "That's game. See you next time!";

            case "list":
                return formatTaskList("Here are your tasks:", tasks);

            case "find":
                return findTasks(input);

            case "mark":
                return markTask(input);

            case "delete":
                return deleteTask(input);

            case "todo":
                return addTodo(input);

            case "deadline":
                return addDeadline(input);

            case "event":
                return addEvent(input);

            default:
                return "Lebron: I don't know that command.";
        }
    }

    private String findTasks(String input) {
        String keyword = input.substring(4).trim();

        if (keyword.isEmpty()) {
            return "Lebron: Tell me what you want to find!";
        }

        TaskList matchingTasks = tasks.find(keyword);

        return formatTaskList(
                "Here are the matching tasks in your list:",
                matchingTasks
        );
    }

    private String markTask(String input) {
        try {
            int taskNumber = parser.parseTaskNumber(input);

            if (taskNumber < 1 || taskNumber > tasks.size()) {
                return "Lebron: That task number doesn't exist!";
            }

            Task task = tasks.mark(taskNumber);

            return saveAndReturn(
                    "Nice! I've marked this task as done:\n" + task
            );

        } catch (NumberFormatException e) {
            return "Lebron: Please give me a valid task number!";
        }
    }

    private String deleteTask(String input) {
        try {
            int taskNumber = parser.parseTaskNumber(input);

            if (taskNumber < 1 || taskNumber > tasks.size()) {
                return "Lebron: That task number doesn't exist!";
            }

            Task deletedTask = tasks.delete(taskNumber);

            return saveAndReturn(
                    "Alright, I've removed this task:\n"
                            + deletedTask
                            + "\nNow you have "
                            + tasks.size()
                            + " tasks in the list."
            );

        } catch (NumberFormatException e) {
            return "Lebron: Please give me a valid task number!";
        }
    }

    private String addTodo(String input) {
        try {
            Todo todo = parser.parseTodo(input);
            tasks.add(todo);

            return saveAndReturn(
                    "Got it. I've added this task:\n" + todo
            );

        } catch (IllegalArgumentException e) {
            return "Lebron: " + e.getMessage();
        }
    }

    private String addDeadline(String input) {
        try {
            Deadline deadline = parser.parseDeadline(input);
            tasks.add(deadline);

            return saveAndReturn(
                    "Got it. I've added this task:\n" + deadline
            );

        } catch (DateTimeParseException e) {
            return "Lebron: Please enter the date as yyyy-MM-dd!";

        } catch (IllegalArgumentException e) {
            return "Lebron: " + e.getMessage();
        }
    }

    private String addEvent(String input) {
        try {
            Event event = parser.parseEvent(input);
            tasks.add(event);

            return saveAndReturn(
                    "Got it. I've added this task:\n" + event
            );

        } catch (IllegalArgumentException e) {
            return "Lebron: " + e.getMessage();
        }
    }

    private String formatTaskList(String heading, TaskList taskList) {
        StringBuilder result = new StringBuilder(heading);

        for (int i = 0; i < taskList.size(); i++) {
            result.append("\n")
                    .append(i + 1)
                    .append(". ")
                    .append(taskList.get(i));
        }

        return result.toString();
    }

    private String saveAndReturn(String response) {
        try {
            storage.saveTasks(tasks);
            return response;

        } catch (IOException e) {
            return response + "\nLebron: I couldn't save your tasks.";
        }
    }

    /**
     * Starts the text-based version of Lebron.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        new Lebron("data", "lebron.txt").run();
    }
}
