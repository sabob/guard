package com.github.sabob.guard.utils;

public class BasicViolationMessage {

    private String name;

    private String path;

    private String code;

    private String message;

    public BasicViolationMessage() {
    }

    public BasicViolationMessage(String name) {
        this.name = name;
    }

    public BasicViolationMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public BasicViolationMessage(String name, String message, String code) {
        this.name = name;
        this.message = message;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Violation { " +
                "name = " + name +
                "path = " + path +
                ", code = " + code +
                ", message = \"" + message + "\"" +
                " }";
    }
}
