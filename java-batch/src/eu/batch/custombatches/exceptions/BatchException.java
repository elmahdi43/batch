package eu.batch.custombatches.exceptions;

/**
 * Custom exception for batch processing operations
 */
public class BatchException extends Exception {
    public BatchException(String message) {
        super(message);
    }
    
    public BatchException(String message, Throwable cause) {
        super(message, cause);
    }
}