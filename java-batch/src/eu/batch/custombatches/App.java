package eu.batch.custombatches;

import java.io.File;
import java.time.Duration;

import eu.batch.custombatches.config.BatchRunner;
import eu.batch.custombatches.impl.NumberIncrementFileBatch;

/**
 * Main application for running the file batch process.
 * 
 * <p>
 * This application reads a file containing numbers, increments each number by 1,
 * and writes the results back to the file at regular intervals.
 * </p>
 * 
 * @author elmahdi43
 * @version 2.0
 * @since 2025-05-14
 */
public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide file path as an argument");
            return;
        }

        String filePath = args[0];
        File file = new File(filePath);
        
        if (!file.exists()) {
            System.err.println("File does not exist: " + filePath);
            return;
        }
        
        System.out.println("Starting batch process for file: " + filePath);
        
        // Create batch implementation
        NumberIncrementFileBatch batch = new NumberIncrementFileBatch();
        
        // Configure the batch runner
        Duration interval = Duration.ofMinutes(3);
        Duration duration = Duration.ofHours(1);
        BatchRunner<File, String> batchRunner = new BatchRunner<>(batch, file, interval, duration);
        
        // Add shutdown hook for graceful termination
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down batch process...");
            batchRunner.stop();
        }));
        
        // Start the batch process
        batchRunner.start();
        
        System.out.println("Batch process started. Will run for " + 
                          duration.toMinutes() + " minutes with " + 
                          interval.toMinutes() + " minute intervals.");
        System.out.println("Press Ctrl+C to stop.");
    }
}