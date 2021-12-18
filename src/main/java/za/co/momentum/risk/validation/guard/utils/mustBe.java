package za.co.momentum.risk.validation.guard.utils;

import za.co.momentum.risk.validation.guard.constraints.*;

public class mustBe {

    public static NullOnly Null() {
        return new NullOnly();
    }

    public static NotNull NotNull() {
        return new NotNull();
    }

    public static Required Required() {
        return new Required();
    }

    public static NotEmpty NotEmpty() {
        return new NotEmpty();
    }

    public static NotBlank NotBlank() {
        return new NotBlank();
    }
}
