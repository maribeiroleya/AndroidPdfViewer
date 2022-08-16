package com.github.barteksc.pdfviewer.util;

public class Hotspot {

    private double xPos;
    private double yPos;


    public Hotspot(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }


    public double getXpos() {
        return xPos;
    }

    public double getYpos() {
        return yPos;
    }
}
