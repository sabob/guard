package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.ObjectBean;
import com.github.sabob.guard.domain.PrimBean;
import com.github.sabob.guard.domain.WeekdayEnum;
import com.github.sabob.guard.violation.Violations;

public class EnumTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void negativeTest() {

        Guard guard = new Guard();

        PrimBean primBean = new PrimBean();
        ObjectBean objBean = new ObjectBean();

        String propertyName = "Enum Property";

        objBean.setString( "foo" );

        Violations violations;
        violations = guard.of( propertyName )
                          .value( objBean.getString() )
                          .constraint( new EnumOf( WeekdayEnum.class ) )
                          .validate();

        Assertions.assertFalse( violations.isValid() );
        Assertions.assertTrue( violations.getList( propertyName ).size() > 0 );

        System.out.println( guard.getViolations().getList() );

    }

}








