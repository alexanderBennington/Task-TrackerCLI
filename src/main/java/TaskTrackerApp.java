import controller.TaskController;

import java.nio.file.*;
import java.util.Scanner;

public class TaskTrackerApp {
    private static final String FILE_NAME = "tasks.json";

    private static void ensureFileExists() {
        Path path = Paths.get(FILE_NAME);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
                Files.writeString(path, "[]");
                System.out.println("📂 File created: " + FILE_NAME);
            } else {
                System.out.println("📂 File exists: " + FILE_NAME);
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to create the JSON file: " + e.getMessage());
        }
    }

    private static void showWelcome(Scanner console) {
        TaskController taskC = new TaskController();
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
                ║  6. Update status             ║
                ║  7. Exit                      ║
                ╚═══════════════════════════════╝
                """ + RESET);
            try {
                System.out.print("Select an option: ");
                int option = Integer.parseInt(console.nextLine());
                exit = taskC.selectedOption(console, option);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number between 1 and 7.");
            }
        } while(!exit);
    }



    public static void main(String[] args) {
        ensureFileExists();
        Scanner console = new Scanner(System.in);
        showWelcome(console);
    }
}
