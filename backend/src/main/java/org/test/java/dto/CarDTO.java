package org.test.java.dto;

public class CarDTO {
    private int id;
    private String carNumber;
    private String brand;
    private String ownerFio;
    private int idOwner;

    public CarDTO() {};

    public CarDTO(int id, String carNumber, String brand, String ownerFio, int idOwner){
        this.id = id;
        this.carNumber = carNumber;
        this.brand = brand;
        this.ownerFio = ownerFio;
        this.idOwner = idOwner;
    }

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOwnerFio() {
        return ownerFio;
    }

    public void setOwnerFio(String ownerFio) {
        this.ownerFio = ownerFio;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }
}
