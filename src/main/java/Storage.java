import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Storage {

    private final Path filePath;

    public Storage(String folderName, String fileName) {
        this.filePath = Path.of(folderName, fileName);
    }

    public int loadTasks(Task[] tasks) throws IOException {

        // Create the folder if it does not exist
        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
        }

        // Create the file if it does not exist
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            return 0;
        }

        List<String> lines = Files.readAllLines(filePath);

        int taskCount = 0;

        for (String line : lines) {

            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split(" \\| ", -1);

            String taskType = parts[0];
            boolean isDone = parts[1].equals("1");

            Task task;

            if (taskType.equals("T")) {

                String description = parts[2];
                task = new Todo(description);

            } else if (taskType.equals("D")) {

                String description = parts[2];
                String by = parts[3];

                task = new Deadline(description, by);

            } else if (taskType.equals("E")) {

                String description = parts[2];
                String from = parts[3];
                String to = parts[4];

                task = new Event(description, from, to);

            } else {
                continue;
            }

            if (isDone) {
                task.markAsDone();
            }

            tasks[taskCount] = task;
            taskCount++;
        }

        return taskCount;
    }

    public void saveTasks(Task[] tasks, int taskCount) throws IOException {

        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
        }

        StringBuilder data = new StringBuilder();

        for (int i = 0; i < taskCount; i++) {

            Task task = tasks[i];
            String done = task.isDone ? "1" : "0";

            if (task instanceof Todo) {

                data.append("T | ")
                        .append(done)
                        .append(" | ")
                        .append(task.description);

            } else if (task instanceof Deadline) {

                Deadline deadline = (Deadline) task;

                data.append("D | ")
                        .append(done)
                        .append(" | ")
                        .append(deadline.description)
                        .append(" | ")
                        .append(deadline.by);

            } else if (task instanceof Event) {

                Event event = (Event) task;

                data.append("E | ")
                        .append(done)
                        .append(" | ")
                        .append(event.description)
                        .append(" | ")
                        .append(event.from)
                        .append(" | ")
                        .append(event.to);
            }

            data.append(System.lineSeparator());
        }

        Files.writeString(filePath, data.toString());
    }
}