package lebron;

/**
 * Parses user input into commands and task information
 * that can be used by the Lebron chatbot.
 */
public class Parser {

    /**
     * Extracts the command word from the user's input.
     *
     * @param input full command entered by the user
     * @return first word of the command, or an empty string if input is empty
     */
    public String getCommand(String input) {
        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            return "";
        }

        return trimmedInput.split(" ", 2)[0];
    }

    /**
     * Extracts the task number from commands such as mark or delete.
     *
     * @param input full command entered by the user
     * @return task number specified by the user
     * @throws NumberFormatException if no valid task number is provided
     */
    public int parseTaskNumber(String input) {
        String[] parts = input.trim().split(" ", 2);

        if (parts.length < 2) {
            throw new NumberFormatException();
        }

        return Integer.parseInt(parts[1].trim());
    }

    /**
     * Parses a todo command and creates a Todo task.
     *
     * @param input full todo command entered by the user
     * @return Todo created from the command
     * @throws IllegalArgumentException if the task description is empty
     */
    public Todo parseTodo(String input) {

        String description =
                input.substring(4).trim();

        if (description.isEmpty()) {
            throw new IllegalArgumentException(
                    "You gotta tell me what the todo is!"
            );
        }

        return new Todo(description);
    }

    /**
     * Parses a deadline command and creates a Deadline task.
     *
     * @param input full deadline command entered by the user
     * @return Deadline created from the command
     * @throws IllegalArgumentException if the description or due date is missing
     */
    public Deadline parseDeadline(String input) {

        String information =
                input.substring(8).trim();

        int byIndex =
                information.indexOf(" /by ");

        if (byIndex == -1) {
            throw new IllegalArgumentException(
                    "A deadline needs a /by date!"
            );
        }

        String description =
                information.substring(0, byIndex).trim();

        String by =
                information.substring(byIndex + 5).trim();

        if (description.isEmpty()) {
            throw new IllegalArgumentException(
                    "A deadline needs a description!"
            );
        }

        if (by.isEmpty()) {
            throw new IllegalArgumentException(
                    "You gotta tell me when it's due!"
            );
        }

        return new Deadline(description, by);
    }

    /**
     * Parses an event command and creates an Event task.
     *
     * @param input full event command entered by the user
     * @return Event created from the command
     * @throws IllegalArgumentException if the description, start time,
     *                                  or end time is missing
     */
    public Event parseEvent(String input) {

        String information =
                input.substring(5).trim();

        int fromIndex =
                information.indexOf(" /from ");

        int toIndex =
                information.indexOf(" /to ");

        if (fromIndex == -1
                || toIndex == -1
                || toIndex < fromIndex) {

            throw new IllegalArgumentException(
                    "An event needs both /from and /to!"
            );
        }

        String description =
                information.substring(
                        0,
                        fromIndex
                ).trim();

        String from =
                information.substring(
                        fromIndex + 7,
                        toIndex
                ).trim();

        String to =
                information.substring(
                        toIndex + 5
                ).trim();

        if (description.isEmpty()) {
            throw new IllegalArgumentException(
                    "An event needs a description!"
            );
        }

        if (from.isEmpty() || to.isEmpty()) {
            throw new IllegalArgumentException(
                    "Tell me when the event starts and ends!"
            );
        }

        return new Event(description, from, to);
    }
}