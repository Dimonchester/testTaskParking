package org.test.java.models;

public class Car {
    private int id;
    private String carNumber;
    private String brand;
    private int idOwner;

    Car(){};

    Car(int id, String carNumber, String brand, int idOwner){
        this.id = id;
        this.carNumber = carNumber;
        this.brand = brand;
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

    public int getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }
}

