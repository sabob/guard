package com.github.sabob.guard.violation;

public class Violation {

    private String name;

    private String code;

    private String message;

    private Object value;

    public Violation() {
    }

    public Violation( String name ) {
        this.name = name;
    }

    public Violation( String name, String message ) {
        this.name = name;
        this.message = message;
    }

    public Violation( String name, String message, String code ) {
        this.name = name;
        this.message = message;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue( Object value ) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Violation { " +
                "name = " + name +
                ", code = " + code +
                ", value = " + value +
                ", message = \"" + message + "\"" +
                " }";
    }
}
