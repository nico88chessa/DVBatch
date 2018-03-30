package com.dv.batch.bean;

import java.util.List;

import com.dv.batch.db.bean.MachineMaxTicket;
import com.dv.batch.db.bean.RemoteTicket;

public class WriterItem {

    private MachineMaxTicket machineMaxTicket;
    private List<RemoteTicket> remoteTicketList;

    public MachineMaxTicket getMachineMaxTicket() {
        return machineMaxTicket;
    }
    public void setMachineMaxTicket(MachineMaxTicket machineMaxTicket) {
        this.machineMaxTicket = machineMaxTicket;
    }
    public List<RemoteTicket> getRemoteTicketList() {
        return remoteTicketList;
    }
    public void setRemoteTicketList(List<RemoteTicket> remoteTicketList) {
        this.remoteTicketList = remoteTicketList;
    }


}
