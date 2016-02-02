package com.dzordandev.snosand;

import java.io.Serializable;

/**
 * Created by marci on 27.01.2016.
 */
public class carDetails implements Serializable {

    String producent;
    String model;
    int fuel;
    int mileage;
    int motor;
    String user;

    public carDetails(String producent, String model, int fuel, int mileage, int motor) {
        this.producent = producent;
        this.model = model;
        this.fuel = fuel;
        this.mileage = mileage;
        this.motor = motor;
    }

    public carDetails(carDetails det) {
        this.producent = det.getProducent();
        this.model = det.getModel();
        this.fuel = det.getFuel();
        this.mileage = det.getMileage();
        this.motor = det.getMotor();
        this.user = det.getUser();
    }

    public String getProducent() {
        return producent;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getMotor() {
        return motor;
    }

    public void setMotor(int motor) {
        this.motor = motor;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}
