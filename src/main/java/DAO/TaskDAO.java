package DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements ITaskDAO{
    private static final String FILE_NAME = "tasks.json";
    private final ObjectMapper objectMapper = new ObjectMapper();


    private int generateNextId(List<Task> tasks) {
        return tasks.stream()
                .mapToInt(Task::getIdTask)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void saveTask(Task task) {
        List<Task> tasks = getAllTasks();
        task.setIdTask(generateNextId(tasks));
        tasks.add(task);
        writeTasksToFile(tasks);
    }

    @Override
    public List<Task> getAllTasks() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Task getTaskById(int idTask) {
        return getAllTasks().stream()
                .filter(task -> task.getIdTask() == idTask)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateTask(Task task) {
        List<Task> tasks = getAllTasks();
        for (Task t : tasks) {
            if (t.getIdTask() == task.getIdTask()) {
                t.setDescription(task.getDescription());
                t.setStatus(task.getStatus());
                t.setUpdatedAt();
                break;
            }
        }
        writeTasksToFile(tasks);
    }

    @Override
    public void deleteTaskById(int idTask) {
        List<Task> tasks = getAllTasks();
        tasks.removeIf(t -> t.getIdTask() == idTask);
        writeTasksToFile(tasks);
    }

    private void writeTasksToFile(List<Task> tasks) {
        try {
            objectMapper.writeValue(new File(FILE_NAME), tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
