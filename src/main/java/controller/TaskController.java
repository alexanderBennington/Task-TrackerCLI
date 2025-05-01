package controller;

import DAO.ITaskDAO;
import DAO.TaskDAO;
import model.Status;
import model.Task;
import utils.AnsiColors;

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
                    System.out.println(AnsiColors.BLUE + "✅ Task saved successfully!" + AnsiColors.RESET);
                    tasks = taskDAO.getAllTasks();
                    viewTasks(tasks);
                } else {
                    System.out.println(AnsiColors.RED + "❌ Task not saved." + AnsiColors.RESET);
                }
            }
            case 3 -> {
                viewTasks(tasks);
                Task task = updateTaskOption(console);
                if (taskDAO.updateTask(task)) {
                    System.out.println("Updating JSON...");
                    System.out.println(AnsiColors.BLUE + "✅ Task updated successfully!" + AnsiColors.RESET);
                    tasks = taskDAO.getAllTasks();
                    viewTasks(tasks);
                } else {
                    System.out.println(AnsiColors.RED + "❌ Task not updated." + AnsiColors.RESET);
                }
            }
            case 4 -> {
                viewTasks(tasks);
                int idTask = enterIdTask(console);

                if(taskDAO.deleteTaskById(idTask)){
                    System.out.println("Updating JSON...");
                    System.out.println(AnsiColors.BLUE + "✅ Task deleted successfully!" + AnsiColors.RESET);
                    tasks = taskDAO.getAllTasks();
                    viewTasks(tasks);
                } else {
                    System.out.println(AnsiColors.RED + "❌ Task not deleted." + AnsiColors.RESET);
                }
            }
            case 5 -> {
                int idTask = enterIdTask(console);
                Task task = taskDAO.getTaskById(idTask);

                if(task == null){
                    System.out.println(AnsiColors.RED + "⚠️ Task not found" + AnsiColors.RESET);
                    viewTasks(tasks);
                } else {
                    System.out.println(task);
                }
            }
            case 6 -> {
                viewTasks(tasks);
                int idTask = enterIdTask(console);
                Status status = selectedStatus(console);

                if(taskDAO.updateStatusById(idTask, status)){
                    System.out.println("Updating JSON...");
                    System.out.println(AnsiColors.BLUE + "✅ Status updated successfully!" + AnsiColors.RESET);
                    tasks = taskDAO.getAllTasks();
                    viewTasks(tasks);
                } else {
                    System.out.println(AnsiColors.RED + "❌ Status not updated." + AnsiColors.RESET);
                }
            }
            case 7 -> {
                List<Task> todoTasks = taskDAO.getTodoTasks();
                if (todoTasks.isEmpty()) {
                    System.out.println(AnsiColors.RED + "📭 No tasks found." + AnsiColors.RESET);
                } else {
                    todoTasks.forEach(System.out::println);
                }
                System.out.println();
            }
            case 8 -> {
                List<Task> progressTasks = taskDAO.getProgressTasks();
                if (progressTasks.isEmpty()) {
                    System.out.println(AnsiColors.RED + "📭 No tasks found." + AnsiColors.RESET);
                } else {
                    progressTasks.forEach(System.out::println);
                }
                System.out.println();
            }
            case 9 -> {
                List<Task> doneTasks = taskDAO.getDoneTasks();
                if (doneTasks.isEmpty()) {
                    System.out.println(AnsiColors.RED + "📭 No tasks found." + AnsiColors.RESET);
                } else {
                    doneTasks.forEach(System.out::println);
                }
                System.out.println();
            }
            case 10 -> {
                System.out.println("👋 Goodbye!");
                return true;
            }
            default -> System.out.println(AnsiColors.RED + "⚠️ Please enter a number between 1 and 10." + AnsiColors.RESET);
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
        int idTask = enterIdTask(console);
        System.out.println("New Description: ");
        String description = console.nextLine();
        Status status = selectedStatus(console);
        return new Task(idTask, description, status);
    }

    private static int enterIdTask(Scanner console){
        int idtask = 0;
        boolean isNumber = false;
        while(!isNumber){
            try{
                System.out.println("Enter ID: ");
                idtask = Integer.parseInt(console.nextLine());
                isNumber = true;
            }catch (NumberFormatException e) {
                System.out.println(AnsiColors.RED + "❌ Invalid input. Please enter a number" + AnsiColors.RESET);
            }
        }
        return idtask;
    }

    private static Status selectedStatus(Scanner console){
        Status status = null;
        int progress;
        boolean validStatus = false;
        while (!validStatus){
            try {
                System.out.println(AnsiColors.CYAN + """
                            Status
                            1. TODO
                            2. IN_PROGRESS
                            3. DONE
                            """ + AnsiColors.RESET);
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
                    default -> System.out.println(AnsiColors.RED + "⚠️ Please enter a number between 1 and 3." + AnsiColors.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(AnsiColors.RED + "❌ Invalid input. Please enter a number between 1 and 3." + AnsiColors.RESET);
            }
        }
        return status;
    }
}
