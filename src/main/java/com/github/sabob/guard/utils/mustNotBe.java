package com.github.sabob.guard.utils;

import com.github.sabob.guard.constraints.NotBlank;
import com.github.sabob.guard.constraints.NotEmpty;
import com.github.sabob.guard.constraints.NotNull;

public class mustNotBe {

    public static NotNull Null() {
        return new NotNull();
    }

    public static NotEmpty Empty() {
        return new NotEmpty();
    }

    public static NotBlank Blank() {
        return new NotBlank();
    }
}
