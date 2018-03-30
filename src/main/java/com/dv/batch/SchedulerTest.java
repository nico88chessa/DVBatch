package com.dv.batch;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import com.dv.batch.db.bean.MachineMaxTicket;
import com.dv.batch.db.mapper.BatchMapper;

public class SchedulerTest {

    private JobLauncher jobLauncher;
    private Job job;
    private TestItemReader reader;
    private BatchMapper batchMapper;


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

    public TestItemReader getReader() {
        return reader;
    }

    public void setReader(TestItemReader reader) {
        this.reader = reader;
    }

    public BatchMapper getBatchMapper() {
        return batchMapper;
    }

    public void setBatchMapper(BatchMapper batchMapper) {
        this.batchMapper = batchMapper;
    }

    public void testPrint() throws Exception {
//        System.out.println("prova");

        List<MachineMaxTicket> machines = batchMapper.getLastIdTicketMachine();
        reader.setMachines(machines);

        JobExecution execution = jobLauncher.run(job, new JobParameters());
//        System.out.println("Exit Status : " + execution.getStatus());
//        execution.wait();
    }


//    private TaskExecutor executor;
//
//    public SchedulerTest() {
//        executor = null;
//    }
//
//    public SchedulerTest(TaskExecutor te) {
//        executor = te;
//    }
//
//    public void callMethod() {
//        executor.execute(new Runnable() {
//            public void run() {
//                System.out.println("prova");
//            }
//        });
//    }
//

}