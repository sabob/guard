package com.github.sabob.guard;

import com.github.sabob.guard.violation.Violation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.constraints.NotNull;
import com.github.sabob.guard.constraints.Required;
import com.github.sabob.guard.domain.Person;
import com.github.sabob.guard.violation.Violations;

import java.util.List;

public class GuardTest {

    private static final Logger LOGGER = LoggerFactory.getLogger( GuardTest.class );

    @Test
    public void testAddPerson() {

        Person person = new Person();
        person.setFirstname( "  " );
        person.setIdNumber( "123" );

        Guard guard = new Guard();

        Violations violations = guard.of( "firstname" )
                                     .code( "FN" )
                                     .value( null )
                                     .constraint( new Required() )
                                     .validate();

        Assertions.assertFalse(violations.isValid());
        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertFalse(violations.isValid("firstname"));
        Assertions.assertTrue(violations.isInvalid("firstname"));

        System.out.println( "firstname Violations: " + violations.getList( "firstname" ) );
        List<Violation> violationList = violations.getList("firstname");
        Violation violation = violationList.get(0);
        System.out.println("message: " +  violation.getMessage());

        violations = guard.of( "idNumber" )
                          .value( person.getIdNumber() )
                          .constraint( new NotNull(), null, "id number cannot be null" )
                          .validate();

        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertTrue(violations.isInvalid("idNumber"));
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
                                     .constraint( new Required() )
                                     .validate();

        LOGGER.info( "Result {}", guard.getViolations().getList() );
        LOGGER.info( "Result {}", guard.getContext() );
    }
}
