package com.github.sabob.guard.constraints;

import com.github.sabob.guard.Constraint;
import com.github.sabob.guard.Guard;
import com.github.sabob.guard.GuardException;
import com.github.sabob.guard.constraints.misc.AtLeast;
import com.github.sabob.guard.domain.Organisation;
import com.github.sabob.guard.domain.Person;
import com.github.sabob.guard.violation.Violations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AtLeastTest {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void atLeastZeroNotEmptySuccess() {

        Person person = new Person();

        person.setLastname("Foo");

        Guard guard = new Guard("lastname");

        AtLeast atLeast = new AtLeast(0, new NotEmpty(), "At least 0 values are required!");
        guard.constraint(atLeast);
        Violations violations = guard.validate();

        Assertions.assertTrue(violations.isValid());
    }

    @Test
    public void atLeastOneNotEmptyFail() {

        Guard guard = new Guard("lastname");
        String errorMessage = "At least 2 values are required!";

        AtLeast atLeast = new AtLeast(2, new NotEmpty(), errorMessage);
        atLeast.values("one");
        guard.constraint(atLeast);

        Violations violations = guard.validate();

        Assertions.assertTrue(violations.isInvalid());
        Assertions.assertTrue(violations.getList().size() == 1);
        Assertions.assertTrue(violations.getList().get(0).getMessage().equals(errorMessage));
        System.out.println(violations.getList("lastname"));
    }

    @Test
    public void atLeastOneContactDetailIsRequiredFail() {

        String phoneNumber = null;
        String email = null;
        Constraint contactDetailIsRequired = new Required();
        String errorMessage = "Either a phoneNumber or email is required";

        AtLeast atLeastOneContact = new AtLeast(1, contactDetailIsRequired, errorMessage);
        atLeastOneContact.values(phoneNumber, email);

        GuardException thrown = Assertions.assertThrows(GuardException.class, () -> {
            new Guard()
                    .of("contactDetail")
                    .constraint(atLeastOneContact)
                    .throwIfInvalid();
        });

        Assertions.assertTrue(thrown.getViolations().getList().size() == 1);
        Assertions.assertTrue(thrown.getViolations().getList().get(0).getMessage().equals(errorMessage));
        System.out.println(thrown.getViolations().getList("contactDetail"));
    }

    @Test
    public void atLeastSomethingIsRequired() {

        Organisation organisation = null;
        Person person = null;
        String phone = "";
        Map map = new HashMap();
        Constraint somethingIsRequired = new Required();

        AtLeast atLeastOneContact = new AtLeast(1, somethingIsRequired, "Gimme at least something!");
        atLeastOneContact.values(person, organisation, phone, map);

        GuardException thrown = Assertions.assertThrows(GuardException.class, () -> {
            new Guard()
                    .of("entity")
                    .constraint(atLeastOneContact)
                    .throwIfInvalid();
        });

        Assertions.assertTrue(thrown.getViolations().getList().size() == 1);
        System.out.println(thrown.getViolations().getList("entity"));
    }

    @Test
    public void testEmptyErrorMessage() {

        Constraint emailOrPhoneIsRequired = new Required();

        AtLeast atLeastOnePerson = new AtLeast(1, emailOrPhoneIsRequired, "");

        Violations violations = new Guard("person")
                .constraint(atLeastOnePerson)
                .validate();

        Assertions.assertTrue(violations.getList().size() == 1);
        Assertions.assertTrue(violations.getList().get(0).getMessage().equals(""));
        System.out.println(violations.getList("person"));

    }
}
