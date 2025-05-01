/**
 * FileHandler
 * 
 * Description: Handles reading and writing employee data to and from a text file.
 * 
 * Author: Vitor Oliveira Trindade
 * Date: 28/04/2025
 */
package CA_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    /*
    Description: readFile
    This method reads the contents of a file line by line and stores each line in a List<String>.

        Logic:
            > Uses a BufferedReader for efficient line-by-line reading.
            > Trims each line to remove leading/trailing whitespace.
            > Collects all lines in a list and returns it.

        Purpose:
            > To read raw data from a CSV/text file for further processing, such as converting to Employee objects.
    */
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

    /*
    Description: writeFile
    This method writes a list of strings to a file, each string on a new line.

        Logic:
            > Opens a BufferedWriter.
            > Iterates through the list of lines.
            > Writes each line followed by a newline character.

        Purpose:
            > To overwrite an entire file with updated data, such as when saving multiple employees at once.
    */
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

    /*
    Description: appendToFile
    This method appends a single Employee's data to the end of a file in CSV format.

        Logic:
            > Opens a FileWriter in append mode (true).
            > Uses String.join to format employee attributes as a comma-separated string.
            > Writes the resulting line followed by a newline.

        Purpose:
            > To add a new employee to the file without overwriting existing data.
            > Keeps a persistent record of employee entries in CSV format.
    */
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
