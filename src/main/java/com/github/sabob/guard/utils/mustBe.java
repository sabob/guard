package com.github.sabob.guard.utils;

import com.github.sabob.guard.constraints.*;

public class mustBe {

    public static NullOnly Null() {
        return new NullOnly();
    }

    public static NotNull NotNull() {
        return new NotNull();
    }

    public static NotEmpty NotEmpty() {
        return new NotEmpty();
    }

    public static NotBlank NotBlank() {
        return new NotBlank();
    }
}
