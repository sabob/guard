package za.co.momentum.risk.validation.guard;

public interface CustomConstraint {

    void apply( GuardContext ctx, GuardContext guardContext );
}
