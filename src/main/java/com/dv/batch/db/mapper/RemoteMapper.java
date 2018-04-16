package com.dv.batch.db.mapper;

import java.util.List;

import org.mybatis.spring.MyBatisSystemException;

import com.dv.batch.db.bean.RemoteTicket;

public interface RemoteMapper {

    public List<RemoteTicket> getAllTickets() throws MyBatisSystemException;

    public List<RemoteTicket> getTicketsFromId(int id) throws MyBatisSystemException;

}
