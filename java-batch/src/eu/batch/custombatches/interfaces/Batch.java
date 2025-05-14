package eu.batch.custombatches.interfaces;

import java.io.IOException;

import eu.batch.custombatches.exceptions.BatchException;

/**
 * Generic batch processing interface that defines the standard operations
 * for batch processing.
 *
 * @param <I> The input type
 * @param <O> The output type
 */
public interface Batch<I, O> {
    /**
     * Reads data from the input source
     *
     * @param input The input source
     * @return The data read from the input source
     * @throws IOException If an I/O error occurs
     */
    String read(I input) throws IOException;
    
    /**
     * Processes the input data and transforms it into output data
     *
     * @param data The input data to process
     * @return The processed output data
     * @throws IllegalArgumentException If the input data is invalid
     */
    O process(String data) throws IllegalArgumentException;
    
    /**
     * Writes the output data to the output destination
     *
     * @param output The output destination
     * @param data The data to write
     * @throws IOException If an I/O error occurs
     */
    void write(I output, O data) throws IOException;
    
    /**
     * Executes the complete batch process: read, process, and write
     *
     * @param source The input/output source
     * @throws BatchException If any error occurs during the batch process
     */
    default void execute(I source) throws BatchException {
        try {
            String data = read(source);
            O processed = process(data);
            write(source, processed);
        } catch (IOException | IllegalArgumentException e) {
            throw new BatchException("Error executing batch process", e);
        }
    }
}