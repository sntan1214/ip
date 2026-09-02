package lebron;

/**
 * Manages the collection of tasks stored by the chatbot.
 */
public class TaskList {

    private final Task[] tasks;
    private int taskCount;

    /**
     * Creates an empty task list with space for up to 100 tasks.
     */
    public TaskList() {
        this.tasks = new Task[100];
        this.taskCount = 0;
    }

    /**
     * Adds a task to the end of the task list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    /**
     * Returns the task at the specified zero-based index.
     *
     * @param index zero-based index of the task
     * @return task at the specified index
     */
    public Task get(int index) {
        return tasks[index];
    }

    /**
     * Returns the number of tasks currently stored.
     *
     * @return number of tasks in the list
     */
    public int size() {
        return taskCount;
    }

    /**
     * Marks the specified task as done.
     *
     * @param taskNumber one-based task number
     * @return task that was marked as done
     */
    public Task mark(int taskNumber) {
        Task task = tasks[taskNumber - 1];
        task.markAsDone();
        return task;
    }

    /**
     * Deletes the specified task from the task list.
     * Remaining tasks are shifted forward to close the gap.
     *
     * @param taskNumber one-based task number
     * @return task that was deleted
     */
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

    /**
     * Finds all tasks whose descriptions contain the given keyword.
     *
     * @param keyword keyword to search for
     * @return task list containing all matching tasks
     */
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
