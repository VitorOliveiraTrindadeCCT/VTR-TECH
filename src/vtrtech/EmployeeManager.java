/**
 * EmployeeManager
 * 
 * Description: Manages a list of employees, allowing adding, searching, sorting, and generating random employees.
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 28/04/2025
 */
package vtrtech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class EmployeeManager {
    private List<Employee> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee searchEmployee(String name) {
        for (Employee employee : employees) {
            String fullName = employee.getFirstName() + " " + employee.getLastName();
            if (fullName.equalsIgnoreCase(name.trim())) {
                return employee;
            }
        }
        return null;
    }

    public void sortEmployees() {
        Collections.sort(employees, Comparator.comparing(Employee::getFirstName).thenComparing(Employee::getLastName));
    }

    public void listAllEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public void sortAndShowFirst20() {
    List<String> fullNames = new ArrayList<>();
    for (Employee e : employees) {
        fullNames.add(e.getFirstName() + " " + e.getLastName());
    }

    List<String> sortedNames = mergeSort(fullNames);

    System.out.println("Top 20 sorted names:");
    for (int i = 0; i < Math.min(20, sortedNames.size()); i++) {
        System.out.println((i + 1) + ". " + sortedNames.get(i));
    }
}

private List<String> mergeSort(List<String> list) {
    if (list.size() <= 1) {
        return list;
    }

    int mid = list.size() / 2;
    List<String> left = mergeSort(new ArrayList<>(list.subList(0, mid)));
    List<String> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())));

    return merge(left, right);
}

private List<String> merge(List<String> left, List<String> right) {
    List<String> result = new ArrayList<>();
    int i = 0, j = 0;

    while (i < left.size() && j < right.size()) {
        if (left.get(i).compareToIgnoreCase(right.get(j)) <= 0) {
            result.add(left.get(i++));
        } else {
            result.add(right.get(j++));
        }
    }

    while (i < left.size()) result.add(left.get(i++));
    while (j < right.size()) result.add(right.get(j++));

    return result;
}

    
    public void generateRandomEmployee() {
        String[] firstNames = {"John", "Jane", "Alice", "Bob", "Emily", "David"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Williams", "Jones", "Garcia"};
        String[] genders = {"Male", "Female"};
        String[] departments = {"IT", "HR", "Finance", "Marketing"};
        String[] positions = {"Manager", "Assistant", "Team Lead"};
        String[] jobTitles = {"Software Engineer", "HR Specialist", "Accountant", "Marketing Analyst"};
        String[] companies = {"VTR-TECH", "TechCorp", "InnovateX"};

        Random random = new Random();

        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String gender = genders[random.nextInt(genders.length)];
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";
        double salary = 30000 + (random.nextDouble() * 50000); // Between 30k and 80k
        String department = departments[random.nextInt(departments.length)];
        String position = positions[random.nextInt(positions.length)];
        String jobTitle = jobTitles[random.nextInt(jobTitles.length)];
        String company = companies[random.nextInt(companies.length)];

        Employee randomEmployee = new Employee(firstName, lastName, gender, email, salary, department, position, jobTitle, company);
        employees.add(randomEmployee);
    }
public void searchAndPrint(String fullName) {
    Employee found = searchEmployee(fullName);
    if (found != null) {
        System.out.println("Found: " + found);
    } else {
        System.out.println("Employee not found.");
    }
}
    public List<Employee> getEmployees() {
        return employees;
    }
}


