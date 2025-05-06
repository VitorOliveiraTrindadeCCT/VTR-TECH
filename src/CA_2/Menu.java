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
        SORT_TOP_20,
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
               case SORT_TOP_20:
            manager.insertionSortEmployees(); // Sort employees using insertion sort
            List<Employee> top20 = manager.getEmployees().subList(0, Math.min(20, manager.getEmployees().size())); // Get top 20 or fewer

            System.out.println("\n============================================================ ***Top 20 Sorted Employees*** ============================================================");
            System.out.println("========================================================================================================================================================");
            System.out.printf("%-4s %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                              "No", "| Name", "| Email", "| Position", "| Department", "| Company", "| Salary");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

            for (int i = 0; i < top20.size(); i++) {
                Employee e = top20.get(i);
                String fullName   = "|  " + e.getFirstName() + " " + e.getLastName();
                String email      = "|  " + e.getEmail();
                String position   = "|  " + e.getPosition().name().replace("_", " ");
                String department = "|  " + e.getDepartment().name().replace("_", " ");
                String company    = "|  " + e.getCompany();
                String salary     = String.format("|  %.2f", e.getSalary());

                System.out.printf("%-4d %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                                  (i + 1), fullName, email, position, department, company, salary);
            }

            System.out.println("========================================================================================================================================================");
            break;

        // Case for searching an employee by full name
      case SEARCH:
            System.out.print("Enter the full name to search (First and Last name): ");
            String searchName = scanner.nextLine(); // Read input
            Employee found = manager.searchEmployeeByFullName(searchName); // Search employee

            if (found != null) {
                System.out.println("\n============================================================== ***Employee Found!*** ======================================================================");
                System.out.println("========================================================================================================================================================");
                System.out.printf("%-4s %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                                  "No", "| Name", "| Email", "| Position", "| Department", "| Company", "| Salary");
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

                String fullName   = "|  " + found.getFirstName() + " " + found.getLastName();
                String email      = "|  " + found.getEmail();
                String position   = "|  " + found.getPosition().name().replace("_", " ");
                String department = "|  " + found.getDepartment().name().replace("_", " ");
                String company    = "|  " + found.getCompany();
                String salary     = String.format("|  %.2f", found.getSalary());

                System.out.printf("%-4d %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                                  1, fullName, email, position, department, company, salary);
                System.out.println("========================================================================================================================================================");
            } else {
                System.out.println("!!! Employee not found !!!"); // Not found message
            }
            break;

        // Case for adding a new employee manually
        case ADD:
            Employee newEmployee = EmployeeFactory.createFromUserInput(scanner); // Create from user input
            manager.addEmployee(newEmployee); // Add to manager
            fileHandler.appendToFile("Applicants_Form.txt", newEmployee); // Save to file

            System.out.println("\n============================================================== ***Employee Added Successfully!*** ==========================================================");
            System.out.println("========================================================================================================================================================");
            System.out.printf("%-4s %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                              "No", "| Name", "| Email", "| Position", "| Department", "| Company", "| Salary");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

            String fullName   = "|  " + newEmployee.getFirstName() + " " + newEmployee.getLastName();
            String email      = "|  " + newEmployee.getEmail();
            String position   = "|  " + newEmployee.getPosition().name().replace("_", " ");
            String department = "|  " + newEmployee.getDepartment().name().replace("_", " ");
            String company    = "|  " + newEmployee.getCompany();
            String salary     = String.format("|  %.2f", newEmployee.getSalary());

            System.out.printf("%-4d %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                              1, fullName, email, position, department, company, salary);
            System.out.println("========================================================================================================================================================");
            break;

        // Case for generating a random employee
        case GENERATE_RANDOM:
            Employee randomEmployee = manager.generateRandomEmployee(); // Generate random employee
            fileHandler.appendToFile("Applicants_Form.txt", randomEmployee); // Save to file

            System.out.println("\n============================================================= ***Random Employee Generated*** =============================================================");
            System.out.println("========================================================================================================================================================");
            System.out.printf("%-4s %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                              "No", "| Name", "| Email", "| Position", "| Department", "| Company", "| Salary");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

            String randFullName   = "|  " + randomEmployee.getFirstName() + " " + randomEmployee.getLastName();
            String randEmail      = "|  " + randomEmployee.getEmail();
            String randPosition   = "|  " + randomEmployee.getPosition().name().replace("_", " ");
            String randDepartment = "|  " + randomEmployee.getDepartment().name().replace("_", " ");
            String randCompany    = "|  " + randomEmployee.getCompany();
            String randSalary     = String.format("|  %.2f", randomEmployee.getSalary());

            System.out.printf("%-4d %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                              1, randFullName, randEmail, randPosition, randDepartment, randCompany, randSalary);
            System.out.println("========================================================================================================================================================");
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
                String fullName2    = "|  " + e.getFirstName() + " " + e.getLastName();
                String email2       = "|  " + e.getEmail();
                String position2    = "|  " + e.getPosition().name().replace("_", " ");
                String department2  = "|  " + e.getDepartment().name().replace("_", " ");
                String company2     = "|  " + e.getCompany();
                String salaryStr2   = String.format("|  %.2f", e.getSalary());

                System.out.printf("%-4d %-30s %-35s %-22s %-27s %-17s %-10s\n", 
                                  (i + 1), fullName2, email2, position2, department2, company2, salaryStr2);
            }

            // Footer
            System.out.println("===========================================================================================================================================================");
            System.out.println("================================================================= ***All Employees***========================================================================");
            break;            
        }
    }
}
