package DAO;

import model.Status;
import model.Task;

import java.util.List;

public interface ITaskDAO {
    boolean saveTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(int idTask);

    boolean updateTask(Task task);

    boolean deleteTaskById(int idTask);

    boolean updateStatusById(int idTask, Status status);

    List<Task> getTodoTasks();

    List<Task> getProgressTasks();

    List<Task> getDoneTasks();
}
