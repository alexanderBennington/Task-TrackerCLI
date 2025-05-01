package DAO;

import model.Task;

import java.util.List;

public interface ITaskDAO {
    public void saveTask(Task task);

    public List<Task> getAllTasks();

    public Task getTaskById(int idTask);

    public void updateTask(Task task);

    public void deleteTaskById(int idTask);
}
