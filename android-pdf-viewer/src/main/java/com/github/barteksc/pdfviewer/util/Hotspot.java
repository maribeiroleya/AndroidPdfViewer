package com.github.barteksc.pdfviewer.util;

import android.graphics.Bitmap;

public class Hotspot {

    private double xPos;
    private double yPos;
    private String type;

    private Bitmap bitmap;


    public Hotspot(double xPos, double yPos, String type) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
    }


    public double getXpos() {
        return xPos;
    }

    public double getYpos() {
        return yPos;
    }

    public String getType() {
        return type;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
