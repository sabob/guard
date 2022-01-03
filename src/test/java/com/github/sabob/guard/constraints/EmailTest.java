package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.ObjectBean;
import com.github.sabob.guard.domain.PrimBean;
import com.github.sabob.guard.violation.Violations;

public class EmailTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void negativeTest() {

        Guard guard = new Guard();

        PrimBean primBean = new PrimBean();
        ObjectBean objBean = new ObjectBean();

        String propertyName = "Email property";

        objBean.setString( "foo" );

        Violations violations;
        violations = guard.of( propertyName )
                          .value( objBean.getString() )
                          .constraint( new Email() )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.isInvalid( propertyName ) );

        System.out.println( guard.getViolations().getList() );

    }

    @Test
    public void testValidMails() {

        Guard guard = new Guard();

        PrimBean primBean = new PrimBean();
        ObjectBean objBean = new ObjectBean();

        String propertyName = "Email property";

        objBean.setString( "baa123@gmail.com" );

        Violations violations;
        violations = guard.of( propertyName )
                          .value( objBean.getString() )
                          .constraint( new Email() )
                          .validate();

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.isValid( propertyName ) );
    }

    @Test
    public void testInvalidMails() {

        Guard guard = new Guard();

        String propertyName = "Email property";

        Violations violations;
        violations = guard.of( propertyName )
                          .value( "baa123@gmail" )
                          .constraint( new Email() )
                          .validate();
        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( propertyName ).size() == 1 );

        guard.getViolations().clear();

        violations = guard.value( "baa123@gmail" )
                          .constraint( new Email() )
                          .validate();

        boolean valid = violations.getList( propertyName ).size() == 1;

        Assertions.assertTrue( valid );

        guard.getViolations().clear();

        violations = guard.value( "123" )
                          .constraint( new Email() )
                          .validate();
        Assertions.assertTrue( violations.getList( propertyName ).size() == 1 );

        System.out.println( violations.getList( propertyName ) );
    }
}



