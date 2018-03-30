package com.dv.batch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.batch.item.ItemProcessor;

import com.dv.batch.bean.WriterItem;
import com.dv.batch.db.bean.MachineMaxTicket;
import com.dv.batch.db.bean.RemoteTicket;
import com.dv.batch.db.mapper.RemoteMapper;

public class TestItemProcessor implements ItemProcessor<MachineMaxTicket, WriterItem> {

    private Map<Integer, SqlSessionFactory> listSqlSessionFactory;
    private Map<Integer, String> listIpSqlSessionBinding;


    public Map<Integer, SqlSessionFactory> getListSqlSessionFactory() {
        return listSqlSessionFactory;
    }

    public void setListSqlSessionFactory(Map<Integer, SqlSessionFactory> listSqlSessionFactory) {
        this.listSqlSessionFactory = listSqlSessionFactory;
    }

    public Map<Integer, String> getListIpSqlSessionBinding() {
        return listIpSqlSessionBinding;
    }

    public void setListIpSqlSessionBinding(Map<Integer, String> listIpSqlSessionBinding) {
        this.listIpSqlSessionBinding = listIpSqlSessionBinding;
    }

    /**
     * metodo chiamato dal spring container per inizializzare il bean
     */
    public void init() {
        this.listSqlSessionFactory = new HashMap<Integer, SqlSessionFactory>();
        this.listIpSqlSessionBinding = new HashMap<Integer, String>();
    }

    private void buildSqlSessionFactory(MachineMaxTicket machine) {

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        PooledDataSourceFactory dataSourceFactory = new PooledDataSourceFactory();

        String url = "jdbc:mysql://"+machine.getIp()+":3306/machine?autoReconnect=true&useSSL=false";
        Properties prop = new Properties();
        prop.setProperty("username", "DVBatch");
        prop.setProperty("password", "DVBatch");
        prop.setProperty("driver", "com.mysql.jdbc.Driver");
        prop.setProperty("url", url);

        dataSourceFactory.setProperties(prop);

        DataSource dataSource = dataSourceFactory.getDataSource();
        String envKey = "env-"+machine.getId();

        Environment e = new Environment(envKey, transactionFactory, dataSource);

        Configuration config = new Configuration(e);

        config.setLazyLoadingEnabled(true);
        config.getTypeAliasRegistry().registerAlias(RemoteTicket.class);
        config.addMapper(RemoteMapper.class);

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = builder.build(config);

        listSqlSessionFactory.put(machine.getId(), sessionFactory);
        listIpSqlSessionBinding.put(machine.getId(), machine.getIp());
    }

    @Override
    public WriterItem process(MachineMaxTicket machine) {

        if (!listSqlSessionFactory.containsKey(machine.getId())) {

            // qui devo creare una sqlSessionfactory
            buildSqlSessionFactory(machine);

        } else {

            // qui controllo che l'indirizzo ip non sia cambiato.
            // se e' cambiato, devo ricreare il sqlSessionFactory
            if (!machine.getIp().equals(listIpSqlSessionBinding.get(machine.getId()))) {
                int machineId = machine.getId();
                listIpSqlSessionBinding.remove(machineId);
                listSqlSessionFactory.remove(machineId);
                buildSqlSessionFactory(machine);
            }

        }

        SqlSession session = listSqlSessionFactory.get(machine.getId()).openSession();

        RemoteMapper mapper = session.getMapper(RemoteMapper.class);

        try {
            List<RemoteTicket> tickets = mapper.getTicketsFromId(machine.getMaxTicket());

            if (tickets.isEmpty())
                return null;

            WriterItem writerItem = new WriterItem();

            writerItem.setRemoteTicketList(tickets);
            writerItem.setMachineMaxTicket(machine);

            return writerItem;

        } catch (PersistenceException e) {
            int prova = 0;
            prova += 1;
            // qualcosa e' andato storto
            // interrompo l'esecuzione del batch
            // dovrei loggare...log 4j

        } finally {
            session.close();
        }

        return null;

    }


}
