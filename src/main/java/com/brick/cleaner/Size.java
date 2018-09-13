package com.brick.cleaner;

public class Size {
    private double size;
    private String type;

    public Size(double size, String type) {
        this.size = size;
        this.type = type;
    }

    public Size() {
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
