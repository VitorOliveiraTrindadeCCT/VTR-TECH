/**
 * Menu
 * 
 * Description: Main class that interacts with the user, displaying options and 
 * invoking operations through the EmployeeManager.
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 28/04/2025
 */
package vtrtech;

import java.util.List;
import java.util.Scanner;

public class Menu {

    public enum MenuOption {
        SORT,
        SEARCH,
        ADD,
        GENERATE_RANDOM,
        EXIT
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();
        FileHandler fileHandler = new FileHandler();

        // Load data from file
       List<String> lines = fileHandler.readFile("Applicants_Form.txt");

// Remove header first (very important) i lost two days because this. 
if (!lines.isEmpty()) {
    lines.remove(0);
}

// Now process only real employee data
for (String line : lines) {
    String[] parts = line.split(",");

    if (parts.length >= 9) {
        String firstName = parts[0].trim();
        String lastName = parts[1].trim();
        String gender = parts[2].trim();
        String email = parts[3].trim();
        double salary = 0.0;
        try {
            salary = Double.parseDouble(parts[4].trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary value for: " + firstName + " " + lastName);
        }
        String department = parts[5].trim();
        String position = parts[6].trim();
        String jobTitle = parts[7].trim();
        String company = parts[8].trim();

        Employee employee = new Employee(firstName, lastName, gender, email, salary, department, position, jobTitle, company);
        manager.addEmployee(employee);
    }
}

        boolean running = true;

        while (running) {
            System.out.println("\nPlease select an option:");
            for (MenuOption option : MenuOption.values()) {
                System.out.println((option.ordinal() + 1) + ". " + option.name().replace("_", " "));
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (choice < 1 || choice > MenuOption.values().length) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }

            switch (MenuOption.values()[choice - 1]) {
                case SORT:
                   manager.sortAndShowFirst20();
                   break;
                    
                case SEARCH:
                   System.out.print("Enter the full name to search (First and Last name): ");
                   String searchName = scanner.nextLine();
                   manager.searchAndPrint(searchName);
                   break;

                case ADD:
                   Employee newEmployee = EmployeeFactory.createFromUserInput(scanner);
                   manager.addEmployee(newEmployee);
                   fileHandler.appendToFile("Applicants_Form.txt", newEmployee);
                   System.out.println("Employee added successfully!");
                   break;

                case GENERATE_RANDOM:
                    manager.generateRandomEmployee();
                    System.out.println("Random employee generated successfully!");
                    break;

                case EXIT:
                    running = false;
                    System.out.println("Exiting program. Goodbye!");
                    break;
            }
        }

        scanner.close();
    }
}
