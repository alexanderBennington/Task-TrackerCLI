import controller.TaskController;
import utils.AnsiColors;

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
            System.out.println(AnsiColors.RED + "❌ Failed to create the JSON file: " + e.getMessage() + AnsiColors.RESET);
        }
    }

    private static void showWelcome(Scanner console) {
        TaskController taskC = new TaskController();
        boolean exit = false;

        System.out.println(AnsiColors.BLUE + """
                
                
                ___________              __     ___________                     __                \s
                \\__    ___/____    _____|  | __ \\__    ___/___________    ____ |  | __ ___________\s
                  |    |  \\__  \\  /  ___/  |/ /   |    |  \\_  __ \\__  \\ _/ ___\\|  |/ // __ \\_  __ \\
                  |    |   / __ \\_\\___ \\|    <    |    |   |  | \\// __ \\\\  \\___|    <\\  ___/|  | \\/
                  |____|  (____  /____  >__|_ \\   |____|   |__|  (____  /\\___  >__|_ \\\\___  >__|  \s
                               \\/     \\/     \\/                       \\/     \\/     \\/    \\/      \s
                
                
                """ + AnsiColors.RESET);

        System.out.println(AnsiColors.CYAN + "🔧 Welcome to Task Tracker CLI" + AnsiColors.RESET);
        System.out.println(AnsiColors.GREEN + "📅 Organize your tasks easily from Command Line Interface." + AnsiColors.RESET);
        do {
            System.out.println(AnsiColors.YELLOW + """
                ╔═══════════════════════════════╗
                ║  1. View all tasks            ║
                ║  2. Create a new task         ║
                ║  3. Edit task                 ║
                ║  4. Delete task               ║
                ║  5. Search for a task by ID   ║
                ║  6. Update status             ║
                ║  7. View pending tasks        ║
                ║  8. View tasks in progress    ║
                ║  9. View completed tasks      ║
                ║  10. Exit                     ║
                ╚═══════════════════════════════╝
                """ + AnsiColors.RESET);
            try {
                System.out.print("Select an option: ");
                int option = Integer.parseInt(console.nextLine());
                exit = taskC.selectedOption(console, option);
            } catch (NumberFormatException e) {
                System.out.println(AnsiColors.RED + "❌ Invalid input. Please enter a number between 1 and 10." + AnsiColors.RESET);
            }
        } while(!exit);
    }



    public static void main(String[] args) {
        ensureFileExists();
        Scanner console = new Scanner(System.in);
        showWelcome(console);
    }
}
