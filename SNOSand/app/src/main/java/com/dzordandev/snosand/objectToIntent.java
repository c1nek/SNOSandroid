package com.dzordandev.snosand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marci on 27.01.2016.
 */
public class objectToIntent implements Serializable {

    List<RowBean> listaTankowan;
    carDetails carInfo;

    public objectToIntent(List<RowBean> listaTankowan, carDetails carInfo) {
        this.listaTankowan = listaTankowan;
        this.carInfo = carInfo;
    }

    public objectToIntent(objectToIntent det) {
        this.listaTankowan = det.listaTankowan;
        this.carInfo = det.carInfo;
    }




}
