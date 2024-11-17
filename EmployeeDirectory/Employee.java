package EmployeeDirectory;

public class Employee {
    private static int idCounter = 0;

    private final int id;
    private String name;
    private String phoneNumber;
    private int experience;

    public Employee(String name, String phoneNumber, int experience) {
        this.id = ++idCounter;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.experience = experience;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', phoneNumber='%s', experience=%d}", id, name, phoneNumber, experience);
    }
}