package za.co.momentum.risk.validation.guard;

public interface Constraint {

    void apply( GuardContext guardContext );

    boolean isValid( Object value );

    default boolean isInvalid( Object value ) {
        return !isValid( value );
    }

}
