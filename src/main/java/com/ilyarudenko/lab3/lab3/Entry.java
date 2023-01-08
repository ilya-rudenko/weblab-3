package com.ilyarudenko.lab3.lab3;

public class Entry {

    private int id;
    private Float xValue;
    private Float yValue;
    private Integer rValue;

    private long scriptTime;

    private boolean hitResult;

    private String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private boolean checkTriangle() {
        return xValue <=0 && yValue <=0 && (-yValue/2 + -xValue <=(double)rValue/2);
    }

    public void setScriptTime(long scriptTime) {
        this.scriptTime = scriptTime;
    }

    public long getScriptTime() {
        return scriptTime;
    }

    private boolean checkRectangle() {
        return xValue >= 0 && yValue >= 0 && xValue <= rValue && yValue <= (double)rValue/2;
    }

    private boolean checkCircle() {
        return xValue <= 0 && yValue >= 0 && xValue*xValue + yValue*yValue <= (double)rValue*rValue/4;
    }

    public void checkHit() {
        hitResult = checkTriangle() || checkRectangle() || checkCircle();
    }

    public boolean getHitResult() {
        return hitResult;
    }

    public void setHitResult(boolean hitResult) {
        this.hitResult = hitResult;
    }

    public Float getxValue() {
        return xValue;
    }
    public void setxValue(Float xValue) {
        this.xValue = xValue;
    }

    public Float getyValue() {
        return yValue;
    }

    public void setyValue(Float yValue){
        this.yValue=yValue;
    }

    public Integer getrValue() {
        return rValue;
    }

    public void setrValue(Integer rValue) {
        this.rValue = rValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
