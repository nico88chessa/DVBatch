package com.dv.batch.db.bean;

public class Ticket {

    private int idM;
    private int idT;
    private int queueActivated;
    private int queuePosition;
    private String workId;
    private String fileName;
    private double fileLength;
    private double fileHeight;
    private int filePixelX;
    private int filePixelY;
    private double fileResolutionX;
    private double fileResolutionY;
    private int fileTotalpixelX;
    private int fileTotalpixelY;
    private double fileTotalsizex;
    private double fileTotalsizey;
    private double cylinderLength;
    private double cylinderDiameter;
    private double cylinderStartAt;
    private double cylinderCrossAt;
    private int laserKind;
    private int laserSources;
    private double laserMinPower;
    private double laserMaxPower;
    private double laserShots;
    private double laserPeriod;
    private int setupCalibration;
    private double setupOffsetX;
    private double setupOffsetY;
    private int setupZPresent;
    private double setupZPosition;
    private int setupFocalPresent;
    private double setupFocalPosition;
    private int setupSpeed;
    private String setupEngravingMode;
    private java.util.Date printStartAt;
    private java.util.Date printStopAt;
    private int printStatus;
    private String printError;
    private String notes;

    public int getIdM() {
        return idM;
    }
    public void setIdM(int idM) {
        this.idM = idM;
    }
    public int getIdT() {
        return idT;
    }
    public void setIdT(int idT) {
        this.idT = idT;
    }
    public int getQueueActivated() {
        return queueActivated;
    }
    public void setQueueActivated(int queueActivated) {
        this.queueActivated = queueActivated;
    }
    public int getQueuePosition() {
        return queuePosition;
    }
    public void setQueuePosition(int queuePosition) {
        this.queuePosition = queuePosition;
    }
    public String getWorkId() {
        return workId;
    }
    public void setWorkId(String workId) {
        this.workId = workId;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public double getFileLength() {
        return fileLength;
    }
    public void setFileLength(double fileLength) {
        this.fileLength = fileLength;
    }
    public double getFileHeight() {
        return fileHeight;
    }
    public void setFileHeight(double fileHeight) {
        this.fileHeight = fileHeight;
    }
    public int getFilePixelX() {
        return filePixelX;
    }
    public void setFilePixelX(int filePixelX) {
        this.filePixelX = filePixelX;
    }
    public int getFilePixelY() {
        return filePixelY;
    }
    public void setFilePixelY(int filePixelY) {
        this.filePixelY = filePixelY;
    }
    public double getFileResolutionX() {
        return fileResolutionX;
    }
    public void setFileResolutionX(double fileResolutionX) {
        this.fileResolutionX = fileResolutionX;
    }
    public double getFileResolutionY() {
        return fileResolutionY;
    }
    public void setFileResolutionY(double fileResolutionY) {
        this.fileResolutionY = fileResolutionY;
    }
    public int getFileTotalpixelX() {
        return fileTotalpixelX;
    }
    public void setFileTotalpixelX(int fileTotalpixelX) {
        this.fileTotalpixelX = fileTotalpixelX;
    }
    public int getFileTotalpixelY() {
        return fileTotalpixelY;
    }
    public void setFileTotalpixelY(int fileTotalpixelY) {
        this.fileTotalpixelY = fileTotalpixelY;
    }
    public double getFileTotalsizex() {
        return fileTotalsizex;
    }
    public void setFileTotalsizex(double fileTotalsizex) {
        this.fileTotalsizex = fileTotalsizex;
    }
    public double getFileTotalsizey() {
        return fileTotalsizey;
    }
    public void setFileTotalsizey(double fileTotalsizey) {
        this.fileTotalsizey = fileTotalsizey;
    }
    public double getCylinderLength() {
        return cylinderLength;
    }
    public void setCylinderLength(double cylinderLength) {
        this.cylinderLength = cylinderLength;
    }
    public double getCylinderDiameter() {
        return cylinderDiameter;
    }
    public void setCylinderDiameter(double cylinderDiameter) {
        this.cylinderDiameter = cylinderDiameter;
    }
    public double getCylinderStartAt() {
        return cylinderStartAt;
    }
    public void setCylinderStartAt(double cylinderStartAt) {
        this.cylinderStartAt = cylinderStartAt;
    }
    public double getCylinderCrossAt() {
        return cylinderCrossAt;
    }
    public void setCylinderCrossAt(double cylinderCrossAt) {
        this.cylinderCrossAt = cylinderCrossAt;
    }
    public int getLaserKind() {
        return laserKind;
    }
    public void setLaserKind(int laserKind) {
        this.laserKind = laserKind;
    }
    public int getLaserSources() {
        return laserSources;
    }
    public void setLaserSources(int laserSources) {
        this.laserSources = laserSources;
    }
    public double getLaserMinPower() {
        return laserMinPower;
    }
    public void setLaserMinPower(double laserMinPower) {
        this.laserMinPower = laserMinPower;
    }
    public double getLaserMaxPower() {
        return laserMaxPower;
    }
    public void setLaserMaxPower(double laserMaxPower) {
        this.laserMaxPower = laserMaxPower;
    }
    public double getLaserShots() {
        return laserShots;
    }
    public void setLaserShots(double laserShots) {
        this.laserShots = laserShots;
    }
    public double getLaserPeriod() {
        return laserPeriod;
    }
    public void setLaserPeriod(double laserPeriod) {
        this.laserPeriod = laserPeriod;
    }
    public int getSetupCalibration() {
        return setupCalibration;
    }
    public void setSetupCalibration(int setupCalibration) {
        this.setupCalibration = setupCalibration;
    }
    public double getSetupOffsetX() {
        return setupOffsetX;
    }
    public void setSetupOffsetX(double setupOffsetX) {
        this.setupOffsetX = setupOffsetX;
    }
    public double getSetupOffsetY() {
        return setupOffsetY;
    }
    public void setSetupOffsetY(double setupOffsetY) {
        this.setupOffsetY = setupOffsetY;
    }
    public int getSetupZPresent() {
        return setupZPresent;
    }
    public void setSetupZPresent(int setupZPresent) {
        this.setupZPresent = setupZPresent;
    }
    public double getSetupZPosition() {
        return setupZPosition;
    }
    public void setSetupZPosition(double setupZPosition) {
        this.setupZPosition = setupZPosition;
    }
    public int getSetupFocalPresent() {
        return setupFocalPresent;
    }
    public void setSetupFocalPresent(int setupFocalPresent) {
        this.setupFocalPresent = setupFocalPresent;
    }
    public double getSetupFocalPosition() {
        return setupFocalPosition;
    }
    public void setSetupFocalPosition(double setupFocalPosition) {
        this.setupFocalPosition = setupFocalPosition;
    }
    public int getSetupSpeed() {
        return setupSpeed;
    }
    public void setSetupSpeed(int setupSpeed) {
        this.setupSpeed = setupSpeed;
    }
    public String getSetupEngravingMode() {
        return setupEngravingMode;
    }
    public void setSetupEngravingMode(String setupEngravingMode) {
        this.setupEngravingMode = setupEngravingMode;
    }
    public java.util.Date getPrintStartAt() {
        return printStartAt;
    }
    public void setPrintStartAt(java.util.Date printStartAt) {
        this.printStartAt = printStartAt;
    }
    public java.util.Date getPrintStopAt() {
        return printStopAt;
    }
    public void setPrintStopAt(java.util.Date printStopAt) {
        this.printStopAt = printStopAt;
    }
    public int getPrintStatus() {
        return printStatus;
    }
    public void setPrintStatus(int printStatus) {
        this.printStatus = printStatus;
    }
    public String getPrintError() {
        return printError;
    }
    public void setPrintError(String printError) {
        this.printError = printError;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
