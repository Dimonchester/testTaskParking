package org.test.java.dto;

public class BookingDTO {
    private int id;
    private String carNumber;
    private String carBrand;
    private String ownerFio;
    private int spotNumber;
    private String startDate;
    private String endDate;
    private boolean isPaid;
    private boolean isActive;

    public int getId() {
        return id;

    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getOwnerFio() {
        return ownerFio;
    }

    public void setOwnerFio(String ownerFio) {
        this.ownerFio = ownerFio;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
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

    public boolean getIsPaid() {
        return isPaid;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}