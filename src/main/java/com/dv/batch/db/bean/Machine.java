package com.dv.batch.db.bean;

public class Machine {

    private int id;
    private String name;
    private java.util.Date lastUpdate;
    private String ip;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public java.util.Date getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(java.util.Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

}
