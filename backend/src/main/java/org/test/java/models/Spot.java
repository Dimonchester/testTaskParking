package org.test.java.models;

public class Spot {
    private int id;
    private int spotNumber;
    private boolean isAvailable;

    public Spot(){}

    public Spot(int id, int spotNumber, boolean isAvailable){
        this.id = id;
        this.spotNumber = spotNumber;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public boolean getIsAvailable(){
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }
}
