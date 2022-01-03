package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.Person;
import com.github.sabob.guard.domain.PrimBean;
import com.github.sabob.guard.violation.Violations;

public class FractionTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void maxTest() {

        Guard guard = new Guard();
        Violations violations;

        PrimBean bean = new PrimBean();
        bean.setFloat( 0.0000001f );

        violations = guard.of( "maxTest" )
                          .value( bean.getFloat() )
                          .constraint( new FractionLength( 0, 2 ) )
                          .validate();

        LOGGER.info( "Result {}", guard.getViolations().getList() );
        Assertions.assertTrue( violations.isInvalid() );

    }

    @Test
    public void minTest() {

        Guard guard = new Guard();
        Violations violations;
        Person person = new Person();

        PrimBean bean = new PrimBean();
        bean.setFloat( 0.1f );

        violations = guard.of( "minTest" )
                          .value( bean.getFloat() )
                          .constraint( new FractionLength( 5, 0 ) )
                          .validate();

        LOGGER.info( "Result {}", guard.getViolations().getList() );
        Assertions.assertTrue( violations.isInvalid() );

    }

    @Test
    public void fractionTest() {

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of( "fractionTest" )
                          .value( null )
                          .constraint( new Size( 10, 15 ) )
                          .validate();

        LOGGER.info( "Result {}", guard.getViolations().getList() );

        Assertions.assertTrue( violations.isValid() );

    }

}
