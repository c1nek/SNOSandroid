package com.dzordandev.snosand;

import java.util.Date;

public class ChartDataObject {
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ChartDataObject(Date date, int value) {
        this.date = date;
        this.value = value;
    }

    Date date;
    int value;
}
