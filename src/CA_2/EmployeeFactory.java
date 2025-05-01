
/**
 * EmployeeFactory
 * 
 * Description: Responsible for creating Employee instances from various sources (e.g., user input).
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 29/04/2025
 */
package CA_2;

import java.util.Scanner;

public class EmployeeFactory {

    /*
    Description: createFromUserInput
    Creates an Employee object by interacting with the user through the console.

        Logic:
            > Prompts the user for various pieces of information: name, gender, email, salary, department, etc.
            > Uses helper methods to validate and read input.
            > Returns a fully populated Employee instance.

        Purpose:
            > Provides a structured and guided way to create an Employee via terminal interaction.
    */
    public static Employee createFromUserInput(Scanner scanner) {
        // Prompt and read first name
        String firstName = readNonEmptyInput(scanner, "First Name: ");
        
        // Prompt and read last name
        String lastName = readNonEmptyInput(scanner, "Last Name: ");

        // Define possible gender options
        String[] genders = {"Male", "Female"};

        // Ask user to select gender
        System.out.println("Select Gender:");
        for (int i = 0; i < genders.length; i++) {
            System.out.println((i + 1) + ". " + genders[i]);
        }

        // Read user selection for gender
        int genderChoice = readOption(scanner, genders.length);
        String gender = genders[genderChoice - 1];

        // Prompt and read email
        String email = readNonEmptyInput(scanner, "Email: ");

        // Prompt and read salary
        System.out.print("Salary: ");
        double salary = 0.0;
        try {
            salary = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary input. Setting salary to 0.0.");
        }

        // Define department options
        String[] departments = {
            "IT Development", "Sales", "HR", "Finance",
            "Marketing", "Accounting", "Operations",
            "Technical Support", "Customer Service", "IT"
        };

        // Ask user to select department
        System.out.println("Select Department:");
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i]);
        }

        // Read user selection for department
        int deptChoice = readOption(scanner, departments.length);
        String department = departments[deptChoice - 1];

        // Define position options
        String[] positions = {"Senior", "Middle", "Intern", "Junior", "Contract", "Analista"};

        // Ask user to select position
        System.out.println("Select Position:");
        for (int i = 0; i < positions.length; i++) {
            System.out.println((i + 1) + ". " + positions[i]);
        }

        // Read user selection for position
        int posChoice = readOption(scanner, positions.length);
        String position = positions[posChoice - 1];

        // Prompt and read job title
        String jobTitle = readNonEmptyInput(scanner, "Job Title: ");

        // Prompt and read company name
        String company = readNonEmptyInput(scanner, "Company: ");

        // Create and return the new Employee object
        return new Employee(firstName, lastName, gender, email, salary, department, position, jobTitle, company);
    }

    /*
    Description: createFromCSV
    Converts a single line of CSV-formatted text into an Employee object.

        Logic:
            > Splits the line by commas.
            > Trims and parses the fields into proper types.
            > Returns an Employee object or null if invalid.

        Purpose:
            > Used to convert file-based data into usable Employee instances.
    */
    public static Employee createFromCSV(String csvLine) {
        String[] parts = csvLine.split(",");

        // Validate if line has all required fields
        if (parts.length < 9) {
            return null; // Invalid line, return null
        }

        // Assign and trim each value from CSV
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

        // Create and return the Employee object
        return new Employee(firstName, lastName, gender, email, salary, department, position, jobTitle, company);
    }

    /*
    Description: readOption
    Reads a numeric option from the user and validates if it's within a valid range.

        Parameters:
            - scanner: the Scanner object for input
            - max: maximum valid number (inclusive)

        Purpose:
            > Ensures users select only valid numbered options in menus.
    */
    private static int readOption(Scanner scanner, int max) {
        int choice = -1;

        while (choice < 1 || choice > max) {
            System.out.print("Enter a number between 1 and " + max + ": ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return choice;
    }

    /*
    Description: readNonEmptyInput
    Prompts the user for input and ensures the field is not left blank.

        Parameters:
            - scanner: the Scanner object for input
            - prompt: the message shown to the user

        Returns:
            - A non-empty, trimmed String

        Purpose:
            > Avoids null or empty fields for critical Employee data.
    */
    private static String readNonEmptyInput(Scanner scanner, String prompt) {
        String input = "";

        while (input.isBlank()) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (input.isBlank()) {
                System.out.println(" This field cannot be empty. Please enter a valid value.");
            }
        }

        return input;
    }

}
