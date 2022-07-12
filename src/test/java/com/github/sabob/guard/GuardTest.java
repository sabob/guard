package com.github.sabob.guard;

import com.github.sabob.guard.constraints.Max;
import com.github.sabob.guard.constraints.NotEmpty;
import com.github.sabob.guard.constraints.NotNull;
import com.github.sabob.guard.constraints.NumberOnly;
import com.github.sabob.guard.constraints.Required;
import com.github.sabob.guard.constraints.Size;
import com.github.sabob.guard.domain.Person;
import com.github.sabob.guard.violation.Violation;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GuardTest {

    private static final Logger LOGGER = LoggerFactory.getLogger( GuardTest.class );

    @Test
    public void testAddPerson() {

        Person person = new Person();
        person.setFirstname( "  " );
        person.setIdNumber( "123" );

        Guard guard = new Guard().of( "firstname" );

        Violations violations = guard
                .code( "FN" )
                .value( null )
                .constraint( new Required() )
                .validate();

        Assertions.assertFalse( violations.isValid() );
        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertFalse( violations.isValid( "firstname" ) );
        Assertions.assertTrue( violations.isInvalid( "firstname" ) );

        System.out.println( "firstname Violations: " + violations.getList( "firstname" ) );
        List<Violation> violationList = violations.getList( "firstname" );
        Violation violation = violationList.get( 0 );
        System.out.println( "message: " + violation.getMessage() );

        violations = guard.of( "idNumber" )
                          .value( person.getIdNumber() )
                          .constraint( new NotNull(), null, "id number cannot be null" )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.isInvalid( "idNumber" ) );
        System.out.println( "idNumber Violations: " + violations.getList( "idNumber" ) );
        System.out.println( guard.getContext() );
    }

    @Test
    public void testToJson() throws Exception {

        Person person = new Person();
        person.setFirstname( "" );

        Guard guard = new Guard();

        Violations violations = guard.of( "firstname" )
                                     .code( "FN" )
                                     .value( person.getFirstname() )
                                     .constraint( new NotNull() )
                                     .constraint( new NotEmpty() )
                                     .validate();

        LOGGER.info( "Violations {}", guard.getViolations().getList() );
        LOGGER.info( "Context {}", guard.getContext() );
    }

    @Test
    public void testNullConstructor() {

        Assertions.assertThrows( IllegalArgumentException.class, () -> {

            // cannot use null as guard name
            Guard guard = new Guard( null );
        } );
    }

    @Test
    public void testEmptyConstructor() {

        Assertions.assertThrows( IllegalStateException.class, () -> {

            Guard guard = new Guard();
        } );
    }

    @Test
    public void testNullOf() {

        Assertions.assertThrows( IllegalArgumentException.class, () -> {

            Guard guard = new Guard().of( null );

            // Cannot use the guard until it's name is set
            guard.validate();

        } );
    }

    @Test
    public void testMultipleConstraints() {

        Violations violations = new Guard( "client phone number" )
                .value( "abc123" )
                .constraint( new Size().max(3) ) // value length cannot be greater than 3
                .constraint( new NumberOnly( ) ) // value must consist of numbers only
                .validate();

        LOGGER.info( "Violations {}", violations.getList() );
        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList().size() == 2 );

    }

}
