package pl.coderslab;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    static final String TASK_FILE = "tasks.csv";

    public static void main(String[] args) {
        menuList();
        downloadFromFile();
    }

    private static void menuList() {
        System.out.println(ConsoleColors.BLUE + "Please select an option: ");
        String[] menuOptions = {"add", "remove", "list", "exit"};
        for (String el : menuOptions) {
            System.out.println(ConsoleColors.WHITE + el);
        }
    }

    private static void menuOperations(String operation) {
        switch (operation) {
            case "add":
                addTask();
                break;
            case "remove":
                removeTask();
                break;
            case "list":
                listTasks();
                break;
            case "exit":
                exit();
                break;
            default:
                System.out.println("Podano niewłaściwą nazwę operacji");
                break;
        }
    }

    private static void exit() {
    }

    private static void listTasks() {

    }

    private static void removeTask() {

    }

    private static void addTask() {

    }

    private static String[][] downloadFromFile() {
        String[][] tasks = new String[0][];
        File file = new File(TASK_FILE);

        try (Scanner scanner = new Scanner(file)) {
            int i = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[tasks.length - 1] = line.split(", ");
                i++;

            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku");
        }
/*        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] +", ");
            }
            System.out.println();
        }*/
        return tasks;
    }
}
