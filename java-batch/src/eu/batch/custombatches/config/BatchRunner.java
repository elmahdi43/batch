package eu.batch.custombatches.config;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import eu.batch.custombatches.interfaces.Batch;
import eu.batch.custombatches.exceptions.BatchException;

/**
 * Configuration and execution of a scheduled batch job
 *
 * @param <I> The input type for the batch
 * @param <O> The output type for the batch
 */
public class BatchRunner<I, O> {
    private final Batch<I, O> batch;
    private final I source;
    private final Duration interval;
    private final Duration duration;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private ScheduledExecutorService scheduler;
    
    /**
     * Creates a new BatchRunner with the specified configuration
     *
     * @param batch The batch implementation to run
     * @param source The source for the batch
     * @param interval The interval between batch executions
     * @param duration The total duration to run the batch, or null for indefinite
     */
    public BatchRunner(Batch<I, O> batch, I source, Duration interval, Duration duration) {
        this.batch = batch;
        this.source = source;
        this.interval = interval;
        this.duration = duration;
    }
    
    /**
     * Starts the batch execution
     */
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            scheduler = Executors.newScheduledThreadPool(1);
            
            Runnable batchTask = () -> {
                try {
                    System.out.println("Executing batch...");
                    batch.execute(source);
                    System.out.println("Batch execution completed successfully.");
                } catch (BatchException e) {
                    System.err.println("Batch execution failed: " + e.getMessage());
                    e.printStackTrace();
                }
            };
            
            scheduler.scheduleAtFixedRate(
                batchTask,
                0,
                interval.toMillis(),
                TimeUnit.MILLISECONDS
            );
            
            if (duration != null) {
                scheduler.schedule(this::stop, duration.toMillis(), TimeUnit.MILLISECONDS);
            }
        }
    }
    
    /**
     * Stops the batch execution
     */
    public void stop() {
        if (isRunning.compareAndSet(true, false)) {
            System.out.println("Stopping batch execution...");
            if (scheduler != null) {
                scheduler.shutdown();
                try {
                    if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                        scheduler.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    scheduler.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Batch execution stopped.");
        }
    }
}