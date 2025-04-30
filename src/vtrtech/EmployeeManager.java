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

    // Search for an employee using binary search
public Employee searchEmployee(String fullName) {
    sortEmployees(); // Guarantee list is sorted
    return binarySearchEmployee(fullName, 0, employees.size() - 1);
}

private Employee binarySearchEmployee(String fullName, int left, int right) {
    if (left > right) {
        return null;
    }

    int mid = left + (right - left) / 2;
    Employee midEmployee = employees.get(mid);
    String midName = midEmployee.getFirstName() + " " + midEmployee.getLastName();

    int compare = midName.compareToIgnoreCase(fullName.trim());

    if (compare == 0) {
        return midEmployee;
    } else if (compare < 0) {
        return binarySearchEmployee(fullName, mid + 1, right);
    } else {
        return binarySearchEmployee(fullName, left, mid - 1);
    }
}


    public void sortEmployees() {
        Collections.sort(employees, Comparator.comparing(Employee::getFirstName).thenComparing(Employee::getLastName));
    }

    public void listAllEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public List<Employee> getTop20SortedEmployees() {
    List<Employee> sortedList = new ArrayList<>(employees);
    sortedList.sort(Comparator.comparing(e -> (e.getFirstName() + " " + e.getLastName())));
    return sortedList.subList(0, Math.min(20, sortedList.size()));
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

    
    public Employee generateRandomEmployee() {
        
    String[] firstNames = {"Abby", "Abdul", "Ada", "Addison", "Adelbert", "Adelina", "Adella", "Adolf", "Adriane", "Alex", "Alice", "Aaron", "Ava","Vitor","Hugo","Tainara","Carlos"};
    String[] lastNames = {"Lulham", "Siaskowski", "Blinkhorn", "Tamburo", "Ramsey", "Alderton", "Pattle", "Chrispin", "Johnson", "Smith", "Williams"};String[] domains = {"gmail.com", "yahoo.com", "outlook.com", "hotmail.com","icloud.com", "aol.com", "live.com"};String[] departments = {"IT Development", "Sales", "HR", "Finance","Marketing", "Accounting", "Operations","Technical Support", "Customer Service", "IT"};
    String[] positions = {"Senior", "Middle", "Intern", "Junior", "Contract", "Analyst"};
    String[] jobTitles = {"Java Developer", "HR Specialist", "Finance Analyst", "Marketing Coordinator", "Support Clerk"};
    String[] companies = {"VTR-TECH", "TechCorp", "InfoSphere", "CodeSolutions", "DevsUnited"};

    Random random = new Random();

    String firstName = firstNames[random.nextInt(firstNames.length)];
    String lastName = lastNames[random.nextInt(lastNames.length)];
    String gender = random.nextBoolean() ? "Male" : "Female";
    String email = (firstName.charAt(0) + lastName + random.nextInt(100) + "@" + domains[random.nextInt(domains.length)]).toLowerCase();
    double salary = 2500 + random.nextDouble() * 100000; // salário entre 2.5k e 100k
    String department = departments[random.nextInt(departments.length)];
    String position = positions[random.nextInt(positions.length)];
    String jobTitle = jobTitles[random.nextInt(jobTitles.length)];
    String company = companies[random.nextInt(companies.length)];

    Employee randomEmployee = new Employee(firstName, lastName, gender, email, salary, department, position, jobTitle, company);
    employees.add(randomEmployee);
    return randomEmployee;
}

    public List<Employee> getEmployees() {
        return employees;
    }
    public Employee searchEmployeeByFullName(String fullName) {
    sortEmployees();
    return binarySearchEmployee(fullName, 0, employees.size() - 1);
}
}


