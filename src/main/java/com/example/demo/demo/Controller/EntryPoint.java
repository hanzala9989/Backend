package com.example.demo.demo.Controller;

import java.io.File;
import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class EntryPoint {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;


    @PostMapping("/importProduct")
    public String importCsvToDBProductJob() {
        long startTime = System.currentTimeMillis();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            String executionTimeInMintue = formatExecutionTime(executionTime);
            System.out.println("Execution time: " + executionTimeInMintue + " ms");
            return "Execution time (MMM): " + executionTimeInMintue;

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String formatExecutionTime(long milliseconds) {
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
