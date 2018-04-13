package com.dv.batch.db.mapper;


import java.util.List;

import com.dv.batch.db.bean.Machine;
import com.dv.batch.db.bean.MachineMaxTicket;
import com.dv.batch.db.bean.Ticket;

public interface BatchMapper {

    public List<Machine> selectMachine();

    public Ticket selectTicket(int idm, int idt);

    public int getMaxTicketIdOfMachine(int idMachine);

    public List<MachineMaxTicket> getLastIdTicketMachine();

    public void deleteMachine();

    public void updateTicket(Ticket ticket);

    public void updateMachineUpdateTime(Machine machine);

    public void insertNewTickets(List<Ticket> tickets);

}
