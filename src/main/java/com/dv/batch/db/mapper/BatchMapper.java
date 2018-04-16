package com.dv.batch.db.mapper;


import java.util.List;

import org.mybatis.spring.MyBatisSystemException;

import com.dv.batch.db.bean.Machine;
import com.dv.batch.db.bean.MachineMaxTicket;
import com.dv.batch.db.bean.Ticket;

public interface BatchMapper {

    public List<Machine> selectMachine() throws MyBatisSystemException;

    public Ticket selectTicket(int idm, int idt) throws MyBatisSystemException;

    public int getMaxTicketIdOfMachine(int idMachine) throws MyBatisSystemException;

    public List<MachineMaxTicket> getLastIdTicketMachine() throws MyBatisSystemException;

    public void deleteMachine() throws MyBatisSystemException;

    public void updateTicket(Ticket ticket) throws MyBatisSystemException;

    public void updateMachineUpdateTime(Machine machine) throws MyBatisSystemException;

    public void insertNewTickets(List<Ticket> tickets) throws MyBatisSystemException;

}
