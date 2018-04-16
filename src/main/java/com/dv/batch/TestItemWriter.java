package com.dv.batch;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.dv.batch.bean.WriterItem;
import com.dv.batch.db.bean.Machine;
import com.dv.batch.db.bean.MachineMaxTicket;
import com.dv.batch.db.bean.RemoteTicket;
import com.dv.batch.db.bean.Ticket;
import com.dv.batch.db.mapper.BatchMapper;

public class TestItemWriter implements ItemWriter<WriterItem> {

    private static Logger logger = LoggerFactory.getLogger(TestItemWriter.class);

    private static final int PRINT_STATUS_OK = 1;
    private static final int PRINT_STATUS_ERROR = -1;
    private static final int PRINT_STATUS_PRINTING = 0;

    private BatchMapper batchMapper;

    public BatchMapper getBatchMapper() {
        return batchMapper;
    }

    public void setBatchMapper(BatchMapper batchMapper) {
        this.batchMapper = batchMapper;
    }

    private Ticket getTicketFromRemoteTicket(RemoteTicket ticket) {
        Ticket t = new Ticket();

        t.setIdT(ticket.getId());
        t.setQueueActivated(ticket.getQueueActivated());
        t.setQueuePosition(ticket.getQueuePosition());
        t.setWorkId(ticket.getWorkId());
        t.setFileName(ticket.getFileName());
        t.setFileLength(ticket.getFileLength());
        t.setFileHeight(ticket.getFileHeight());
        t.setFilePixelX(ticket.getFilePixelX());
        t.setFilePixelY(ticket.getFilePixelY());
        t.setFileResolutionX(ticket.getFileResolutionX());
        t.setFileResolutionY(ticket.getFileResolutionY());
        t.setFileTotalpixelX(ticket.getFileTotalpixelX());
        t.setFileTotalpixelY(ticket.getFileTotalpixelY());
        t.setFileTotalsizex(ticket.getFileTotalsizex());
        t.setFileTotalsizey(ticket.getFileTotalsizey());
        t.setCylinderLength(ticket.getCylinderLength());
        t.setCylinderDiameter(ticket.getCylinderDiameter());
        t.setCylinderStartAt(ticket.getCylinderStartAt());
        t.setCylinderCrossAt(ticket.getCylinderCrossAt());
        t.setLaserKind(ticket.getLaserKind());
        t.setLaserSources(ticket.getLaserSources());
        t.setLaserMinPower(ticket.getLaserMinPower());
        t.setLaserMaxPower(ticket.getLaserMaxPower());
        t.setLaserShots(ticket.getLaserShots());
        t.setLaserPeriod(ticket.getLaserPeriod());
        t.setSetupCalibration(ticket.getSetupCalibration());
        t.setSetupOffsetX(ticket.getSetupOffsetX());
        t.setSetupOffsetY(ticket.getSetupOffsetY());
        t.setSetupZPresent(ticket.getSetupZPresent());
        t.setSetupZPosition(ticket.getSetupZPosition());
        t.setSetupFocalPresent(ticket.getSetupFocalPresent());
        t.setSetupFocalPosition(ticket.getSetupFocalPosition());
        t.setSetupSpeed(ticket.getSetupSpeed());
        t.setSetupEngravingMode(ticket.getSetupEngravingMode());
        t.setPrintStartAt(ticket.getPrintStartAt());
        t.setPrintStopAt(ticket.getPrintStopAt());
        t.setPrintStatus(ticket.getPrintStatus());
        t.setPrintError(ticket.getPrintError());

        return t;
    }

    private Ticket getTicketFromRemoteTicket(MachineMaxTicket machine, RemoteTicket ticket) {
        Ticket t = getTicketFromRemoteTicket(ticket);
        t.setIdM(machine.getId());
        return t;
    }

    /*
     * IMPORTANTE: il numero massimo di elementi presenti nella lista items dipende dal parametro
     * commit-interval presente nel file batch-context.xml
     * quindi se commit-interval e' 2, la lista contiene al piu' 2 elementi
     */
    @Override
    public void write(List<? extends WriterItem> items) throws Exception {

        logger.trace("Enter");

        // una volta che arrivo qui, devo aggiornare l'ultimo id memorizzato
        // e caricare i rimanenti (se ce ne sono)
        // qui devo aggiornare anche l'update sul db centralizzato

        for (WriterItem item: items) {

            logger.debug("write item with machine id: "+item.getMachineMaxTicket().getId());

            MachineMaxTicket machineMaxTicket = item.getMachineMaxTicket();
            List<RemoteTicket> remoteTicketList = item.getRemoteTicketList();

            // se la lista e' vuota, esco
            if (remoteTicketList.isEmpty())
                continue;

            // se c'e' almeno un ticket presente nel db centralizzato (maxTicket > 0),
            // verifico se aggiornarlo o meno
            if (machineMaxTicket.getMaxTicket() > 0) {
                // aggiorno solamente se il ticket presente nel db centralizzato e' in stampa (status = 0);
                Ticket t = null;
                try {
                    t = batchMapper.selectTicket(machineMaxTicket.getId(), machineMaxTicket.getMaxTicket());
                } catch (MyBatisSystemException | PersistenceException e) {
                    logger.error("MachineMaxTicket values");
                    logger.error("id: "+machineMaxTicket.getId());
                    logger.error("maxTicket: "+machineMaxTicket.getMaxTicket());
                    logger.error("ip: "+machineMaxTicket.getIp());
                    logger.error(e.getMessage());
                    continue;
                }

                // se l'ultimo ticket inserito corrisponde all'ultimo presente in macchina verifico
                if (t.getIdT() == machineMaxTicket.getMaxTicket()) {

					// lo tolgo dalla lista e, se prima era in stampa, aggiorno lo stato
            		RemoteTicket firstRemoteTicket = remoteTicketList.remove(0);

                    if (t.getPrintStatus() == PRINT_STATUS_PRINTING) {
    	                Ticket firstTicket = getTicketFromRemoteTicket(firstRemoteTicket);
        	            try {
                            batchMapper.updateTicket(firstTicket);
                        } catch (MyBatisSystemException | PersistenceException e) {
                            logger.error("FirstTicket values");
                            logger.error("idM: "+firstTicket.getIdM());
                            logger.error("idT: "+firstTicket.getIdT());
                            logger.error(e.getMessage());
                            continue;
                        }
                    }
                }
            }

            // controllo se dopo il controllo dell'aggiornamento del ticket,
            // ne ho altri o meno; se e' vuoto, esco
            if (remoteTicketList.isEmpty())
                continue;

            List<Ticket> ticketList = new ArrayList<Ticket>();
            for (RemoteTicket rt : remoteTicketList)
                ticketList.add(getTicketFromRemoteTicket(machineMaxTicket, rt));

            // e poi aggiungo tutti gli altri
            try {
                batchMapper.insertNewTickets(ticketList);
            } catch (MyBatisSystemException | PersistenceException e) {
                logger.error("Insert new tickets error");
                logger.error("TicketList size: "+ticketList.size());
                logger.error(e.getMessage());
                continue;
            }

            // aggiorno l'update del tempo nella tabella Machine
            LocalDateTime ldt = LocalDateTime.now();
            Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

            Machine machine2update = new Machine();
            machine2update.setId(machineMaxTicket.getId());
            machine2update.setLastUpdate(out);

            try {
                batchMapper.updateMachineUpdateTime(machine2update);
            } catch (MyBatisSystemException | PersistenceException e) {
                logger.error("Update machine update time");
                logger.error("Machine values");
                logger.error("id: "+machine2update.getId());
                logger.error("ip: "+machine2update.getIp());
                logger.error("name: "+machine2update.getName());
                logger.error("last update: "+machine2update.getLastUpdate());
                logger.error("to delete: "+machine2update.isToDelete());
                logger.error(e.getMessage());
            }

        }

        logger.trace("Exit");

    }

}
