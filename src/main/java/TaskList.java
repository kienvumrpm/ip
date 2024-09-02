import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;
    public TaskList() { this.tasks = new ArrayList<>(); }

    public TaskList(List<Task> tasks) { this.tasks = tasks; }

    public int size() { return tasks.size(); }

    public Task get(int i) { return tasks.get(i); }

    private void set(int i, Task task) { tasks.set(i, task); }

    public void add(Task task) { tasks.add(task); }

    public Task mark(int i) throws TaskListException {
        if (i < 0 || i >= tasks.size()) {
            throw new TaskListException("Idx out of bound for mark command");
        }
        Task task = get(i);
        task.mark();
        set(i, task);
        return task;
    }

    public Task unmark(int i) throws TaskListException {
        if (i < 0 || i >= tasks.size()) {
            throw new TaskListException("Idx out of bound for unmark command");
        }
        Task task = get(i);
        task.unmark();
        set(i, task);
        return task;
    }

    public Task delete(int i) throws TaskListException {
        if (i < 0 || i >= tasks.size()) {
            throw new TaskListException("Idx out of bound for delete command");
        }
        Task task = tasks.get(i);
        tasks.remove(i); 
        return task;
    }
};