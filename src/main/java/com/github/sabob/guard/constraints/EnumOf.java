package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.GuardContext;
import com.github.sabob.guard.utils.StringUtils;
import com.github.sabob.guard.utils.GuardUtils;
import com.github.sabob.guard.validators.Validators;
import com.github.sabob.guard.violation.Violation;

import java.util.EnumSet;
import java.util.StringJoiner;

public class EnumOf implements Constraint {

    protected static final String messageTemplate = GuardUtils.getProperties().getProperty( "enum.of.message" );

    private Class enumClass;

    public EnumOf( Class<? extends java.lang.Enum<?>> enumClass ) {

        boolean isEnum = enumClass.isEnum();

        // Alternative check if enumClass is in fact an enum. This check is useful to check if a enumClass is a Subclass of a specific class
        //boolean isEnum = java.lang.Enum.class.isAssignableFrom(enumClass);
        if ( !isEnum ) {
            throw new IllegalArgumentException( enumClass + " is not an enum" );
        }

        this.enumClass = enumClass;
    }

    @Override
    public void apply( GuardContext guardContext ) {

        Object value = guardContext.getValue();

        boolean valid = isValid( value );

        if ( valid ) {
            return;
        }

        StringJoiner joiner = new StringJoiner( ", " );
        EnumSet<?> enumSet = EnumSet.allOf( enumClass );
        enumSet.forEach( en -> joiner.add( en.name() ) );
        String validValues = joiner.toString();

        String name = StringUtils.capitalize( guardContext.getName() );
        Violation violation = GuardUtils.toViolationWithTemplateMessage( guardContext, messageTemplate,
                name,
                guardContext.getValue(),
                enumClass.getSimpleName(),
                validValues );
        guardContext.addViolation( violation );
    }

    @Override
    public boolean isValid( Object value ) {

        if ( value == null ) {
            return true;
        }

        String strValue = GuardUtils.toString( EnumOf.class.getSimpleName(), value );

        boolean valid = Validators.isValidEnumValue( strValue, enumClass );
        return valid;
    }
}






