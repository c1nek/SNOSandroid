package com.dzordandev.snosand;

import java.io.Serializable;
import java.util.Date;

public class RowBean implements Serializable {

    public int id;
    public Date dateDate;
    public byte type;

    public RowBean(int id, Date dateDate, byte type) {
        this.id = id;
        this.dateDate = dateDate;
        this.type = type;
    }

}
