package com.example.demo.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoadController {


    //JOBLAUNCHER ---> job --> sTEP - 1.rEADER 2.pROCESSOR 3.WrITER
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping("/load")
    public BatchStatus getLoad() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String,JobParameter> m = new HashMap<>();
        m.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(m);
       JobExecution jobExecution = jobLauncher.run(job,jobParameters);
       return jobExecution.getStatus();

    }
}

