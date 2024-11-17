package EmployeeDirectory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDirectory {
    private final Map<Integer, Employee> employeeMap;

    public EmployeeDirectory() {
        this.employeeMap = new HashMap<>();
    }

    public List<Employee> findEmployeesByExperience(int expectedExperience) {
        return employeeMap.values().stream()
                .filter(employee -> employee.getExperience() == expectedExperience)
                .collect(Collectors.toList());
    }

    public List<String> findPhoneNumbersByName(String name) {
        return employeeMap.values().stream()
                .filter(employee -> employee.getName().equals(name))
                .map(employee -> String.format("Имя: %s, Телефон: %s", employee.getName(), employee.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    public Employee findEmployeeById(int employeeId) {
        return employeeMap.get(employeeId);
    }

    public boolean addEmployee(Employee employee) {
        if (employeeMap.containsKey(employee.getId())) {
            return false; // Employee with this ID already exists
        }
        employeeMap.put(employee.getId(), employee);
        return true;
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Employee employee : employeeMap.values()) {
                writer.write(String.format("%d,%s,%s,%d%n", employee.getId(), employee.getName(), employee.getPhoneNumber(), employee.getExperience()));
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String phoneNumber = parts[2];
                    int experience = Integer.parseInt(parts[3]);
                    Employee employee = new Employee(name, phoneNumber, experience);
                    employeeMap.put(id, employee);
                }
            }
        }
    }
}