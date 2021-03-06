package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.GuardException;
import com.github.sabob.guard.constraints.misc.AtLeast;
import com.github.sabob.guard.domain.Person;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtLeastTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void atLeastOneNotEmpty() {

        Person person = new Person();

        person.setLastname( "Foo" );

        Guard guard = new Guard();
        guard.of( "lastname" );

        AtLeast atLeast = new AtLeast( 2, new NotEmpty(), "At least 2 values are required!" );
        atLeast.values( "one" );
        guard.constraint( atLeast );

        Violations violations = guard.validate();

        System.out.println( violations.getList( "lastname" ) );
        System.out.println( "Is valid? " + violations.isValid( "lastname" ) );
    }

    @Test
    public void atLeastOneRequired() {

        String phoneNumber = null;
        String email = null;
        Constraint emailOrPhoneIsRequired = new Required();

        AtLeast atLeastOneContact = new AtLeast( 1, emailOrPhoneIsRequired, "At least one of claimReferenceNumber or claimFolderId is required" );
        atLeastOneContact.values( phoneNumber, email );

        GuardException thrown = Assertions.assertThrows( GuardException.class, () -> {
            new Guard()
                    .of( "find-by-id" )
                    .constraint( atLeastOneContact )
                    .throwIfInvalid();
        } );

        Assertions.assertTrue( thrown.getViolations().getList().size() == 1 );
    }
}
