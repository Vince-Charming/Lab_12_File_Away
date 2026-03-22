import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {

    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        try {
            // Set the JFileChooser to open in the src directory [cite: 572]
            File workingDirectory = new File(System.getProperty("user.dir") + "/src");
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                System.out.println("--- File Contents ---");

                // Read file line by line [cite: 581]
                while (reader.ready()) {
                    rec = reader.readLine();
                    lineCount++;

                    // Echo line to screen [cite: 572]
                    System.out.printf("Line %4d: %s\n", lineCount, rec);

                    // Count characters in the line [cite: 584]
                    charCount += rec.length();

                    // Count words using split [cite: 583]
                    // We check if the line is empty to avoid counting a blank string as 1 word
                    if (!rec.trim().isEmpty()) {
                        String[] words = rec.split("\\s+"); // splits by any whitespace
                        wordCount += words.length;
                    }
                }
                reader.close();

                // Print the summary report [cite: 573-578]
                System.out.println("\n--- Summary Report ---");
                System.out.println("File Name: " + selectedFile.getName());
                System.out.println("Number of Lines: " + lineCount);
                System.out.println("Number of Words: " + wordCount);
                System.out.println("Number of Characters: " + charCount);

            } else {
                System.out.println("No file selected. Exiting program.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}