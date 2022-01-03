package com.github.sabob.guard.domain;

import java.util.Date;

public class Person {

    private String firstname;

    private String lastname;

    private String idNumber;

    private Integer age;

    private boolean male;

    private Date dob;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname( String firstname ) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname( String lastname ) {
        this.lastname = lastname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber( String idNumber ) {
        this.idNumber = idNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge( Integer age ) {
        this.age = age;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale( boolean male ) {
        this.male = male;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob( Date dob ) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                '}';
    }
}
