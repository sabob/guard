package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.DateBean;
import com.github.sabob.guard.violation.Violations;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PastTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void dateInFutureTest() {

        Violations violations;
        Guard guard = new Guard();
        DateBean bean = new DateBean();

        bean.setLocalDate( LocalDate.of( 2024, 1, 8 ) );

        violations = guard.of( "test.1" )
                          .value( bean.getLocalDate() )
                          .constraint( new Past() )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( "test.1" ).size() > 0 );

        System.out.println( guard.getViolations().getList() );

    }

    @Test
    public void dateInFutureWithTime() {

        Violations violations;
        Guard guard = new Guard();
        DateBean bean = new DateBean();

        bean.setLocalDateTime( LocalDateTime.of( 2025, 1, 8, 11, 59 ) );

        violations = guard.of( "test.2" )
                          .value( bean.getLocalDateTime() )
                          .constraint( new Past() )
                          .validate();

        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.getList( "test.2" ).size() > 0 );

        System.out.println( guard.getViolations().getList() );

    }

    @Test
    public void dateInPastWithTimeTest() {

        Violations violations;
        Guard guard = new Guard();
        DateBean bean = new DateBean();

        bean.setLocalDateTime( LocalDateTime.of( 2019, 1, 8, 11, 59 ) );

        violations = guard.of( "test.3" )
                          .value( bean.getLocalDateTime() )
                          .constraint( new Past() )
                          .validate();

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( "test.3" ).isEmpty() );

    }

    @Test
    public void dateInPastTest() {

        Violations violations;
        Guard guard = new Guard();
        DateBean bean = new DateBean();

        bean.setLocalDate( LocalDate.of( 2021, 8, 19 ) );

        violations = guard.of( "test.4" )
                          .value( bean.getLocalDate() )
                          .constraint( new Past() )
                          .validate();

        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.getList( "test.4" ).isEmpty() );
    }
}
