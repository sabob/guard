package za.co.momentum.risk.validation.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.momentum.risk.validation.guard.Guard;
import za.co.momentum.risk.validation.guard.domain.Person;
import za.co.momentum.risk.validation.guard.violation.Violations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RequiredTest {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Test
    public void requiredValidTest() {

        Person person = new Person();

        person.setLastname( "Foo" );

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of( "lastname" )
                          .value( person.getLastname() )
                          .constraint( new Required() )
                          .validate();

        LOGGER.info( "Result {}", violations.getList( "lastname" ) );
        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.isValid( "lastname" ) );

    }

    @Test
    public void requiredInvalidNullTest() {

        Person person = new Person();

        person.setLastname( null );

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of( "lastname" )
                          .value( person.getLastname() )
                          .constraint( new Required() )
                          .validate();

        LOGGER.info( "Result {}", violations.getList( "lastname" ) );
        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.isInvalid( "lastname" ) );

    }

    @Test
    public void requiredInvalidBlankTest() {

        Person person = new Person();

        person.setLastname( "  " );

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of( "lastname" )
                          .value( person.getLastname() )
                          .constraint( new Required() )
                          .validate();

        LOGGER.info( "Result {}", violations.getList( "lastname" ) );
        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.isInvalid( "lastname" ) );

    }

    @Test
    public void requiredInvalidEmptyTest() {

        Person person = new Person();

        person.setLastname( "" );

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of( "lastname" )
                          .value( person.getLastname() )
                          .constraint( new Required() )
                          .validate();

        LOGGER.info( "Result {}", violations.getList( "lastname" ) );
        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.isInvalid( "lastname" ) );
    }

    @Test
    public void requiredValidRequiredTest() {

        Person person = new Person();

        person.setLastname( "a" );

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of( "lastname" )
                          .value( person.getLastname() )
                          .constraint( new Required() )
                          .validate();

        LOGGER.info( "Result {}", violations.getList( "lastname" ) );
        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.isValid( "lastname" ) );

    }

    @Test
    public void requiredInvalidListTest() {

        List value = new ArrayList<>();

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of( "list" )
                          .value( value )
                          .constraint( new Required() )
                          .validate();

        LOGGER.info( "Result {}", violations.getList( "list" ) );
        Assertions.assertTrue( violations.isInvalid() );
        Assertions.assertTrue( violations.isInvalid( "list" ) );

    }

    @Test
    public void requiredValidListTest() {

        Collection value = new ArrayList<>();
        value.add( "test" );

        Guard guard = new Guard();
        Violations violations;

        violations = guard.of( "list" )
                          .value( value )
                          .constraint( new Required() )
                          .validate();

        LOGGER.info( "Result {}", violations.getList( "list" ) );
        Assertions.assertTrue( violations.isValid() );
        Assertions.assertTrue( violations.isValid( "list" ) );

    }
}
