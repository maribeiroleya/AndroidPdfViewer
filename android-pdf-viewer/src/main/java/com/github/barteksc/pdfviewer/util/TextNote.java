package com.github.barteksc.pdfviewer.util;

import java.util.List;

public class TextNote {


    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private String backgroundColor;
    private int backgroundAlpha;
    private String borderColor;
    private int borderSize;
    private int borderAlpha;
    private List<TextLine> lines;


    public TextNote(
            double xPos,
            double yPos,
            double width,
            double height,
            String backgroundColor,
            float backgroundAlpha,
            String borderColor,
            int borderSize,
            float borderAlpha,
            List<TextLine> lines) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.backgroundAlpha = (int)(255*backgroundAlpha);
        this.borderColor = borderColor;
        this.borderSize = borderSize;
        this.borderAlpha = (int)(255*borderAlpha);
        this.lines = lines;
    }


    public double getXpos() {
        return xPos;
    }

    public double getYpos() {
        return yPos;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public int getBackgroundAlpha() { return backgroundAlpha; }

    public String getBorderColor() {
        return borderColor;
    }

    public int getBorderSize() { return borderSize; }

    public int getBorderAlpha() { return borderAlpha; }


    public List<TextLine> getLines() {
        return this.lines;
    }
}
