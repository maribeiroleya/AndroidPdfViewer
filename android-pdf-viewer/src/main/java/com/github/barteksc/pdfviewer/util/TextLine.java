package com.github.barteksc.pdfviewer.util;

public class TextLine {


    private double fontSize;
    private String fontColor;
    private int fontAlpha;
    private String text;

    private float letterSpace;


    public TextLine(double fontSize, String fontColor, float fontAlpha, String text) {
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.fontAlpha = (int)(255*fontAlpha);
        this.text = text;
        this.letterSpace = 0.0f;
    }


    public double getFontSize() {
        return this.fontSize;
    }


    public String getFontColor() {
        return this.fontColor;
    }


    public int getFontAlpha() {
        return this.fontAlpha;
    }


    public String getText() {
        return this.text;
    }


    public void setLetterSpace(float letterSpace) {
        this.letterSpace = letterSpace;
    }


    public float getLetterSpace() {
        return letterSpace;
    }
}
