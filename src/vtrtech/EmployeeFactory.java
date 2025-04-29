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

        System.out.print("Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Salary: ");
        double salary = 0.0;
        try {
            salary = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary input. Setting salary to 0.0.");
        }

        System.out.print("Department: ");
        String department = scanner.nextLine();

        System.out.print("Position: ");
        String position = scanner.nextLine();

        System.out.print("Job Title: ");
        String jobTitle = scanner.nextLine();

        System.out.print("Company: ");
        String company = scanner.nextLine();

        return new Employee(firstName, lastName, gender, email, salary, department, position, jobTitle, company);
    }
}
