/**
 * Menu
 * 
 * Description: Main class that interacts with the user, displaying options and 
 * invoking operations through the EmployeeManager.
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 28/04/2025
 */
package CA_2; // Defines the package name for this class

import java.util.List;
import java.util.Scanner;

public class Menu {

    // Enum that defines all available menu options
    public enum MenuOption {
        SORT,
        SEARCH,
        ADD,
        GENERATE_RANDOM,
        EXIT
    }
/*
Description:main
This is the entry point of the program. It initializes all core components (scanner, manager, file handler), loads employee data from a file, and starts the main loop for interacting with the user.

	Key responsibilities:

		>Reads employees from Applicants_Form.txt.
		>Converts each line to an Employee object and adds it to the system.
		>Continuously displays a menu, takes user input, and calls the appropriate action.
		>Ends when the user selects the "EXIT" option.
*/
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner to read user input
        EmployeeManager manager = new EmployeeManager(); // Handles employee operations
        FileHandler fileHandler = new FileHandler(); // Handles file reading/writing

        // Reads data from a text file and loads employees
        List<String> lines = fileHandler.readFile("Applicants_Form.txt");
        if (!lines.isEmpty()) {
            lines.remove(0); // Removes header line it is necesary 

        }

        // Converts each CSV line into an Employee object and adds it to the manager
      for (String line : lines) {
           Employee employee = EmployeeFactory.createFromCSV(line);
            if (employee != null) {
                manager.addEmployee(employee);
            }
        }

        boolean running = true;
        // Main menu loop
        while (running) {
            displayMenuOptions(); // Show the menu options to the user
            MenuOption option = readUserChoice(scanner); // Read and validate user input

            if (option == null) continue; // Skip iteration if invalid input

            if (option == MenuOption.EXIT) {
                System.out.println("Exiting program. Goodbye!");
                running = false; // Exit loop
            } else {
                // Handle selected option
                handleMenuOption(option, scanner, manager, fileHandler);
            }
        }

        scanner.close(); // Close the scanner to avoid resource leak
    }

/*
Description:displayMenuOptions
This function displays the list of available menu options to the user in a numbered format.

	Logic:

		>It loops over the MenuOption enum values.
		>For each enum, it prints its ordinal (starting from 1) and its name with underscores replaced by spaces for better readability.

	Purpose:
		>Make the user interface intuitive by presenting a clear and numbered list of choices.
*/
    private static void displayMenuOptions() {
        System.out.println("\nPlease select an option:");
        for (MenuOption option : MenuOption.values()) {
            // Prints each option with number + formatted name
            System.out.println((option.ordinal() + 1) + ". " + option.name().replace("_", " "));
        }
    }

/*
Description:readUserChoice
This method reads the user input from the command line and converts it into a valid MenuOption.

	Logic:

		>Reads a line from the scanner.
		>Tries to parse it into an integer and check if it matches a valid menu option.
		>If valid, returns the corresponding MenuOption enum.
		>If invalid (wrong number or not a number), displays an error and returns null.

	Purpose:
		>Ensure that only valid and safe inputs proceed to logic execution, preventing crashes and improving UX.
*/
    private static MenuOption readUserChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());

            // Check if user input is in valid range
            if (choice < 1 || choice > MenuOption.values().length) {
                System.out.println("\n Atention Invalid option. Please enter a number between 1 and 5.");
                System.out.println("                         !!!Please tyr Again!!!");
                return null;
            }

            // Return corresponding MenuOption based on ordinal
            return MenuOption.values()[choice - 1];
        } catch (NumberFormatException e) {
            // Handles non-integer inputs
            System.out.println("\n Atention Invalid option. Please enter a number between 1 and 5.");
            System.out.println("                         !!!Please try Again!!!");
            return null;
        }
    }

/*
Description:handleMenuOption
This function is responsible for executing the logic associated with each menu option selected by the user.

	How it works:

		>Uses a switch statement to check which option was selected.
		>Depending on the option, performs different actions:

			*SORT 	->Calls getTop20SortedEmployees() from the manager. Displays the top 20 employees sorted by some criteria (likely by salary or performance).
			*SEARCH	-> Prompts the user for a full name. Searches using manager.searchEmployeeByFullName(name). Displays detailed employee info if found.
			*ADD	->Uses EmployeeFactory.createFromUserInput() to collect data and create a new employee.Adds the new employee to the system and appends it to the file.
			*RANDOM	->Creates a random employee using internal logic from the manager.Saves it and prints all their info.
			*EXIT	->Actually handled in main() before this function is called.

	Purpose:
		This is the controller method that links the user choice with the actual program behavior. It encapsulates the business logic for each menu operation.
*/
    private static void handleMenuOption(MenuOption option, Scanner scanner, EmployeeManager manager, FileHandler fileHandler) {
        switch (option) {
                        case SORT:
                // Sort the employees using Insertion Sort
                manager.insertionSortEmployees();

                // Get the top 20 employees after sorting
                List<Employee> top20 = manager.getEmployees().subList(0, Math.min(20, manager.getEmployees().size()));

                System.out.println("\n ***Top 20 Sorted Employees***");
                System.out.println("======================================");

                for (int i = 0; i < top20.size(); i++) {
                    Employee e = top20.get(i);
                    System.out.println((i + 1) + ". " + e.getFirstName() + " " + e.getLastName());
                    System.out.println("   Position (Manager Type): " + e.getPosition());
                    System.out.println("   Department: " + e.getDepartment());
                    System.out.println("--------------------------------------");
                }
                break;

            case SEARCH:
                // Prompt user for full name and search employee
                System.out.print("Enter the full name to search (First and Last name): ");
                String searchName = scanner.nextLine();
                Employee found = manager.searchEmployeeByFullName(searchName);

                if (found != null) {
                    // Display employee details if found
                    System.out.println("\n***Employee Found!***");
                    System.out.println("======================================");
                    System.out.println("Name: " + found.getFirstName() + " " + found.getLastName());
                    System.out.println("Position (Manager Type): " + found.getPosition());
                    System.out.println("Department: " + found.getDepartment());
                    System.out.println("Email: " + found.getEmail());
                    System.out.println("Company: " + found.getCompany());
                    System.out.println("Salary: " + found.getSalary());
                    System.out.println("======================================");
                } else {
                    // Notify if employee was not found
                    System.out.println("!!! Employee not found!!!.");
                }
                break;

            case ADD:
                // Add a new employee using user input
                Employee newEmployee = EmployeeFactory.createFromUserInput(scanner);
                manager.addEmployee(newEmployee); // Add to manager
                fileHandler.appendToFile("Applicants_Form.txt", newEmployee); // Append to file

                // Confirmation message
                System.out.println("\n ***Employee added successfully!***");
                System.out.println("======================================");
                System.out.println("Name: " + newEmployee.getFirstName() + " " + newEmployee.getLastName());
                System.out.println("Gender: " + newEmployee.getGender());
                System.out.println("Email: " + newEmployee.getEmail());
                System.out.println("Salary: " + newEmployee.getSalary());
                System.out.println("Department: " + newEmployee.getDepartment());
                System.out.println("Position: " + newEmployee.getPosition());
                System.out.println("Job Title: " + newEmployee.getJobTitle());
                System.out.println("Company: " + newEmployee.getCompany());
                System.out.println("======================================");
                break;

            case GENERATE_RANDOM:
                // Generate and add a random employee
                Employee randomEmployee = manager.generateRandomEmployee();
                fileHandler.appendToFile("Applicants_Form.txt", randomEmployee); // Save to file

                // Display generated employee
                System.out.println("\n*** Random Employee Generated***");
                System.out.println("======================================");
                System.out.println("Name: " + randomEmployee.getFirstName() + " " + randomEmployee.getLastName());
                System.out.println("Gender: " + randomEmployee.getGender());
                System.out.println("Email: " + randomEmployee.getEmail());
                System.out.println("Salary: " + randomEmployee.getSalary());
                System.out.println("Department: " + randomEmployee.getDepartment());
                System.out.println("Position: " + randomEmployee.getPosition());
                System.out.println("Job Title: " + randomEmployee.getJobTitle());
                System.out.println("Company: " + randomEmployee.getCompany());
                System.out.println("======================================");
                break;

            default:
                // Just in case a new enum value is added and not handled
                System.out.println("Option not implemented.");
        }
    }
}
