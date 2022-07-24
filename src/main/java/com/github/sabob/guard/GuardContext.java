package com.github.sabob.guard;

import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.violation.Violation;
import com.github.sabob.guard.violation.Violations;

import java.util.List;
import java.util.Optional;

public class GuardContext {

    protected boolean nameWasSet = false;

    protected String name;

    protected boolean codeWasSet = false;

    protected String code;

    protected boolean messageWasSet = false;

    protected String message;

    protected boolean valueWasSet = false;

    protected Object value;

    Violations violations = new Violations();

    public GuardContext() {
    }

    public GuardContext( Object value, String message ) {
        setValue( value );
        setMessage( message );
    }

    GuardContext( String name ) {
        setName( name );
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        if ( StringUtils.isBlank( name ) ) {
            throw new IllegalArgumentException( "The name to guard cannot be null. Usage: new Guard(\"some_name\") or myGuard.of(\"some_name\")" );
        }
        this.nameWasSet = true;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.codeWasSet = true;
        this.code = code;
    }

    public GuardContext code( String code ) {
        setCode( code );
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.messageWasSet = true;
        this.message = message;
    }

    public GuardContext message( String message ) {
        setMessage( message );
        return this;
    }

    public Object getValue() {
        return value;
    }

    public void setValue( Object value ) {
        this.valueWasSet = true;
        this.value = value;
    }

    public GuardContext value( Object value ) {
        setValue( value );
        return this;
    }

    public Violation toViolation() {
        Violation violation = new Violation();
        violation.setCode( getCode() );
        violation.setName( getName() );
        violation.setValue( getValue() );
        violation.setMessage( getMessage() );
        return violation;
    }

    @Override
    public GuardContext clone() {
        GuardContext clone = new GuardContext( getName() );
        clone.setCode( getCode() );
        clone.setMessage( getMessage() );

        if ( this.valueWasSet ) {
            clone.setValue( getValue() );
        }

        return clone;
    }

    public GuardContext merge( GuardContext guardContext ) {
        GuardContext clone = clone();

        if ( guardContext.nameWasSet ) {
            clone.setName( guardContext.getName() );
        }

        if ( guardContext.codeWasSet ) {
            clone.setCode( guardContext.getCode() );
        }

        if ( guardContext.messageWasSet ) {
            clone.setMessage( guardContext.getMessage() );
        }

        if ( guardContext.valueWasSet ) {
            clone.setValue( guardContext.getValue() );
        }

        return clone;
    }

    public boolean hasValue() {
        return valueWasSet;
    }

    public void addViolation( Violation violation ) {
        getViolations().add( violation );
    }

    public Violations getViolations() {
        return violations;
    }

    public void setViolations( Violations violations ) {
        this.violations = violations;
    }

    public Optional<Violation> getLatestViolation() {
        return getLatestViolation( getName() );
    }

    public Optional<Violation> getLatestViolation( String name ) {
        List<Violation> list = getViolations().getList( name );

        if ( list.isEmpty() ) {
            return Optional.empty();
        }

        Violation violation = list.get( list.size() - 1 );
        return Optional.of( violation );

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "GuardContext { " +
                "name = " + name +
                ", code = " + code );

        if ( message == null ) {
            sb.append( ", message = null" );
        } else {
            sb.append( ", message = \"" + message + "\"" );
        }
        sb.append( ", value = " + value + " }" );

        return sb.toString();
    }
}

