import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Storage {

    private final Path filePath;

    public Storage(String folderName, String fileName) {
        this.filePath =
                Path.of(folderName, fileName);
    }

    public TaskList loadTasks() throws IOException {

        if (filePath.getParent() != null) {
            Files.createDirectories(
                    filePath.getParent()
            );
        }

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            return new TaskList();
        }

        List<String> lines =
                Files.readAllLines(filePath);

        TaskList tasks =
                new TaskList();

        for (String line : lines) {

            if (line.isBlank()) {
                continue;
            }

            String[] parts =
                    line.split(" \\| ", -1);

            String taskType =
                    parts[0];

            boolean isDone =
                    parts[1].equals("1");

            Task task;

            if (taskType.equals("T")) {

                task =
                        new Todo(parts[2]);

            } else if (taskType.equals("D")) {

                task =
                        new Deadline(
                                parts[2],
                                parts[3]
                        );

            } else if (taskType.equals("E")) {

                task =
                        new Event(
                                parts[2],
                                parts[3],
                                parts[4]
                        );

            } else {
                continue;
            }

            if (isDone) {
                task.markAsDone();
            }

            tasks.add(task);
        }

        return tasks;
    }

    public void saveTasks(TaskList tasks)
            throws IOException {

        if (filePath.getParent() != null) {
            Files.createDirectories(
                    filePath.getParent()
            );
        }

        StringBuilder data =
                new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {

            Task task =
                    tasks.get(i);

            String done =
                    task.isDone ? "1" : "0";

            if (task instanceof Todo) {

                data.append("T | ")
                        .append(done)
                        .append(" | ")
                        .append(task.description);

            } else if (task instanceof Deadline) {

                Deadline deadline =
                        (Deadline) task;

                data.append("D | ")
                        .append(done)
                        .append(" | ")
                        .append(deadline.description)
                        .append(" | ")
                        .append(deadline.by);

            } else if (task instanceof Event) {

                Event event =
                        (Event) task;

                data.append("E | ")
                        .append(done)
                        .append(" | ")
                        .append(event.description)
                        .append(" | ")
                        .append(event.from)
                        .append(" | ")
                        .append(event.to);
            }

            data.append(
                    System.lineSeparator()
            );
        }

        Files.writeString(
                filePath,
                data.toString()
        );
    }
}