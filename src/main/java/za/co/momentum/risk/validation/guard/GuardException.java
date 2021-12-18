package za.co.momentum.risk.validation.guard;

import za.co.momentum.risk.validation.guard.violation.Violation;
import za.co.momentum.risk.validation.guard.violation.Violations;

public class GuardException extends RuntimeException {

    protected Violations violations;

    public GuardException() {
    }

    public GuardException( Violations violations ) {
        this.violations = violations;
    }

    public GuardException( String message ) {
        super( message );
    }

    public GuardException( String message, Violations violations ) {
        super( message );
        this.violations = violations;
    }

    public GuardException( String message, Throwable cause ) {
        super( message, cause );
    }

    public GuardException( String message, Throwable cause, Violations violations ) {
        super( message, cause );
        this.violations = violations;
    }

    public Violations getViolations() {
        return violations;
    }

    public void setViolations( Violations violations ) {
        this.violations = violations;
    }

    public void addViolation( Violation violation ) {
        getViolations().add( violation );
    }
}
