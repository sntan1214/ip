package lebron;

public class Parser {

    public String getCommand(String input) {
        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            return "";
        }

        return trimmedInput.split(" ", 2)[0];
    }

    public int parseTaskNumber(String input) {
        String[] parts = input.trim().split(" ", 2);

        if (parts.length < 2) {
            throw new NumberFormatException();
        }

        return Integer.parseInt(parts[1].trim());
    }

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