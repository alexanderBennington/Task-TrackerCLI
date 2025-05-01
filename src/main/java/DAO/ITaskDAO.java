package DAO;

import model.Task;

import java.util.List;

public interface ITaskDAO {
    public boolean saveTask(Task task);

    public List<Task> getAllTasks();

    public Task getTaskById(int idTask);

    public boolean updateTask(Task task);

    public boolean deleteTaskById(int idTask);
}
