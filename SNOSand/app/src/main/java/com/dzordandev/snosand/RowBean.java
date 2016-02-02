package com.dzordandev.snosand;

import java.io.Serializable;
import java.util.Date;

public class RowBean implements Serializable {

    public Date dataa;

    public RowBean(int litrow, Date dataa, int przebieg, double koszt) {
        this.litrow = litrow;
        this.dataa = dataa;
        this.przebieg = przebieg;
        this.koszt = koszt;
    }

    public int przebieg;
    public double koszt;
    public int litrow;



}
