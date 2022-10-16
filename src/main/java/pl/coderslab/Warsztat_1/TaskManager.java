package pl.coderslab.Warsztat_1;


import org.apache.commons.lang3.ArrayUtils;
import pl.coderslab.Warsztat_1.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TaskManager {
    static final String TASK_FILE = "tasks.csv";
    static String[][] tasks = new String[0][];
    private static Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public static void main(String[] args) {
        tasks = downloadFromFile();
        menuList();
        menuOperations(tasks);
    }

    private static void menuList() {
        System.out.println(ConsoleColors.BLUE + "Please select an option: ");
        String[] menuOptions = {"add", "remove", "list", "exit"};
        for (String el : menuOptions) {
            System.out.println(ConsoleColors.WHITE + el);
        }
    }

    private static void menuOperations(String[][] tasks) {
        System.out.println("Wybierz operację");
        Scanner scanner = new Scanner(System.in);
        String operation = scanner.nextLine();

        for (; ; ) {
            switch (operation) {
                case "add":
                    addTask(tasks);
                    break;
                case "remove":
                    removeTask(tasks);
                    break;
                case "list":
                    listTasks(tasks);
                    break;
                case "exit":
                    exit(tasks);
                    break;
                default:
                    System.out.println("Podano niewłaściwą nazwę operacji");
                    menuOperations(tasks);
            }
        }
    }

    private static void exit(String[][] tasks) {
        saveTabToFile(TASK_FILE, tasks);
        System.out.println(ConsoleColors.RED + "Bye, bye.");
        System.exit(0);
    }

    private static void listTasks(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(i + ": " + tasks[i][j] + ", ");
            }
            System.out.println();
        }
        System.out.println();
        menuOperations(tasks);
    }

    private static String[][] removeTask(String[][] tasks) {
        System.out.println("Please select number to remove");
        Scanner scanner = new Scanner(System.in);

        for (; ; ) {
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Podana liczba jest nieprawidłowa. Spróbuj jeszcze raz:");
            }
            int taskToRemove = scanner.nextInt();
            if (taskToRemove > 0 && taskToRemove < tasks.length) {
                try {
                    tasks = ArrayUtils.remove(tasks, taskToRemove);
                    break;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Podano wartość spoza zakresu tablicy");
                }
            } else {
                System.out.println("Podana wartość jest nieprawidłowa");
            }
        }

        System.out.println("Prawidłowo usunięto element z tablicy");
        menuOperations(tasks);
        return tasks;
    }

    private static String[][] addTask(String[][] tasks) {
        Scanner scanner = new Scanner(System.in);
        String taskDescription = "";
        String taskDueDate = "";
        String taskImportance = "";

        System.out.println("Please add task description");
        taskDescription = scanner.nextLine();

        System.out.println("Please add task due date");
        taskDueDate = scanner.nextLine();

        while (true) {
            if (matches(taskDueDate) == true) {
                break;
            } else {
                System.out.println("Podana wartość nie jest datą. Podaj jeszcze raz: ");
                taskDueDate = scanner.nextLine();
            }
        }

        System.out.println("Is your task important: true/false");
        taskImportance = scanner.nextLine();

        while (true) {
            if ("true".equals(taskImportance) || "false".equals(taskImportance)) {
                break;
            } else {
                System.out.println("Podana wartość nie jest prawidłowa. Wpisz true lub false:");
                taskImportance = scanner.nextLine();
            }
        }
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = taskDescription;
        tasks[tasks.length - 1][1] = taskDueDate;
        tasks[tasks.length - 1][2] = taskImportance;

        System.out.println("Prawidłowo dodano element do pliku");
        menuOperations(tasks);
        return tasks;
    }


    private static String[][] downloadFromFile() {
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

        return tasks;
    }

    public static boolean matches(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }

    public static void saveTabToFile(String fileName, String[][] tasks) {
        Path dir = Paths.get(fileName);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            lines[i] = String.join(",", tasks[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
