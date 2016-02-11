package com.dzordandev.snosand;

import java.io.Serializable;

/**
 * Created by marci on 08.02.2016.
 */
public class CurrentValues implements Serializable {

    String deviceID;
    int tempInside;
    int humInside;
    int tempOutside;
    int pressure;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public int getTempInside() {
        return tempInside;
    }

    public void setTempInside(int tempInside) {
        this.tempInside = tempInside;
    }

    public int getHumInside() {
        return humInside;
    }

    public void setHumInside(int humInside) {
        this.humInside = humInside;
    }

    public int getTempOutside() {
        return tempOutside;
    }

    public void setTempOutside(int tempOutside) {
        this.tempOutside = tempOutside;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }


}
