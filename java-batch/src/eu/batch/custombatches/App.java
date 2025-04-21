package eu.batch.custombatches;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * App is a class for testing the batch processing of the data using java 21 features.
 * 
 * <p>
 * The App class provides a main method to test the batch processing of the data.
 * </p>
 * 
 * @author elmahdi43
 * @version 1.0
 * @since 2025-03-26
 */

 // TODO: configure a batch in java 21 to read a file and increment each line by 1 and write the new content to the file. And after think about how can i usse my applying table to crate a small app. i may use spring batch to do it.
public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Please provide file path as an argument");
            return;
        }

        String path = args[0];
        System.out.println("Path: " + path);

        long startTime = System.currentTimeMillis();
        long duration = 60 * 60 * 1000; // 1 hour in milliseconds
        long interval = 3 * 60 * 1000; // 3 minutes in milliseconds

        while (System.currentTimeMillis() - startTime < duration) {
            try {
                String content = Files.readString(Path.of(path));
                System.out.println("File content before processing: ");
                System.out.println(content);

                String[] lines = content.split("\n");

                StringBuilder sb = new StringBuilder();
                for (String line : lines) {
                    int index = Integer.parseInt(line.trim());
                    sb.append(index + 1).append("\n");
                }

                System.out.println("File content after processing: ");
                System.out.println(sb.toString());
                // write the new content to the file
                Files.write(Path.of(path), sb.toString().getBytes());

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            Thread.sleep(interval); // Wait for 3 minutes before the next execution
        }
    }
}