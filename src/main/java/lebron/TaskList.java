package lebron;

public class TaskList {

    private final Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[100];
        this.taskCount = 0;
    }

    public void add(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    public Task get(int index) {
        return tasks[index];
    }

    public int size() {
        return taskCount;
    }

    public Task mark(int taskNumber) {
        Task task = tasks[taskNumber - 1];
        task.markAsDone();
        return task;
    }

    public Task delete(int taskNumber) {
        int index = taskNumber - 1;
        Task deletedTask = tasks[index];

        for (int i = index; i < taskCount - 1; i++) {
            tasks[i] = tasks[i + 1];
        }

        tasks[taskCount - 1] = null;
        taskCount--;

        return deletedTask;
    }

    public TaskList find(String keyword) {
        TaskList matchingTasks = new TaskList();

        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].description.toLowerCase()
                    .contains(keyword.toLowerCase())) {
                matchingTasks.add(tasks[i]);
            }
        }

        return matchingTasks;
    }
}