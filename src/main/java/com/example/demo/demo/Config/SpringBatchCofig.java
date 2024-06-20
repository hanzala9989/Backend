package com.example.demo.demo.Config;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(SpringBatchCofig.class);
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    CustomerRepo customerRepo;

    @Bean
    public FlatFileItemReader<CustomerEntity> reader() throws FileNotFoundException {
        logger.info("SpringBatchCofig :: Initializing FlatFileItemReader !!!");
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
            logger.error("SpringBatchCofig :: No CSV file found in directory: " + directoryPath);
            throw new FileNotFoundException("No CSV file found in directory: " + directoryPath);
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
        logger.info("SpringBatchCofig :: Initializing CustomerProcessor !!!");
        return new CustomerPorcessor();
    }

    @Bean
    public RepositoryItemWriter<CustomerEntity> writer() {
        logger.info("SpringBatchCofig :: Initializing RepositoryItemWriter !!!");
        RepositoryItemWriter<CustomerEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepo);
        writer.setMethodName("save");

        return writer;
    }

    @Bean
    public Step step1() {
        try {
            logger.info("SpringBatchCofig :: Initializing Step !!!");
            return stepBuilderFactory.get("csv-step").<CustomerEntity, CustomerEntity>chunk(10)
                    .reader(reader())
                    .processor(porcessor())
                    .writer(writer())
                    .taskExecutor(taskExecutor())
                    .build();
        } catch (FileNotFoundException e) {
            logger.error("SpringBatchCofig :: Error in Step initialization: ", e);
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public Job runJob() {
        logger.info("SpringBatchCofig :: Initializing Job !!!");
        return jobBuilderFactory.get("step1()").flow(step1()).end().build();
    }

    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(10);
        return simpleAsyncTaskExecutor;
    }

}
