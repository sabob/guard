package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Guard;
import com.github.sabob.guard.constraints.parseable.ParseableTo;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ParseableToTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void nullTest() {

        Violations violations;
        Guard guard = new Guard();

        violations = guard.of("test")
                .value(null)
                .constraint(new ParseableTo(Integer.class))
                .validate();

        Assertions.assertTrue(violations.isValid());
        Assertions.assertTrue(violations.getList("test").isEmpty());
    }

    @Test
    public void parseableToIntegerSucceedTest() {

        Guard guard = new Guard();

        Violations violations = guard.of("test")
                .value("123")
                .constraint(new ParseableTo(Integer.class))
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isValid());
        Assertions.assertTrue(violations.getList("test").size() == 0);
    }

    @Test
    public void parseableToIntegerFailTest() {

        Guard guard = new Guard();

        Violations violations = guard.of("test")
                .value("abc")
                .constraint(new ParseableTo(Integer.class))
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertTrue(violations.getList("test").size() == 1);
    }

        @Test
    public void customParseableEnumSuccessTest() {

        Guard guard = new Guard();

        String value = Weekday.MONDAY.name();

        Violations violations = guard.of("day")
                .value(value)
                .constraint(new ParseableTo(Weekday.class)
                        .parseable((obj) -> Weekday.MONDAY.name().equals(value)))
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isValid());
        Assertions.assertTrue(violations.getList("day").size() == 0);
    }

    @Test
    public void customParseableEnumFailTest() {

        Guard guard = new Guard();

        String value = "tuesday";

        Violations violations = guard.of("day")
                .value(value)
                .constraint(new ParseableTo(Weekday.class)
                        .parseable((obj) -> Weekday.MONDAY.equals(value)))
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertTrue(violations.getList("day").size() == 1);
    }

    @Test
    public void validDateTest() {

        Guard guard = new Guard();

        String value = "1-11-2022";

        Violations violations = guard.of("date")
                .value(value)
                .constraint(new ParseableTo(Date.class, "dd-MM-yyyy"))
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isValid());
        Assertions.assertTrue(violations.getList("date").size() == 0);

    }

    @Test
    public void invalidDateTest() {

        Guard guard = new Guard();

        String value = "1-1-2022";

        Violations violations = guard.of("date")
                .value(value)
                .constraint(new ParseableTo(Date.class, "d/m/yyyy"))
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertTrue(violations.getList("date").size() == 1);
    }

    @Test
    public void integerMinTest() {

        Guard guard = new Guard();

        int max = 1;
        String value = "2";

        Violations violations = guard.of("int")
                .value(value)
                .constraint(new NotNull())
                .constraint(new ParseableTo(Integer.class))
                .throwIfInvalid()
                .value(Integer.parseInt(value))
                .constraint(new Max(max))
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertTrue(violations.getList("int").size() == 1);
    }

    enum Weekday {
        MONDAY
    }
}
