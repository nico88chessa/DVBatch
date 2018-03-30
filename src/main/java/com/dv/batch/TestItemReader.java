package com.dv.batch;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.dv.batch.db.bean.MachineMaxTicket;

public class TestItemReader implements ItemReader<MachineMaxTicket> {

    private List<MachineMaxTicket> machines;

    public List<MachineMaxTicket> getMachines() {
        return machines;
    }

    public void setMachines(List<MachineMaxTicket> machines) {
        this.machines = machines;
    }

    @Override
    public MachineMaxTicket read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (machines==null || machines.isEmpty())
            return null;

        MachineMaxTicket machine = machines.remove(0);
        return machine;
    }


}
