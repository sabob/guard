package com.github.sabob.guard;

public interface CustomConstraint {

    void apply( GuardContext ctx, GuardContext guardContext );
}
