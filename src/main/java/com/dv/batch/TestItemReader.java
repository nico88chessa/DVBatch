package com.dv.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.dv.batch.db.bean.MachineMaxTicket;

public class TestItemReader implements ItemReader<MachineMaxTicket> {

    private static Logger logger = LoggerFactory.getLogger(TestItemReader.class);

    private List<MachineMaxTicket> machines;

    public List<MachineMaxTicket> getMachines() {
        return machines;
    }

    public void setMachines(List<MachineMaxTicket> machines) {
        this.machines = machines;
    }

    @Override
    public MachineMaxTicket read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        logger.trace("Enter");

        if (machines==null || machines.isEmpty())
            return null;

        MachineMaxTicket machine = machines.remove(0);
        logger.trace("Exit");

        return machine;
    }


}
