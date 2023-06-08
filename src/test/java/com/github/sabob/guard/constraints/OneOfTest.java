package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Guard;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneOfTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Test
    public void oneOfFailsTest() {

        Guard guard = new Guard();

        Violations violations = guard.of("number")
                .value("four")
                .constraint(new OneOf("one","two", "three"))
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isInvalid());
    }

    @Test
    public void oneOfSucceedsTest() {

        Guard guard = new Guard();

        Violations violations = guard.of("number")
                .value("two")
                .constraint(new OneOf("one","two", "three"))
                .validate();

        Assertions.assertTrue(violations.isValid());
    }

}
