import DAO.ITaskDAO;
import DAO.TaskDAO;
import model.Status;
import model.Task;

import java.nio.file.*;
import java.util.List;
import java.util.Scanner;

public class TaskTrackerApp {
    private static final String FILE_NAME = "tasks.json";

    public static void ensureFileExists() {
        Path path = Paths.get(FILE_NAME);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                Files.writeString(path, "[]");
                System.out.println("📂 File created: " + FILE_NAME);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to create the JSON file: " + e.getMessage());
        }
    }

    public static void showWelcome(Scanner console) {
        boolean exit = false;
        String RESET = "\u001B[0m";
        String BLUE = "\u001B[34m";
        String GREEN = "\u001B[32m";
        String CYAN = "\u001B[36m";
        String YELLOW = "\u001B[33m";

        System.out.println(BLUE + """
                
                
                ___________              __     ___________                     __                \s
                \\__    ___/____    _____|  | __ \\__    ___/___________    ____ |  | __ ___________\s
                  |    |  \\__  \\  /  ___/  |/ /   |    |  \\_  __ \\__  \\ _/ ___\\|  |/ // __ \\_  __ \\
                  |    |   / __ \\_\\___ \\|    <    |    |   |  | \\// __ \\\\  \\___|    <\\  ___/|  | \\/
                  |____|  (____  /____  >__|_ \\   |____|   |__|  (____  /\\___  >__|_ \\\\___  >__|  \s
                               \\/     \\/     \\/                       \\/     \\/     \\/    \\/      \s
                
                
                """ + RESET);

        System.out.println(CYAN + "🔧 Welcome to Task Tracker CLI" + RESET);
        System.out.println(GREEN + "📅 Organize your tasks easily from Command Line Interface." + RESET);
        do {
            System.out.println(YELLOW + """
                ╔═══════════════════════════════╗
                ║  1. View all tasks            ║
                ║  2. Create a new task         ║
                ║  3. Edit task                 ║
                ║  4. Delete task               ║
                ║  5. Search for a task by ID   ║
                ║  6. Exit                      ║
                ╚═══════════════════════════════╝
                """ + RESET);
            try {
                System.out.print("Select an option: ");
                int option = Integer.parseInt(console.nextLine());
                exit = selectedOption(console, option);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number between 1 and 6.");
            }
        } while(!exit);
    }

    public static boolean selectedOption(Scanner console, int option){
        ITaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = taskDAO.getAllTasks();
        switch (option){
            case 1 -> {
                if (tasks.isEmpty()) {
                    System.out.println("📭 No tasks found.");
                } else {
                    tasks.forEach(System.out::println);
                }
                System.out.println();
            }
            case 2 -> {
                Task task = saveTaskOption(console);
                if (taskDAO.saveTask(task)) {
                    System.out.println("✅ Task saved successfully!");
                } else {
                    System.out.println("❌ Task not saved.");
                }
            }
            case 3 -> {}
            case 4 -> {}
            case 5 -> {}
            case 6 -> {
                System.out.println("👋 Goodbye!");
                return true;
            }
            default -> System.out.println("⚠️ Please enter a number between 1 and 6.");
        }
        return false;
    }

    private static Task saveTaskOption(Scanner console){
        String description;
        Status status = null;
        int progress;
        boolean validStatus = false;

        System.out.print("📝 Enter task description: ");
        description = console.nextLine();

        while (!validStatus){
            try {
                System.out.println("""
                            Status (Select 1, 2 or 3)
                            1. TODO
                            2. IN_PROGRESS
                            3. DONE
                            """);
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
        return new Task(description, status);
    }

    public static void main(String[] args) {
        ensureFileExists();
        Scanner console = new Scanner(System.in);
        showWelcome(console);
    }
}
