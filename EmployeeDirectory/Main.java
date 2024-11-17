package EmployeeDirectory;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeDirectory employeeDirectory = new EmployeeDirectory();
        Scanner scanner = new Scanner(System.in);
        String filename = "employees.csv";

        // Load existing employees from file
        try {
            employeeDirectory.loadFromFile(filename);
        } catch (IOException e) {
            System.out.println("Ошибка загрузки данных: " + e.getMessage());
        }

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Добавить сотрудника");
            System.out.println("2. Найти сотрудника по табельному номеру");
            System.out.println("3. Найти телефоны сотрудников по имени");
            System.out.println("4. Найти сотрудников по стажу");
            System.out.println("5. Выход из программы");
            System.out.print("Выбранный пункт меню: ");

            int option = 0;
            boolean validInput = false;

            while (!validInput) {
                try {
                    option = scanner.nextInt();
                    validInput = true; // Успешный ввод, выходим из цикла
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка: введите номер пункта меню в числовом формате.");
                    scanner.next(); // Очищаем некорректный ввод
                }
            }
            scanner.nextLine(); // Считываем оставшийся символ новой строки

            switch (option) {
                case 1:
                    addEmployee(employeeDirectory, scanner);
                    break;
                case 2:
                    findEmployeeById(employeeDirectory, scanner);
                    break;
                case 3:
                    findEmployeesByName(employeeDirectory, scanner);
                    break;
                case 4:
                    findEmployeesByExperience(employeeDirectory, scanner);
                    break;
                case 5:
                    System.out.println("Выход...");
                    try {
                        employeeDirectory.saveToFile(filename);
                    } catch (IOException e) {
                        System.out.println("Ошибка сохранения данных: " + e.getMessage());
                    }
                    return;
                default:
                    System.out.println("Неверный выбранный пункт. Выберите верный пункт списка снова.");
            }
        }
    }

    private static void addEmployee(EmployeeDirectory employeeDirectory, Scanner scanner) {
        System.out.print("Введите имя сотрудника в формате через пробел Имя Фамилия Отчество: ");
        String name = scanner.nextLine();
        System.out.print("Введите телефонный номер сотрудника: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Введите стаж сотрудника как число лет: ");

        int experience = 0;
        while (true) {
            try {
                experience = scanner.nextInt();
                break; // Выходим из цикла, если ввод корректен
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите стаж в числовом формате.");
                scanner.next(); // Очищаем некорректный ввод
            }
        }

        Employee employee = new Employee(name, phoneNumber, experience);
        if (employeeDirectory.addEmployee(employee)) {
            System.out.println("Сотрудник успешно добавлен.");
        } else {
            System.out.println("Сотрудник с данным табельным номером уже существует.");
        }
    }

    private static void findEmployeeById(EmployeeDirectory employeeDirectory, Scanner scanner) {
        System.out.print("Введите табельный номер: ");

        int employeeId = 0;
        while (true) {
            try {
                employeeId = scanner.nextInt();
                break; // Выходим из цикла, если ввод корректен
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите табельный номер в числовом формате.");
                scanner.next(); // Очищаем некорректный ввод
            }
        }

        Employee employee = employeeDirectory.findEmployeeById(employeeId);
        if (employee != null) {
            System.out.println("Найден сотрудник: " + employee);
        } else {
            System.out.println("Сотрудник с таким табельным номером не найден.");
        }
    }

    private static void findEmployeesByName(EmployeeDirectory employeeDirectory, Scanner scanner) {
        System.out.print("Введите имя сотрудника: ");
        String name = scanner.nextLine();
        List<String> phoneNumbers = employeeDirectory.findPhoneNumbersByName(name);
        if (!phoneNumbers .isEmpty()) {
            System.out.println("Найдены номера телефонов: " + phoneNumbers);
        } else {
            System.out.println("Не найдено сотрудников с таким именем.");
        }
    }

    private static void findEmployeesByExperience(EmployeeDirectory employeeDirectory, Scanner scanner) {
        System.out.print("Введите стаж работы сотрудника: ");

        int experience = 0;
        while (true) {
            try {
                experience = scanner.nextInt();
                break; // Выходим из цикла, если ввод корректен
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите стаж в числовом формате.");
                scanner.next(); // Очищаем некорректный ввод
            }
        }

        List<Employee> employees = employeeDirectory.findEmployeesByExperience(experience);
        if (!employees.isEmpty()) {
            System.out.println("Найдены сотрудники: " + employees);
        } else {
            System.out.println("Не найдено сотрудников с таким стажем работы.");
        }
    }
}