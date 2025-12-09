package org.test.java.models;


public class Booking {
    private int id;
    private int idCar;
    private int idSpot;
    private String startDate;
    private String endDate;
    private boolean isPaid;
    private boolean isActive;

    public Booking(){};

    public Booking(int id, int idCar, int idSpot, String startDate, String endDate, boolean isPaid, boolean isActive) {
        this.id = id;
        this.idCar = idCar;
        this.idSpot = idSpot;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPaid = isPaid;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public int getIdSpot() {
        return idSpot;
    }

    public void setIdSpot(int idSpot) {
        this.idSpot = idSpot;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean getPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
