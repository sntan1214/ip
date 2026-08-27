package lebron;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {

    @Test
    public void delete_existingTask_removesCorrectTask() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("read book"));
        tasks.add(new Todo("play basketball"));

        Task deletedTask = tasks.delete(1);

        assertEquals(
                "[T][ ] read book",
                deletedTask.toString()
        );

        assertEquals(
                1,
                tasks.size()
        );

        assertEquals(
                "[T][ ] play basketball",
                tasks.get(0).toString()
        );
    }

    @Test
    public void mark_existingTask_marksCorrectTask() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("read book"));

        Task markedTask = tasks.mark(1);

        assertEquals(
                "[T][X] read book",
                markedTask.toString()
        );
    }
}