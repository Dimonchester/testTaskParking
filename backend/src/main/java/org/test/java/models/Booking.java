package org.test.java.models;


import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int idCar;
    private int idSpot;
    private LocalDateTime startDate;
    private LocalDateTime  endDate;
    private boolean isPaid;

    public Booking(){};

    public Booking(int id, int idCar, int idSpot, LocalDateTime  startDate, LocalDateTime  endDate, boolean isPaid) {
        this.id = id;
        this.idCar = idCar;
        this.idSpot = idSpot;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPaid = isPaid;
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

    public LocalDateTime  getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime  startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime  getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime  endDate) {
        this.endDate = endDate;
    }

    public boolean getPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
