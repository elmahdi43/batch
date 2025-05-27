package fr.batch.springbatch;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchApplication implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job incrementNumbersJob;

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting Spring Batch Job...");
        
        // Create job parameters (useful for tracking different job executions)
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startTime", System.currentTimeMillis()) // Makes each run unique
                .toJobParameters();
        
        // Launch the job
        jobLauncher.run(incrementNumbersJob, jobParameters);
        
        System.out.println("Spring Batch Job completed!");
    }
}