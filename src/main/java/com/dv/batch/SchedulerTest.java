package com.dv.batch;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import com.dv.batch.db.bean.MachineMaxTicket;
import com.dv.batch.db.mapper.BatchMapper;

public class SchedulerTest {

    private static Logger logger = LoggerFactory.getLogger(SchedulerTest.class);

    private JobLauncher jobLauncher;
    private Job job;
    private TestItemReader reader;
    private TestItemProcessor processor;
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

    public TestItemProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(TestItemProcessor processor) {
        this.processor = processor;
    }

    public void startBatch() throws Exception {

        logger.trace("enter");

        // prima di tutto elimino le macchine da eliminare (flag TO_DELETE a true)
        List<MachineMaxTicket> machines = Collections.<MachineMaxTicket>emptyList();
        try {
            batchMapper.deleteMachine();

            machines = batchMapper.getLastIdTicketMachine();
            reader.setMachines(machines);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }

        // qui elimino gli oggetti sql del processor associati ai db eliminati
        Map<Integer, SqlSessionFactory> listSqlSessionFactory = processor.getListSqlSessionFactory();
        MachineMaxTicket compareMachine = new MachineMaxTicket();
        for (int key : listSqlSessionFactory.keySet()) {
            compareMachine.setId(key);
            if (!machines.contains(compareMachine)) {
                listSqlSessionFactory.remove(key);
            }
        }

        Map<Integer, String> listIpSqlSessionBinding = processor.getListIpSqlSessionBinding();
        for (int key : listIpSqlSessionBinding.keySet()) {
            compareMachine.setId(key);
            if (!machines.contains(compareMachine)) {
                listIpSqlSessionBinding.remove(key);
            }
        }

        if (machines.isEmpty())
            return;

        logger.debug("startBatch will call job execution");

        // qui parto con il job
//        JobExecution execution = jobLauncher.run(job, new JobParameters());
        jobLauncher.run(job, new JobParameters());

        logger.trace("exit");
    }

}