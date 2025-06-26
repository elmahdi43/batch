package fr.batch.springbatch.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.lang.Nullable;

@Configuration
public class BatchConfiguration {

    @Value("${app.batch.input.file}")
    private String inputFilePath;

    @Value("${app.batch.output.file}")
    private String outputFilePath;

    // Step 1: ItemReader - Reads numbers from file line by line
    @Bean
    public FlatFileItemReader<String> reader() {
        return new FlatFileItemReaderBuilder<String>()
                .name("numberReader")
                .resource(new FileSystemResource(inputFilePath))
                .lineMapper((line, lineNumber) -> line.trim()) // Simple line mapping
                .build();
    }

    // Step 2: ItemProcessor - Increments each number by 1
    @Bean
    public ItemProcessor<String, String> processor() {
        return new ItemProcessor<String, String>() {
            @Override
            public String process(@Nullable String item) throws Exception {
                try {
                    int number = Integer.parseInt(item);
                    return String.valueOf(number + 1);
                } catch (NumberFormatException e) {
                    // Log the error and skip this item, or throw exception to fail the job
                    throw new Exception("Invalid number format: " + item, e);
                }
            }
        };
    }

    // Step 3: ItemWriter - Writes incremented numbers to output file
    @Bean
    public FlatFileItemWriter<String> writer() {
        return new FlatFileItemWriterBuilder<String>()
                .name("numberWriter")
                .resource(new FileSystemResource(outputFilePath))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    // Step Definition - Combines Reader, Processor, and Writer
    @Bean
    public Step incrementNumbersStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   ItemReader<String> reader,
                                   ItemProcessor<String, String> processor,
                                   ItemWriter<String> writer) {
        return new StepBuilder("incrementNumbersStep", jobRepository)
                .<String, String>chunk(10, transactionManager) // Process 10 items at a time
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // Job Definition - Contains one or more steps
    @Bean
    public Job incrementNumbersJob(JobRepository jobRepository, Step incrementNumbersStep) {
        return new JobBuilder("incrementNumbersJob", jobRepository)
                .start(incrementNumbersStep)
                .build();
    }
}
