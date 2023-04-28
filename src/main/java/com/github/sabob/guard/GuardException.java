package com.github.sabob.guard;

import com.github.sabob.guard.violation.Violation;
import com.github.sabob.guard.violation.Violations;

public class GuardException extends RuntimeException {

    protected Violations violations = new Violations();

    public GuardException() {
    }

    public GuardException(Violations violations) {
        this.violations = violations;
    }

    public GuardException(String message) {
        super(message);
    }

    public GuardException(String message, Violations violations) {
        super(message);
        this.violations = violations;
    }

    public GuardException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuardException(String message, Throwable cause, Violations violations) {
        super(message, cause);
        this.violations = violations;
    }

    public Violations getViolations() {
        return violations;
    }

    public void setViolations(Violations violations) {
        this.violations = violations;
    }

    public void addViolation(Violation violation) {
        getViolations().add(violation);
    }
}
