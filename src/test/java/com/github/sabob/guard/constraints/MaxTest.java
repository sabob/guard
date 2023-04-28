package com.github.sabob.guard.constraints;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.ObjectBean;
import com.github.sabob.guard.domain.PrimBean;
import com.github.sabob.guard.violation.Violations;

public class MaxTest {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // Negative Test: Set value  > Max
    @Test
    public void valueSetGreaterThanMaxTest() {

        Violations violations;
        Guard guard = new Guard();

        PrimBean bean = new PrimBean();
        bean.setInt(21);

        violations = guard.of("test.1")
                .value(bean.getInt())
                .constraint(new Max(20))
                .validate();

        Assertions.assertFalse(violations.isValid());
        Assertions.assertTrue(violations.isInvalid("test.1"));

        System.out.println(guard.getViolations().getList());

    }

    // Positive Test: Set value ==  Max.
    @Test
    public void valueSetEqualsToMaxTest() {

        Violations violations;
        Guard guard = new Guard();

        PrimBean bean = new PrimBean();

        bean.setInt(20);

        violations = guard.of("test.2")
                .value(bean.getInt())
                .constraint(new Max(20))
                .validate();

        Assertions.assertTrue(violations.isValid());
        Assertions.assertTrue(violations.isValid("test.2"));
    }

    // Positive Test: Set value <  Max.
    @Test
    public void valueSetSmallerThanMaxTest() {

        Violations violations;
        Guard guard = new Guard();

        PrimBean bean = new PrimBean();

        bean.setInt(19);

        violations = guard.of("test.3")
                .value(bean.getInt())
                .constraint(new Max(20))
                .validate();

        Assertions.assertTrue(violations.isValid());
        Assertions.assertFalse(violations.getList("test.3").size() > 0);

    }

    @Test
    public void nullTest() {

        Violations violations;
        Guard guard = new Guard();

        ObjectBean bean = new ObjectBean();

        bean.setString(null);

        violations = guard.of("test.4")
                .value(bean.getString())
                .constraint(new Max(20))
                .validate();

        Assertions.assertTrue(violations.isValid());
        Assertions.assertTrue(violations.getList("test.4").isEmpty());
    }

}
