package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks;

    public static void main(String[] args) {
        loadData();
        showMenu();
        chooseMenuOption();
    }

    public static void wylistuj() {
        for (int i = 0; i < tasks.length; i++) {
            String lineTabString = "";
            for (int j = 0; j < tasks[i].length; j++) {
                lineTabString += " " + tasks[i][j];
            }
            System.out.println(i + " :" + lineTabString);
        }
    }


    private static void loadData() {
        Path path = Paths.get("tasks.csv");
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.print("Nie ma takiego pliku kurła");
            return;
        }
        tasks = new String[lines.toArray().length][3];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] lineTab = line.split(",");
            for (int j = 0; j < lineTab.length; j++) {
                tasks[i][j] = lineTab[j];
            }
        }
    }

    private static void showMenu() {
        System.out.println(ConsoleColors.BLUE + "Proszę wybierz opcję: ");
        System.out.print(ConsoleColors.RESET);
        String[] menu = {"add", "remove", "list", "exit"};
        for (String element : menu) {
            System.out.println(element);
        }
    }

    private static void chooseMenuOption() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String option = scanner.nextLine();
            switch (option) {
                case "list":
                    wylistuj();
                    showMenu();
                    chooseMenuOption();
                    break;
                case "add":
                    ;
                    add();
                    showMenu();
                    chooseMenuOption();
                    break;
                case "remove":
                    remove();
                    showMenu();
                    chooseMenuOption();
                    break;
                case "exit":
                    System.out.println("Wybrano opcje wyjdź");
                    exit();
                    break;
                default:
                    System.out.println("Wpisz poprawną opcje");
            }
        }
    }

    private static void add() {
        Scanner scanner = new Scanner(System.in);
        String taskName = null;
        String taskDate = null;
        String taskImportance = null;
        System.out.print("Podaj nazwę zadania");
        taskName = scanner.nextLine();
        System.out.print("Podaj date zadania");
        taskDate = scanner.nextLine();
        System.out.print("Czy zadanie jest ważne?");
        taskImportance = scanner.nextLine();
        String[][] newAdd = Arrays.copyOf(tasks, tasks.length + 1);
        newAdd[newAdd.length - 1] = new String[3];
        newAdd[newAdd.length - 1][0] = taskName;
        newAdd[newAdd.length - 1][1] = taskDate;
        newAdd[newAdd.length - 1][2] = taskImportance;
        tasks = newAdd;
        saveToFile();
    }

    private static void saveToFile() {
        Path path = Paths.get("tasks.csv");
        String[] lineTab = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            lineTab[i] = String.join(",", tasks[i]);
        }
        try {
            Files.write(path, Arrays.asList(lineTab));
        } catch (IOException e) {
            System.out.println("Nie udało się zapisać");
        }
    }

    private static void remove() {
        int nrWiersza;
        while (true) {
            System.out.println("Podaj numer zadania do skasowania");
            Scanner scanner = new Scanner(System.in);
            nrWiersza = scanner.nextInt();
            if (nrWiersza > tasks.length - 1) {
                System.out.println("Nie ma takiego zadania");
            } else {
                break;
            }
        }
        tasks = ArrayUtils.remove(tasks, nrWiersza);
        saveToFile();
    }

    private static void exit() {
        System.out.printf(ConsoleColors.RED + "Bye bye");
        System.exit(0);
    }
}