package com.example.demo.demo.Config;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.example.demo.demo.Entities.CustomerEntity;
import com.example.demo.demo.RepoImplementation.CustomerRepo;

@Configuration
@EnableBatchProcessing
public class SpringBatchCofig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    CustomerRepo customerRepo;

    @Bean
    public FlatFileItemReader<CustomerEntity> reader() {
        FlatFileItemReader<CustomerEntity> itemReader = new FlatFileItemReader<>();
        String directoryPath = "src/main/resources/csvFile/";
        String csvFileName = findCsvFile(directoryPath);

        if (csvFileName != null) {
            String resourceLocation = directoryPath + csvFileName;
            itemReader.setResource(new FileSystemResource(resourceLocation));
            itemReader.setName(csvFileName);
            itemReader.setLinesToSkip(1);
            itemReader.setLineMapper(lineMapper());
        } else {
            System.out.println("No CSV file found in directory: " + directoryPath);
        }

        return itemReader;
    }

    public static String findCsvFile(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
                    return file.getName();
                }
            }
        }
        return null;
    }

    private LineMapper<CustomerEntity> lineMapper() {

        DefaultLineMapper<CustomerEntity> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "Customer Id", "First Name", "Last Name", "Company", "City", "Country", "Phone 1",
                "Phone 2", "Email", "Subscription Date", "Website");

        BeanWrapperFieldSetMapper<CustomerEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CustomerEntity.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public CustomerPorcessor porcessor() {
        return new CustomerPorcessor();
    }

    @Bean
    public RepositoryItemWriter<CustomerEntity> writer() {

        RepositoryItemWriter<CustomerEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepo);
        writer.setMethodName("save");

        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csv-step").<CustomerEntity, CustomerEntity>chunk(10)
                .reader(reader())
                .processor(porcessor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("step1()").flow(step1()).end().build();
    }

    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(10);
        return simpleAsyncTaskExecutor;
    }

}
