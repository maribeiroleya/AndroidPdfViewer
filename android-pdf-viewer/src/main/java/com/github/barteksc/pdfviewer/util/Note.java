package com.github.barteksc.pdfviewer.util;

import android.graphics.Bitmap;

public class Note {

    private double xPos;
    private double yPos;
    private String color;

    private Bitmap bitmap;

    public Note(double xPos, double yPos, String color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
    }


    public double getXpos() {
        return xPos;
    }

    public double getYpos() {
        return yPos;
    }

    public String getColor() {
        return color;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
