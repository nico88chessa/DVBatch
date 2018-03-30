package com.dv.batch.db.mapper;

import java.util.List;

import com.dv.batch.db.bean.RemoteTicket;

public interface RemoteMapper {

    public List<RemoteTicket> getAllTickets();

    public List<RemoteTicket> getTicketsFromId(int id);

}
