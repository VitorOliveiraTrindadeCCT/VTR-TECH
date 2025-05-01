/**
 * EmployeeManager
 * 
 * Description: Manages a list of employees, allowing adding, searching, sorting, and generating random employees.
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 28/04/2025 (Atualizado para uso com enums)
 */
package CA_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class EmployeeManager {
    private List<Employee> employees;

    /*
    Description: Constructor
    Initializes the internal employee list when an EmployeeManager instance is created.
    */
    public EmployeeManager() {
        employees = new ArrayList<>();
    }

    /*
    Description: addEmployee
    Adds a new Employee object to the internal list.
    
        Parameters:
            - employee: the Employee to add

        Purpose:
            - Used when importing, creating, or generating a new employee.
    */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /*
    Description:  searchEmployeeByFullName
    Searches for an employee by full name using binary search.

        Logic:
            - First sorts the employee list.
            - Then performs a recursive binary search on the sorted list.

        Purpose:
            - Provides a fast search for a specific employee by their full name.
    */


    public Employee searchEmployeeByFullName(String fullName) {
        sortEmployees(); // Ensures binary search will work correctly
        return binarySearchEmployee(fullName, 0, employees.size() - 1);
    }

    /*
    Description: binarySearchEmployee
    Performs binary search recursively on the sorted employee list to find a match by full name.
    
        Parameters:
            - fullName: The full name to search
            - left, right: Index bounds for binary search

        Returns:
            - Employee object if found, otherwise null
    */
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

    /*
    Description: sortEmployees
    Sorts the employee list alphabetically by first name, then by last name.

        Purpose:
            - Ensures predictable order for displaying, searching, and reporting.
    */
    public void sortEmployees() {
        Collections.sort(employees, Comparator.comparing(Employee::getFirstName).thenComparing(Employee::getLastName));
    }

    /*
    Description: listAllEmployees
    Prints all employees to the console (used for debugging or testing).
    */
    public void listAllEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    /*
    Description: insertionSortEmployees
    Sorts the employee list in alphabetical order by full name (first name + last name),
    using the Insertion Sort algorithm.

        Logic:
            - Starts from the second employee in the list.
            - Compares the full name (case-insensitive) with previous elements.
            - Shifts larger elements one position forward to create space.
            - Inserts the selected employee (key) in the correct sorted position.
            - Repeats the process for all elements until the list is fully sorted.

        Purpose:
            - Implements a simple sorting algorithm introduced in class.
            - Replaces the use of built-in or advanced sorts like MergeSort.
            - Ensures the list is correctly ordered for binary search and display operations.
    */
    public void insertionSortEmployees() {
        for (int i = 1; i < employees.size(); i++) {
            Employee key = employees.get(i);
            int j = i - 1;

            String keyFullName = key.getFirstName() + " " + key.getLastName();

            while (j >= 0) {
                Employee current = employees.get(j);
                String currentFullName = current.getFirstName() + " " + current.getLastName();

                if (currentFullName.compareToIgnoreCase(keyFullName) > 0) {
                    employees.set(j + 1, current);
                    j--;
                } else {
                    break;
                }
            }

            employees.set(j + 1, key);
        }
    }

    /*
    Description: generateRandomEmployee
    Creates a new Employee object with randomized fields, adds it to the list, and returns it.

        Logic:
            - Selects random values from predefined arrays for names, email domains, job titles, companies.
            - Randomly selects values from enums DepartmentType and PositionType.
            - Constructs a new Employee and adds it to the internal list.

        Purpose:
            - Useful for testing or creating sample data automatically.
    */
    public Employee generateRandomEmployee() {
        String[] firstNames = {"Abby", "Abdul", "Ada", "Addison", "Adelbert", "Adelina", "Adella", "Adolf", "Adriane", "Alex", "Alice", "Aaron", "Ava", "Vitor", "Hugo", "Tainara", "Carlos"};
        String[] lastNames = {"Lulham", "Siaskowski", "Blinkhorn", "Tamburo", "Ramsey", "Alderton", "Pattle", "Chrispin", "Johnson", "Smith", "Williams"};
        String[] domains = {"gmail.com", "yahoo.com", "outlook.com", "hotmail.com", "icloud.com", "aol.com", "live.com"};
        String[] jobTitles = {"Java Developer", "HR Specialist", "Finance Analyst", "Marketing Coordinator", "Support Clerk"};
        String[] companies = {"VTR-TECH", "TechCorp", "InfoSphere", "CodeSolutions", "DevsUnited"};

        Random random = new Random();

        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String gender = random.nextBoolean() ? "Male" : "Female";
        String email = (firstName.charAt(0) + lastName + random.nextInt(100) + "@" + domains[random.nextInt(domains.length)]).toLowerCase();
        double salary = 2500 + random.nextDouble() * 100000;

        DepartmentType department = DepartmentType.values()[random.nextInt(DepartmentType.values().length)];
        PositionType position = PositionType.values()[random.nextInt(PositionType.values().length)];
        String jobTitle = jobTitles[random.nextInt(jobTitles.length)];
        String company = companies[random.nextInt(companies.length)];

        Employee randomEmployee = new Employee(firstName, lastName, gender, email, salary, department, position, jobTitle, company);
        employees.add(randomEmployee);
        return randomEmployee;
    }

    /*
    Description: getEmployees
    Returns the full list of employees (useful for accessing outside the manager).

        Purpose:
            - Allows external classes to retrieve the full employee list, such as for reporting or analytics.
    */
    public List<Employee> getEmployees() {
        return employees;
    }
}
