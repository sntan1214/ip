package lebron;

import java.io.IOException;
import java.time.format.DateTimeParseException;

/**
 * Represents the main chatbot application.
 * Coordinates user interaction, command parsing, task management,
 * and storage of tasks.
 */
public class Lebron {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Creates a Lebron chatbot and loads previously saved tasks
     * from the specified storage location.
     *
     * @param folderName name of the folder containing the data file
     * @param fileName name of the file used to store tasks
     */
    public Lebron(String folderName, String fileName) {

        ui = new Ui();
        parser = new Parser();

        storage = new Storage(folderName, fileName);

        try {
            tasks = storage.loadTasks();

        } catch (IOException | DateTimeParseException e) {

            ui.showMessage(
                    "Lebron: I couldn't load your saved tasks."
            );

            tasks = new TaskList();
        }
    }

    /**
     * Runs the main chatbot loop.
     * Reads commands from the user and performs the corresponding actions
     * until the user enters the bye command.
     */
    public void run() {

        ui.showGreeting();

        boolean isRunning = true;

        while (isRunning) {

            String input = ui.readCommand();

            String command = parser.getCommand(input);

            switch (command) {

                case "bye":
                    isRunning = false;
                    break;

                case "list":
                    ui.showTaskList(tasks);
                    break;

                case "find":
                    findTasks(input);
                    break;

                case "mark":
                    markTask(input);
                    break;

                case "delete":
                    deleteTask(input);
                    break;

                case "todo":
                    addTodo(input);
                    break;

                case "deadline":
                    addDeadline(input);
                    break;

                case "event":
                    addEvent(input);
                    break;

                default:
                    ui.showMessage(
                            "Lebron: I don't know that command."
                    );
                    break;
            }
        }

        ui.showGoodbye();
        ui.close();
    }

    private void findTasks(String input) {

        String keyword = input.substring(4).trim();

        if (keyword.isEmpty()) {
            ui.showMessage(
                    "Lebron: Tell me what you want to find!"
            );
            return;
        }

        TaskList matchingTasks = tasks.find(keyword);

        ui.showMatchingTasks(matchingTasks);
    }

    private void markTask(String input) {

        try {

            int taskNumber = parser.parseTaskNumber(input);

            if (taskNumber < 1 || taskNumber > tasks.size()) {

                ui.showMessage(
                        "Lebron: That task number doesn't exist!"
                );

                return;
            }

            Task task = tasks.mark(taskNumber);

            ui.showMarkedTask(task);

            saveTasks();

        } catch (NumberFormatException e) {

            ui.showMessage(
                    "Lebron: Please give me a valid task number!"
            );
        }
    }

    private void deleteTask(String input) {

        try {

            int taskNumber = parser.parseTaskNumber(input);

            if (taskNumber < 1 || taskNumber > tasks.size()) {

                ui.showMessage(
                        "Lebron: That task number doesn't exist!"
                );

                return;
            }

            Task deletedTask = tasks.delete(taskNumber);

            ui.showDeletedTask(
                    deletedTask,
                    tasks.size()
            );

            saveTasks();

        } catch (NumberFormatException e) {

            ui.showMessage(
                    "Lebron: Please give me a valid task number!"
            );
        }
    }

    private void addTodo(String input) {

        try {

            Todo todo = parser.parseTodo(input);

            tasks.add(todo);

            ui.showAddedTask(todo);

            saveTasks();

        } catch (IllegalArgumentException e) {

            ui.showMessage(
                    "Lebron: " + e.getMessage()
            );
        }
    }

    private void addDeadline(String input) {

        try {

            Deadline deadline = parser.parseDeadline(input);

            tasks.add(deadline);

            ui.showAddedTask(deadline);

            saveTasks();

        } catch (DateTimeParseException e) {

            ui.showMessage(
                    "Lebron: Please enter the date as yyyy-MM-dd!"
            );

        } catch (IllegalArgumentException e) {

            ui.showMessage(
                    "Lebron: " + e.getMessage()
            );
        }
    }

    private void addEvent(String input) {

        try {

            Event event = parser.parseEvent(input);

            tasks.add(event);

            ui.showAddedTask(event);

            saveTasks();

        } catch (IllegalArgumentException e) {

            ui.showMessage(
                    "Lebron: " + e.getMessage()
            );
        }
    }

    private void saveTasks() {

        try {

            storage.saveTasks(tasks);

        } catch (IOException e) {

            ui.showMessage(
                    "Lebron: I couldn't save your tasks."
            );
        }
    }

    /**
     * Starts the Lebron chatbot using the default data storage location.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        new Lebron(
                "data",
                "lebron.txt"
        ).run();
    }
}