package com.github.sabob.guard;

import com.github.sabob.guard.constraints.NotNull;
import com.github.sabob.guard.constraints.Required;
import com.github.sabob.guard.domain.Person;
import com.github.sabob.guard.violation.Violation;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViolationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger( ViolationTest.class );

    static String FIRSTNAME = "firstname";

    static String LASTNAME = "lastname";

    @Test
    public void testEachNameMustHaveOwnViolations() {

        Person person = new Person();
        person.setFirstname( "  " );
        person.setIdNumber( "123" );

        Guard guard = new Guard( FIRSTNAME )
                .value( null )
                .constraint( new Required() );

        Violations violations = guard.of( LASTNAME )
                                     .value( null )
                                     .constraint( new Required() )
                                     .validate();

        System.out.println(violations.getList( FIRSTNAME ));
        System.out.println(violations.getList( LASTNAME ));

        Assertions.assertTrue( violations.getList( FIRSTNAME ).size() == 1 );
        Assertions.assertTrue( violations.getList( LASTNAME ).size() == 1 );
    }

    @Test
    public void  testLatestViolation() {

        Guard guard = new Guard( FIRSTNAME );

        Violation violation = guard
                .value( null )
                .constraint( new NotNull() )
                .constraint( new Required() ).getLatestViolation().orElse( null );

        System.out.println("Latest Violation: " + violation);
        System.out.println("Latest " + FIRSTNAME + " Violation: " + guard.getLatestViolation(FIRSTNAME));
        System.out.println("All Violation: " + guard.getViolations().getList());
    }
}
