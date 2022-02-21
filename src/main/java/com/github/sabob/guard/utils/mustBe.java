package com.github.sabob.guard.utils;

import com.github.sabob.guard.constraints.NullOnly;

public class mustBe {

    public static NullOnly Null() {
        return new NullOnly();
    }
}
