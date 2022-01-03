package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.violation.Violations;

public class TrueOnlyTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void testValueSetAtFalse() {

        Guard guard = new Guard();
        Violations violations;

        boolean testTrue = false;

        violations = guard.of( "test 1" )
                          .value( testTrue )
                          .constraint( new TrueOnly() )
                          .validate();

        System.out.println( violations.getList( "test 1" ) );
        Assertions.assertTrue( violations.isInvalid() );

    }

}
