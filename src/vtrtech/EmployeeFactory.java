/**
 * EmployeeFactory
 * 
 * Description: Responsible for creating Employee instances from various sources (e.g., user input).
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 29/04/2025
 */
package vtrtech;

import java.util.Scanner;

public class EmployeeFactory {

    public static Employee createFromUserInput(Scanner scanner) {
    System.out.print("First Name: ");
    String firstName = scanner.nextLine();

    System.out.print("Last Name: ");
    String lastName = scanner.nextLine();

    // Gender
    String[] genders = {"Male", "Female"};
    System.out.println("Select Gender:");
    for (int i = 0; i < genders.length; i++) {
        System.out.println((i + 1) + ". " + genders[i]);
    }
    int genderChoice = readOption(scanner, genders.length);
    String gender = genders[genderChoice - 1];

    // Email
    System.out.print("Email: ");
    String email = scanner.nextLine();

    // Salary
    System.out.print("Salary: ");
    double salary = 0.0;
    try {
        salary = Double.parseDouble(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
        System.out.println("Invalid salary input. Setting salary to 0.0.");
    }

    // Department
    String[] departments = {
        "IT Development", "Sales", "HR", "Finance",
        "Marketing", "Accounting", "Operations",
        "Technical Support", "Customer Service", "IT"
    };
    System.out.println("Select Department:");
    for (int i = 0; i < departments.length; i++) {
        System.out.println((i + 1) + ". " + departments[i]);
    }
    int deptChoice = readOption(scanner, departments.length);
    String department = departments[deptChoice - 1];

    // Position
    String[] positions = {"Senior", "Middle", "Intern", "Junior", "Contract", "Analista"};
    System.out.println("Select Position:");
    for (int i = 0; i < positions.length; i++) {
        System.out.println((i + 1) + ". " + positions[i]);
    }
    int posChoice = readOption(scanner, positions.length);
    String position = positions[posChoice - 1];

    // Job Title
    System.out.print("Job Title: ");
    String jobTitle = scanner.nextLine();

    // Company
    System.out.print("Company: ");
    String company = scanner.nextLine();

    return new Employee(firstName, lastName, gender, email, salary, department, position, jobTitle, company);
}
    
public static Employee createFromCSV(String csvLine) {
    String[] parts = csvLine.split(",");

    if (parts.length < 9) {
        return null; // Linha inválida, retorna null
    }

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

    return new Employee(firstName, lastName, gender, email, salary, department, position, jobTitle, company);
}
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
    
}
