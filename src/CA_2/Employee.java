/**
 * Employee
 * 
 * Description: Represents an employee with detailed attributes based on the CSV file.
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 28/04/2025
 */
package CA_2;

import CA_2.DepartmentType;
import CA_2.PositionType;

public class Employee {

    // Employee personal and professional attributes
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private double salary;
    private DepartmentType department;
    private PositionType position;
    private String jobTitle;
    private String company;

    /*
    Description: Constructor
    Initializes all fields of the Employee object.

        Parameters:
            - firstName, lastName: name of the employee
            - gender: "Male" or "Female"
            - email: contact email
            - salary: numeric salary value
            - department: enum DepartmentType within the company
            - position: enum PositionType indicating the role (e.g., HEAD_MANAGER, INTERN)
            - jobTitle: the actual job title (e.g., "Java Developer")
            - company: company name the employee works for
    */
    public Employee(String firstName, String lastName, String gender, String email,
                    double salary, DepartmentType department, PositionType position, String jobTitle, String company) {
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

    /*
    Description: Getter methods
    These methods provide read-only access to the private fields.
    Used to retrieve individual pieces of employee data.
    */

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

    public DepartmentType getDepartment() {
        return department;
    }

    public PositionType getPosition() {
        return position;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompany() {
        return company;
    }

    /*
    Description: toString override
    Provides a human-readable string representation of the Employee object.
    Useful for debugging or printing lists of employees.

        Format: "FirstName LastName - JobTitle (Department) - Company"
    */
    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + jobTitle + " (" + department.name().replace("_", " ") + ") - " + company;
    }
}
