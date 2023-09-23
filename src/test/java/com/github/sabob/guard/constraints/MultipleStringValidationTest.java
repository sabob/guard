package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Guard;
import com.github.sabob.guard.GuardViolationException;
import com.github.sabob.guard.constraints.parseable.ParseableTo;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultipleStringValidationTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void multipleStringValidationWithNonIntegerValues() {

        try {
            Bean bean = new Bean();
            bean.age = "age";
            bean.salary = "salary";

            Guard guard = new Guard();
            guard.path("Bean.");

            guard.of("age")
                    .value(bean.age)
                    .constraint(new NotNull())
                    .constraint(new ParseableTo(Integer.class))
                    .throwIfContextInvalid("age")
                    .value(new Integer(bean.age)) // convert value to integer so we can do integer specific validation
                    .constraint(new Max(50))
                    .of("salary")
                    .value(bean.salary)
                    .constraint(new NotNull())
                    .constraint(new ParseableTo(Integer.class))
                    .throwIfContextInvalid()
                    .throwIfInvalid()
                    .value(new Integer(bean.salary)) // convert value to integer so we can do integer specific validation
                    .constraint(new Min(1000))
                    .validate();

            Assertions.fail("age is not a valid integer");


        } catch (GuardViolationException ex) {
            System.out.println(ex.getViolations().getList());
            Assertions.assertTrue(ex.getViolations().getList().size() == 1);
        }
    }

    @Test
    public void multipleStringValidationWithIntegerValues() {

        try {
            Bean bean = new Bean();
            bean.age = "60";
            bean.salary = "100";

            Guard guard = new Guard();
            guard.path("Bean.");

            guard.of("age")
                    .value(bean.age)
                    .constraint(new NotNull())
                    .constraint(new ParseableTo(Integer.class))
                    .throwIfContextInvalid("age")
                    .value(new Integer(bean.age)) // convert value to integer so we can do integer specific validation
                    .constraint(new Max(50));

            Violations violations = guard.of("salary")
                    .value(bean.salary)
                    .constraint(new NotNull())
                    .constraint(new ParseableTo(Integer.class))
                    .throwIfContextInvalid("salary")
                    .value(new Integer(bean.salary)) // convert value to integer so we can do integer specific validation
                    .constraint(new Min(1000))
                    .validate();

            System.out.println(guard.getViolations().getList());

            Assertions.assertTrue(violations.isInvalid());
            Assertions.assertTrue(violations.getList().size() == 2);


        } catch (GuardViolationException ex) {
            System.out.println(ex.getViolations().getList());
        }
    }

    class Bean {
        String age;
        String salary;
    }
}
