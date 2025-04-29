/**
 * FileHandler
 * 
 * Description: Handles reading and writing employee data to and from a text file.
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 28/04/2025
 * 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vtrtech;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // Read a file and return its lines as a List
    public List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return lines;
    }

    // Write a list of lines to a file
    public void writeFile(String filename, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    public void appendToFile(String filename, Employee employee) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
        String line = String.join(",",
            employee.getFirstName(),
            employee.getLastName(),
            employee.getGender(),
            employee.getEmail(),
            String.valueOf(employee.getSalary()),
            employee.getDepartment(),
            employee.getPosition(),
            employee.getJobTitle(),
            employee.getCompany()
        );
        writer.write(line);
        writer.newLine();
    } catch (IOException e) {
        System.out.println("Error appending to file: " + e.getMessage());
    }
}

}
