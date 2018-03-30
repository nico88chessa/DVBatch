package com.dv.batch;

import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

public class TestBatch {

    private JobLauncher jobLauncher;
    private Job job;

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @SuppressWarnings("resource")
    @Test
    public void testJob() throws Exception {
//        JobExecution jobExecution = jobLauncher.run("job2", new JobParameters());


        // Creating the application context object
//        ApplicationContext context = new ClassPathXmlApplicationContext("appServlet/servlet-context.xml");

        // Creating the job launcher
//        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");

        // Creating the job
//        Job job = (Job) context.getBean("job2");

        // Executing the JOB
        JobExecution execution = jobLauncher.run(job, new JobParameters());
        System.out.println("Exit Status : " + execution.getStatus());

    }


}
