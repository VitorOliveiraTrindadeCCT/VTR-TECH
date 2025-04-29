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
        if (!lines.isEmpty()) {
            lines.remove(0); // Remove header
        }

        for (String line : lines) {
            Employee employee = EmployeeFactory.createFromCSV(line);
            if (employee != null) {
                manager.addEmployee(employee);
            }
        }

        boolean running = true;
        while (running) {
            displayMenuOptions();
            MenuOption option = readUserChoice(scanner);
            if (option == null) continue;

            if (option == MenuOption.EXIT) {
                System.out.println("Exiting program. Goodbye!");
                running = false;
            } else {
                handleMenuOption(option, scanner, manager, fileHandler);
            }
        }

        scanner.close();
    }

    private static void displayMenuOptions() {
        System.out.println("\nPlease select an option:");
        for (MenuOption option : MenuOption.values()) {
            System.out.println((option.ordinal() + 1) + ". " + option.name().replace("_", " "));
        }
    }

    private static MenuOption readUserChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 1 || choice > MenuOption.values().length) {
                System.out.println("Invalid option. Please try again.");
                return null;
            }
            return MenuOption.values()[choice - 1];
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return null;
        }
    }

    private static void handleMenuOption(MenuOption option, Scanner scanner, EmployeeManager manager, FileHandler fileHandler) {
        switch (option) {
            case SORT:
            List<Employee> top20 = manager.getTop20SortedEmployees();
            System.out.println("\n ***Top 20 Sorted Employees***");
            System.out.println("======================================");

            for (int i = 0; i < top20.size(); i++) {
                Employee e = top20.get(i);
                System.out.println((i + 1) + ". " + e.getFirstName() + " " + e.getLastName());
                System.out.println("   Position (Manager Type): " + e.getPosition());
                System.out.println("   Department: " + e.getDepartment());
                System.out.println("***--------------------------------------***");
            }
            break;
            
            
            case SEARCH:
                System.out.print("Enter the full name to search (First and Last name): ");
                String searchName = scanner.nextLine();
                Employee found = manager.searchEmployeeByFullName(searchName);

                if (found != null) {
                    System.out.println("\n🔍 Employee Found!");
                    System.out.println("======================================");
                    System.out.println("Name: " + found.getFirstName() + " " + found.getLastName());
                    System.out.println("Position (Manager Type): " + found.getPosition());
                    System.out.println("Department: " + found.getDepartment());
                    System.out.println("Email: " + found.getEmail());
                    System.out.println("Company: " + found.getCompany());
                    System.out.println("Salary: " + found.getSalary());
                    System.out.println("======================================");
                } else {
                    System.out.println("⚠️ Employee not found.");
                }
                break;

                
            case ADD:
                 Employee newEmployee = EmployeeFactory.createFromUserInput(scanner);
                 manager.addEmployee(newEmployee);
                fileHandler.appendToFile("Applicants_Form.txt", newEmployee);

                System.out.println("\n *** Employee added successfully!***");
                System.out.println("======================================");
                System.out.println("Name: " + newEmployee.getFirstName() + " " + newEmployee.getLastName());
                System.out.println("Gender: " + newEmployee.getGender());
                System.out.println("Email: " + newEmployee.getEmail());
                System.out.println("Salary: " + newEmployee.getSalary());
                System.out.println("Department: " + newEmployee.getDepartment());
                System.out.println("Position: " + newEmployee.getPosition());
                System.out.println("Job Title: " + newEmployee.getJobTitle());
                System.out.println("Company: " + newEmployee.getCompany());
                System.out.println("***======================================***");
                break;
                
                
            case GENERATE_RANDOM:
                manager.generateRandomEmployee();
                System.out.println("Random employee generated successfully!");
                break;
            default:
                System.out.println("Option not implemented.");
        }
    }
}

