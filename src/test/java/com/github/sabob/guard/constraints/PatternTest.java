package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.ObjectBean;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PatternTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Test
    public void patternMatchesTest() {

        Guard guard = new Guard();

        Violations violations = guard.of("number")
                .value("1a")
                .constraint(new Pattern("[a-zA-Z0-9]+")) // alphanumeric characters only
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isValid());
    }

    @Test
    public void patternDoesNotMatchTest() {

        Violations violations;
        Guard guard = new Guard();

        violations = guard.of("number")
                .value("1,a")
                .constraint(new Pattern("[a-zA-Z0-9]+")) // alphanumeric characters only
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isInvalid());
    }

}
