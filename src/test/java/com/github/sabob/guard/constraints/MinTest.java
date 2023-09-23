package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Guard;
import com.github.sabob.guard.domain.ObjectBean;
import com.github.sabob.guard.domain.PrimBean;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // Negative Test: Set value < Min
    @Test
    public void setValueLessThanMinTest() {

        Guard guard = new Guard();
        PrimBean bean = new PrimBean();
        bean.setInt(20);

        Violations violations = guard.of("test.1")
                .value(bean.getInt())
                .constraint(new Min(21))
                .validate();

        System.out.println(guard.getViolations().getList());

        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertTrue(violations.getList("test.1").size() == 1);

    }

    // Positive Test: Set value == Min
    @Test
    public void setValueEqualsToMin() {

        Guard guard = new Guard();
        PrimBean bean = new PrimBean();
        bean.setInt(21);

        Violations violations = guard.of("test.2")
                .value(bean.getInt())
                .constraint(new Min(21))
                .validate();

        Assertions.assertTrue(violations.getList("test.2").isEmpty());
    }

    // Positive Test: Set value > Min
    @Test
    public void setValueGreaterThanMin() {

        Guard guard = new Guard();
        PrimBean bean = new PrimBean();
        bean.setInt(22);

        Violations violations = guard.of("test.3")
                .value(bean.getInt())
                .constraint(new Min(21))
                .validate();

        Assertions.assertTrue(violations.getList("test.3").isEmpty());
        Assertions.assertTrue(violations.isValid());

    }

    @Test
    public void nullTest() {

        Guard guard = new Guard();

        ObjectBean bean = new ObjectBean();

        bean.setString(null);

        Violations violations = guard.of("test.4")
                .value(bean.getString())
                .constraint(new Min(20))
                .validate();

        Assertions.assertTrue(violations.isValid());
        Assertions.assertTrue(violations.getList("test.4").isEmpty());
    }

}
