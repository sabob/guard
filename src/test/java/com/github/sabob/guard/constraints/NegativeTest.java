package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.PrimBean;
import com.github.sabob.guard.violation.Violations;

public class NegativeTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Test
    public void valueIsPositive() {

        PrimBean bean = new PrimBean();
        Guard guard = new Guard();
        Violations violations;

        bean.setDouble(0.12d);

        violations = guard.of("test.1")
                .value(bean.getDouble())
                .constraint(new Negative())
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertTrue(!violations.getList("test.1").isEmpty());

    }

    // Positive Test: Set negative value
    @Test
    public void valueIsNegative() {
        PrimBean bean = new PrimBean();
        Guard guard = new Guard();
        Violations violations;

        bean.setDouble(-0.00001d);

        violations = guard.of("test.2")
                .value(bean.getDouble())
                .constraint(new Negative())
                .validate();

        Assertions.assertTrue(violations.isValid());
        Assertions.assertTrue(violations.getList("test.2").isEmpty());
    }

}
