package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.MonthEnum;
import com.github.sabob.guard.domain.ObjectBean;
import com.github.sabob.guard.domain.WeekdayEnum;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void positiveStringTest() {

        Guard guard = new Guard();

        String propertyName = "Enum Property";

        Violations violations;
        violations = guard.of( propertyName )
                          .value( WeekdayEnum.MONDAY.name() )
                          .constraint( new EnumOf( WeekdayEnum.class ) )
                          .validate();

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( propertyName ).isEmpty() );

        LOGGER.info( guard.getViolations().getList().toString() );

    }

    @Test
    public void positiveEnumTest() {

        Guard guard = new Guard();

        String propertyName = "Enum Property";

        Violations violations;
        violations = guard.of( propertyName )
                          .value( WeekdayEnum.MONDAY )
                          .constraint( new EnumOf( WeekdayEnum.class ) )
                          .validate();

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( propertyName ).isEmpty() );

        LOGGER.info( guard.getViolations().getList().toString() );

    }

    @Test
    public void negativeStringTest() {

        Guard guard = new Guard();

        ObjectBean objBean = new ObjectBean();

        String propertyName = "Enum Property";

        objBean.setString( "foo" );

        Violations violations;
        violations = guard.of( propertyName )
                          .value( objBean.getString() )
                          .constraint( new EnumOf( WeekdayEnum.class ) )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( propertyName ).size() > 0 );

        LOGGER.info( guard.getViolations().getList().toString() );

    }

    @Test
    public void negativeEnumTest() {

        Guard guard = new Guard();

        String propertyName = "Enum Property";

        Violations violations;
        violations = guard.of( propertyName )
                          .value( MonthEnum.JANUARY )
                          .constraint( new EnumOf( WeekdayEnum.class ) )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( propertyName ).size() > 0 );

        LOGGER.info( guard.getViolations().getList().toString() );
    }

}








