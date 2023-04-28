package com.github.sabob.guard.domain;

public class ObjectBean {

    private String stringObj;

    private Boolean booleanObj;

    private Integer integerObj;

    private Double doubleObj;

    private Float floatObj;

    public String getString() {
        return stringObj;
    }

    public void setString(String stringObj) {
        this.stringObj = stringObj;
    }

    public Integer getInteger() {
        return integerObj;
    }

    public void setInteger(Integer integerObj) {
        this.integerObj = integerObj;
    }

    public Boolean getBoolean() {
        return booleanObj;
    }

    public void setBoolean(Boolean booleanObj) {
        this.booleanObj = booleanObj;
    }

    public Double getDoubleObj() {
        return doubleObj;
    }

    public void setDoubleObj(Double doubleObj) {
        this.doubleObj = doubleObj;
    }

    public Float getFloatObj() {
        return floatObj;
    }

    public void setFloatObj(Float floatObj) {
        this.floatObj = floatObj;
    }
}

