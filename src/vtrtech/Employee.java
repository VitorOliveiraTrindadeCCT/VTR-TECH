/**
 * Employee
 * 
 * Description: Represents an employee with detailed attributes based on the CSV file.
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 28/04/2025
 */
package vtrtech;

public class Employee {
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private double salary;
    private String department;
    private String position;
    private String jobTitle;
    private String company;

    // Constructor
    public Employee(String firstName, String lastName, String gender, String email,
                    double salary, String department, String position, String jobTitle, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.salary = salary;
        this.department = department;
        this.position = position;
        this.jobTitle = jobTitle;
        this.company = company;
    }

    // Getters (no need for setters for now, only reading data)
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + jobTitle + " (" + department + ") - " + company;
    }
}
