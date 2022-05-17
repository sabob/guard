package com.github.sabob.guard.constraints;

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
        //atLeast.constraint( new NotEmpty2(), guard.context() );

        Violations violations = guard.validate();

        System.out.println( violations.getList( "lastname" ) );
        System.out.println( "Is valid? " + violations.isValid( "lastname" ) );
    }

    @Test
    public void atLeastOneRequired() {

        String claimFolderId = null;
        String claimReferenceNumber = null;

        AtLeast atLeastOneCriteria = new AtLeast( 1, new Required(), "At least one of claimReferenceNumber or claimFolderId is required" );
        atLeastOneCriteria.values( claimFolderId, claimReferenceNumber );

        GuardException thrown = Assertions.assertThrows( GuardException.class, () -> {
            new Guard()
                    .of( "find-by-id" )
                    .constraint( atLeastOneCriteria )
                    .throwIfInvalid();
        } );

        Assertions.assertTrue( thrown.getViolations().getList().size() == 1 );
    }
}
