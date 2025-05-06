/**
 * Menu
 * 
 * Description: Main class that interacts with the user, displaying options and 
 * invoking operations through the EmployeeManager.
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 28/04/2025 (Atualizado para uso com enums)
 */
package CA_2;

import java.util.List;
import java.util.Scanner;

public class Menu {

    // Enum that defines all available menu options
    public enum MenuOption {
        SORT,
        List_All_Employees,
        SEARCH,
        ADD,
        GENERATE_RANDOM,
        EXIT
    }

    /*
    Description: main
    This is the entry point of the program. It initializes all core components (scanner, manager, file handler), loads employee data from a file, and starts the main loop for interacting with the user.

        Key responsibilities:
            > Reads employees from Applicants_Form.txt.
            > Converts each line to an Employee object and adds it to the system.
            > Continuously displays a menu, takes user input, and calls the appropriate action.
            > Ends when the user selects the "EXIT" option.
    */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManager manager = new EmployeeManager();
        FileHandler fileHandler = new FileHandler();

        List<String> lines = fileHandler.readFile("Applicants_Form.txt");
        if (!lines.isEmpty()) {
            lines.remove(0); // Remove header line
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

    /*
    Description: displayMenuOptions
    This function displays the list of available menu options to the user in a numbered format.

        Logic:
            > Loops over the MenuOption enum values.
            > Prints each option with its ordinal and formatted name.

        Purpose:
            > Makes the user interface intuitive and easy to navigate.
    */
    private static void displayMenuOptions() {
        System.out.println("\nPlease select an option:");
        for (MenuOption option : MenuOption.values()) {
            System.out.println((option.ordinal() + 1) + ". " + option.name().replace("_", " "));
        }
    }

    /*
    Description: readUserChoice
    This method reads the user input from the command line and converts it into a valid MenuOption.

        Logic:
            > Parses user input into an integer.
            > Checks for valid range.
            > Returns corresponding MenuOption or null on failure.

        Purpose:
            > Ensures valid interaction and prevents invalid selections.
    */
    private static MenuOption readUserChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());

            if (choice < 1 || choice > MenuOption.values().length) {
                System.out.println("\n Attention! Invalid option. Please enter a number between 1 and 5.");
                return null;
            }

            return MenuOption.values()[choice - 1];
        } catch (NumberFormatException e) {
            System.out.println("\n Attention! Invalid input. Please enter a valid number.");
            return null;
        }
    }

    /*
    Description: handleMenuOption
    This function is responsible for executing the logic associated with each menu option selected by the user.

        How it works:
            > Uses a switch statement to determine which option was selected.
            > Calls the appropriate method on the EmployeeManager or prompts the user accordingly.

        Purpose:
            > Acts as the controller that links UI interaction with program functionality.
    */
    private static void handleMenuOption(MenuOption option, Scanner scanner, EmployeeManager manager, FileHandler fileHandler) {
        switch (option) {
        case SORT:
            manager.insertionSortEmployees(); // Sorts employees using insertion sort
            List<Employee> top20 = manager.getEmployees().subList(0, Math.min(20, manager.getEmployees().size())); // Gets top 20 (or fewer if less than 20 employees)

            System.out.println("\n ***Top 20 Sorted Employees***");
            System.out.println("======================================");

            // Prints each of the top 20 employees' basic info
            for (int i = 0; i < top20.size(); i++) {
                Employee e = top20.get(i);
                System.out.println((i + 1) + ". " + e.getFirstName() + " " + e.getLastName());
                System.out.println("   Position (Manager Type): " + e.getPosition().name().replace("_", " "));
                System.out.println("   Department: " + e.getDepartment().name().replace("_", " "));
                System.out.println("--------------------------------------");
            }
            break;

        // Case for searching an employee by full name
        case SEARCH:
            System.out.print("Enter the full name to search (First and Last name): ");
            String searchName = scanner.nextLine(); // Reads full name from user input
            Employee found = manager.searchEmployeeByFullName(searchName); // Searches employee by full name

            if (found != null) {
                // If found, display full employee details
                System.out.println("\n***Employee Found!***");
                System.out.println("======================================");
                System.out.println("Name: " + found.getFirstName() + " " + found.getLastName());
                System.out.println("Position (Manager Type): " + found.getPosition().name().replace("_", " "));
                System.out.println("Department: " + found.getDepartment().name().replace("_", " "));
                System.out.println("Email: " + found.getEmail());
                System.out.println("Company: " + found.getCompany());
                System.out.println("Salary: " + found.getSalary());
                System.out.println("======================================");
            } else {
                System.out.println("!!! Employee not found !!!"); // Message if employee is not found
            }
            break;

        // Case for adding a new employee manually
        case ADD:
            Employee newEmployee = EmployeeFactory.createFromUserInput(scanner); // Creates new employee from user input
            manager.addEmployee(newEmployee); // Adds employee to list
            fileHandler.appendToFile("Applicants_Form.txt", newEmployee); // Appends employee to file

            // Displays confirmation and employee details
            System.out.println("\n ***Employee added successfully!***");
            System.out.println("======================================");
            System.out.println("Name: " + newEmployee.getFirstName() + " " + newEmployee.getLastName());
            System.out.println("Gender: " + newEmployee.getGender());
            System.out.println("Email: " + newEmployee.getEmail());
            System.out.println("Salary: " + newEmployee.getSalary());
            System.out.println("Department: " + newEmployee.getDepartment().name().replace("_", " "));
            System.out.println("Position: " + newEmployee.getPosition().name().replace("_", " "));
            System.out.println("Job Title: " + newEmployee.getJobTitle());
            System.out.println("Company: " + newEmployee.getCompany());
            System.out.println("======================================");
            break;

        // Case for generating a random employee
        case GENERATE_RANDOM:
            Employee randomEmployee = manager.generateRandomEmployee(); // Generates a random employee
            fileHandler.appendToFile("Applicants_Form.txt", randomEmployee); // Appends to file

            // Displays the randomly generated employee
            System.out.println("\n*** Random Employee Generated ***");
            System.out.println("======================================");
            System.out.println("Name: " + randomEmployee.getFirstName() + " " + randomEmployee.getLastName());
            System.out.println("Gender: " + randomEmployee.getGender());
            System.out.println("Email: " + randomEmployee.getEmail());
            System.out.println("Salary: " + randomEmployee.getSalary());
            System.out.println("Department: " + randomEmployee.getDepartment().name().replace("_", " "));
            System.out.println("Position: " + randomEmployee.getPosition().name().replace("_", " "));
            System.out.println("Job Title: " + randomEmployee.getJobTitle());
            System.out.println("Company: " + randomEmployee.getCompany());
            System.out.println("======================================");
            break;

        // Case for listing all employees in a table format
        case List_All_Employees:
            manager.insertionSortEmployees(); // Sorts all employees before listing
            List<Employee> allEmployees = manager.getEmployees(); // Gets all employees

            // Table headers
            System.out.println("\n================================================================= ***All Employees***========================================================================");
            System.out.println("==========================================================================================================================================================");
            System.out.printf("%-4s %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                              "No", "| Name", "| Email", "| Position", "| Department", "| Company", "| Salary");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

            // Prints each employee as a row in the table
            for (int i = 0; i < allEmployees.size(); i++) {
                Employee e = allEmployees.get(i);
                String fullName    = "|  " + e.getFirstName() + " " + e.getLastName();
                String email       = "|  " + e.getEmail();
                String position    = "|  " + e.getPosition().name().replace("_", " ");
                String department  = "|  " + e.getDepartment().name().replace("_", " ");
                String company     = "|  " + e.getCompany();
                String salaryStr   = String.format("|  %.2f", e.getSalary());

                System.out.printf("%-4d %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                                  (i + 1), fullName, email, position, department, company, salaryStr);
            }

            // Footer
            System.out.println("===========================================================================================================================================================");
            System.out.println("================================================================= ***All Employees***========================================================================");
            break;            
        }
    }
}
