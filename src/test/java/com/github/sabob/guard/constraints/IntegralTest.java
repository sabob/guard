package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.PrimBean;
import com.github.sabob.guard.violation.Violations;

public class IntegralTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void testMax() {

        Guard guard = new Guard();
        Violations violations;

        PrimBean bean = new PrimBean();
        bean.setFloat( 100.0000001f );

        violations = guard.of( "test.1" )
                          .value( bean.getFloat() )
                          .constraint( new IntegralLength( 0, 2 ) )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );

        System.out.println( guard.getViolations().getList() );

    }

    @Test
    public void testMinList() {

        Guard guard = new Guard();
        Violations violations;

        PrimBean bean = new PrimBean();
        bean.setFloat( 1.1f );

        violations = guard.of( "test.2" )
                          .value( bean.getFloat() )
                          .constraint( new IntegralLength( 5, 0 ) )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );
        System.out.println( guard.getViolations().getList() );

    }

    @Test
    public void testNull() {

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of( "test.3" )
                          .value( null )
                          .constraint( new IntegralLength( 10, 15 ) )
                          .validate();

        System.out.println( guard.getViolations().getList() );
        Assertions.assertTrue( violations.isValid() );

    }

}
