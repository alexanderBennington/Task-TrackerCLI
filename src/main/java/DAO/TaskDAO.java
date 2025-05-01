package DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDAO implements ITaskDAO{
    private static final Logger LOGGER = Logger.getLogger(TaskDAO.class.getName());
    private static final String FILE_NAME = "tasks.json";
    private final ObjectMapper objectMapper;

    public TaskDAO() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private int generateNextId(List<Task> tasks) {
        return tasks.stream()
                .mapToInt(Task::getIdTask)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public boolean saveTask(Task task) {
        try {
            List<Task> tasks = getAllTasks();
            task.setIdTask(generateNextId(tasks));
            tasks.add(task);
            writeTasksToFile(tasks);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Failed to save task: " + e.getMessage());
            return false;
        }
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
            LOGGER.log(Level.WARNING, "Failed to read the JSON file", e);
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
    public boolean updateTask(Task task) {
        List<Task> tasks = getAllTasks();
        boolean updated = false;

        for (Task t : tasks) {
            if (t.getIdTask() == task.getIdTask()) {
                t.setDescription(task.getDescription());
                t.setStatus(task.getStatus());
                t.setUpdatedAt();
                updated = true;
                break;
            }
        }

        if (updated) {
            writeTasksToFile(tasks);
        }

        return updated;
    }

    @Override
    public boolean deleteTaskById(int idTask) {
        List<Task> tasks = getAllTasks();
        int originalSize = tasks.size();

        tasks.removeIf(t -> t.getIdTask() == idTask);

        if (tasks.size() < originalSize) {
            writeTasksToFile(tasks);
            return true;
        } else {
            return false;
        }
    }

    private void writeTasksToFile(List<Task> tasks) {
        try {
            objectMapper.writeValue(new File(FILE_NAME), tasks);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Filed to write the JSON file", e);
        }
    }
}
