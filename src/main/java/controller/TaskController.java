package controller;

import DAO.ITaskDAO;
import DAO.TaskDAO;
import model.Status;
import model.Task;

import java.util.List;
import java.util.Scanner;

public class TaskController {
    public boolean selectedOption(Scanner console, int option){
        ITaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = taskDAO.getAllTasks();
        switch (option){
            case 1 -> {
                viewTasks(tasks);
            }
            case 2 -> {
                Task task = saveTaskOption(console);
                if (taskDAO.saveTask(task)) {
                    System.out.println("Updating JSON...");
                    System.out.println("✅ Task saved successfully!");
                    tasks = taskDAO.getAllTasks();
                    viewTasks(tasks);
                } else {
                    System.out.println("❌ Task not saved.");
                }
            }
            case 3 -> {
                viewTasks(tasks);
                Task task = updateTaskOption(console);
                if (taskDAO.updateTask(task)) {
                    System.out.println("Updating JSON...");
                    System.out.println("✅ Task updated successfully!");
                    tasks = taskDAO.getAllTasks();
                    viewTasks(tasks);
                } else {
                    System.out.println("❌ Task not updated.");
                }
            }
            case 4 -> {
                int idTask = 0;
                try {
                    viewTasks(tasks);
                    System.out.println("Enter task ID: ");
                    idTask = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input. Please enter a number.");
                }

                if(taskDAO.deleteTaskById(idTask)){
                    System.out.println("Updating JSON...");
                    System.out.println("✅ Task deleted successfully!");
                    tasks = taskDAO.getAllTasks();
                    viewTasks(tasks);
                } else {
                    System.out.println("❌ Task not deleted.");
                }
            }
            case 5 -> {
                int idTask = 0;
                Task task = null;
                try {
                    System.out.println("Enter task ID: ");
                    idTask = Integer.parseInt(console.nextLine());
                    task = taskDAO.getTaskById(idTask);
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input. Please enter a number.");
                }

                if(task == null){
                    System.out.println("⚠️ Task not found");
                    viewTasks(tasks);
                } else {
                    System.out.println(task);
                }
            }
            case 6 -> {
                int idTask = 0;
                Status status = null;
                try {
                    viewTasks(tasks);
                    System.out.println("Enter task ID: ");
                    idTask = Integer.parseInt(console.nextLine());
                    status = selectedStatus(console);
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input. Please enter a number.");
                }

                if(taskDAO.updateStatusById(idTask, status)){
                    System.out.println("Updating JSON...");
                    System.out.println("✅ Status updated successfully!");
                    tasks = taskDAO.getAllTasks();
                    viewTasks(tasks);
                } else {
                    System.out.println("❌ Status not updated.");
                }
            }
            case 7 -> {
                System.out.println("👋 Goodbye!");
                return true;
            }
            default -> System.out.println("⚠️ Please enter a number between 1 and 7.");
        }
        return false;
    }

    private static void viewTasks(List<Task> tasks){
        if (tasks.isEmpty()) {
            System.out.println("📭 No tasks found.");
        } else {
            tasks.forEach(System.out::println);
        }
        System.out.println();
    }

    private static Task saveTaskOption(Scanner console){
        String description;
        System.out.print("📝 Enter task description: ");
        description = console.nextLine();
        Status status = selectedStatus(console);
        return new Task(description, status);
    }

    private static Task updateTaskOption(Scanner console){
        System.out.println("ID of the task to be modified: ");
        int idtask = Integer.parseInt(console.nextLine());
        System.out.println("New Description: ");
        String description = console.nextLine();
        Status status = selectedStatus(console);
        return new Task(idtask, description, status);
    }

    private static Status selectedStatus(Scanner console){
        Status status = null;
        int progress;
        boolean validStatus = false;
        while (!validStatus){
            try {
                System.out.println("""
                            Status
                            1. TODO
                            2. IN_PROGRESS
                            3. DONE
                            """);
                System.out.print("Select: ");
                progress = Integer.parseInt(console.nextLine());
                switch (progress){
                    case 1 -> {
                        status = Status.TODO;
                        validStatus = true;
                    }
                    case 2 -> {
                        status = Status.IN_PROGRESS;
                        validStatus = true;
                    }
                    case 3 -> {
                        status = Status.DONE;
                        validStatus = true;
                    }
                    default -> System.out.println("⚠️ Please enter a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number between 1 and 3.");
            }
        }
        return status;
    }
}
