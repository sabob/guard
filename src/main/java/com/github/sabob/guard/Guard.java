package com.github.sabob.guard;

import com.github.sabob.guard.violation.Violation;
import com.github.sabob.guard.violation.Violations;

import java.util.List;
import java.util.Optional;

public class Guard {

    protected boolean failFast = false;

    protected GuardContext context = null;

    protected Violations violations = new Violations();

    public Guard() {
        context = new GuardContext();
    }

    public Guard(String name) {
        of(name);
    }

    public GuardContext getContext() {
        return context();
    }

    public Guard of(String name) {

        GuardContext newContext = new GuardContext(name);

        if (context != null) {
            newContext.path(context.getPath());
            violations.merge(context.getViolations());
        }

        context = newContext;

        return this;
    }

    public Guard constraint(Constraint constraint) {
        GuardContext clone = context().clone();

        apply(constraint, clone);
        return this;
    }

    public Guard constraint(Constraint constraint, GuardContext guardContext) {
        GuardContext clone = context().merge(guardContext);

        apply(constraint, clone);
        return this;
    }

    public Guard constraint(Constraint constraint, String message) {
        GuardContext clone = context().clone();
        clone.setMessage(message);

        apply(constraint, clone);
        return this;
    }

    public Guard constraint(Constraint constraint, Object value, String message) {
        GuardContext clone = context().clone();
        clone.setValue(value);
        clone.setMessage(message);

        apply(constraint, clone);
        return this;

    }

    public Guard throwIfInvalid() throws GuardViolationException {
        if (invalid()) {
            throw new GuardViolationException(getViolations());
        }
        return this;
    }

    public Guard throwIfContextInvalid() throws GuardViolationException {
        GuardContext ctx = getContext();
        ctx.throwIfInvalid();
        return this;
    }

    public Guard throwIfContextInvalid(String name) throws GuardViolationException {
        if (violations.isInvalid(name)) {
            throw new GuardViolationException(getViolations().copy(name));
        }
        return this;
    }

    protected Guard failFastIfInvalid() throws GuardViolationException {
        if (failFast()) {
            throwIfInvalid();
        }
        return this;
    }

    public Violations validate() {
        violations.merge(context().getViolations());
        return violations;
    }

    public Optional<Violation> getLatestViolation() {
        return getContext().getLatestViolation();
    }

    public Optional<Violation> getLatestViolation(String name) {
        return getContext().getLatestViolation(name);
    }

    public Violations getViolations() {
        return violations;
    }

    public void setViolations(Violations violations) {
        this.violations = violations;
    }

    public boolean addViolation(Violation violation) {
        return getViolations().add(violation);
    }

    public boolean valid() {
        return violations.isValid();
    }

    public boolean invalid() {
        return !valid();
    }

    public boolean failFast() {
        return failFast;
    }

    public Guard failFast(boolean failFast) {
        this.failFast = failFast;
        return this;
    }

    public String name() {
        return context().getName();
    }

    public Guard name(String name) {
        context().setName(name);
        return this;
    }

    public String path() {
        return context().getPath();
    }

    public Guard path(String path) {
        context().setPath(path);
        return this;
    }

    public String code() {
        return context().getCode();
    }

    public Guard code(String code) {
        context().setCode(code);
        return this;
    }

    public Object value() {
        return context().getValue();
    }

    public Guard value(Object value) {
        context().setValue(value);
        return this;
    }

    public String message() {
        return context().getMessage();
    }

    public Guard message(String message) {
        context().setMessage(message);
        return this;
    }

    public GuardContext context() {
        if (context == null) {
            throw new IllegalStateException("No context available. Usage: new Guard(\"some_name\") or myGuard.of(\"some_name\") to create a context");
        }
        return context;
    }

    protected Guard apply(Constraint constraint, GuardContext ctx) {

        constraint.apply(ctx);
        Violations violations = getViolations();
        violations.merge(ctx.getViolations());
        context().violations.merge(ctx.getViolations());
        failFastIfInvalid();
        return this;
    }
}
