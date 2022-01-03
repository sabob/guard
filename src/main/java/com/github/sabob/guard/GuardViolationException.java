package com.github.sabob.guard;

import com.github.sabob.guard.violation.Violations;

public class GuardViolationException extends GuardException {

    public GuardViolationException() {
    }

    public GuardViolationException( Violations violations ) {
        this.violations = violations;
    }

    public GuardViolationException( String message ) {
        super( message );
    }

    public GuardViolationException( String message, Violations violations ) {
        super( message );
        this.violations = violations;
    }

    public GuardViolationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public GuardViolationException( String message, Throwable cause, Violations violations ) {
        super( message, cause );
        this.violations = violations;
    }
}
