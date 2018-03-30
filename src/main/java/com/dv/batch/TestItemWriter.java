package com.dv.batch;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.dv.batch.bean.WriterItem;
import com.dv.batch.db.bean.Machine;
import com.dv.batch.db.bean.MachineMaxTicket;
import com.dv.batch.db.bean.RemoteTicket;
import com.dv.batch.db.bean.Ticket;
import com.dv.batch.db.mapper.BatchMapper;

public class TestItemWriter implements ItemWriter<WriterItem> {

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

    @Override
    public void write(List<? extends WriterItem> items) throws Exception {

        // una volta che arrivo qui, devo aggiornare l'ultimo id memorizzato
        // e caricare i rimanenti (se ce ne sono)
        // qui devo aggiornare anche l'update sul db centralizzato

        MachineMaxTicket machineMaxTicket = items.get(0).getMachineMaxTicket();
        List<RemoteTicket> remoteTicketList = items.get(0).getRemoteTicketList();

        // se la lista e' vuota, esco
        if (remoteTicketList.isEmpty())
            return;

        // se c'e' almeno un ticket presente nel db centralizzato (maxTicket > 0),
        // verifico se aggiornarlo o meno
        if (machineMaxTicket.getMaxTicket() > 0) {
            // aggiorno solamente se il ticket presente nel db centralizzato e' in stampa (status = 0);
            Ticket t = batchMapper.selectTicket(machineMaxTicket.getId(), machineMaxTicket.getMaxTicket());

            if (t.getPrintStatus() == PRINT_STATUS_PRINTING) {
                RemoteTicket firstRemoteTicket = remoteTicketList.remove(0);
                Ticket firstTicket = getTicketFromRemoteTicket(firstRemoteTicket);
                batchMapper.updateTicket(firstTicket);
            }
        }

        // controllo se dopo il controllo dell'aggiornamento del ticket,
        // ne ho altri o meno; se e' vuoto, esco
        if (remoteTicketList.isEmpty())
            return;

        List<Ticket> ticketList = new ArrayList<Ticket>();
        for (RemoteTicket rt : remoteTicketList)
            ticketList.add(getTicketFromRemoteTicket(machineMaxTicket, rt));

        // e poi aggiungo tutti gli altri
        batchMapper.insertNewTickets(ticketList);

        // aggiorno l'update del tempo nella tabella Machine
        LocalDateTime ldt = LocalDateTime.now();
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        Machine machine2update = new Machine();
        machine2update.setId(machineMaxTicket.getId());
        machine2update.setLastUpdate(out);

        batchMapper.updateMachineUpdateTime(machine2update);

    }

}
