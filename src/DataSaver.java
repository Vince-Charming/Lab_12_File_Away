import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean hasMoreRecords = true;

        System.out.println("--- CSV Record Creator ---");

        // Loop to collect records [cite: 599]
        while (hasMoreRecords) {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter First Name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter Last Name");
            String idNumber = SafeInput.getRegExString(in, "Enter ID Number (6 digits, e.g., 000001)", "^\\d{6}$");
            String email = SafeInput.getNonZeroLenString(in, "Enter Email");
            int yob = SafeInput.getRangedInt(in, "Enter Year of Birth", 1000, 9999);

            // Format as a CSV string [cite: 598]
            String record = String.format("%s, %s, %s, %s, %d", firstName, lastName, idNumber, email, yob);
            records.add(record); // Add to ArrayList [cite: 600]

            hasMoreRecords = SafeInput.getYNConfirm(in, "Do you want to add another record?");
        }

        // Prompt for file name [cite: 600]
        String fileName = SafeInput.getNonZeroLenString(in, "Enter the name for your save file (e.g., mydata)");
        fileName += ".csv"; // Add the .csv extension [cite: 600]

        // Set path to the src directory [cite: 600-601]
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "/src/" + fileName);

        // Write the file [cite: 600]
        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for (String rec : records) {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("\nData successfully saved to: " + file.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}