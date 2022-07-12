package com.github.sabob.guard.utils;

import com.github.sabob.guard.constraints.FalseOnly;
import com.github.sabob.guard.constraints.NullOnly;
import com.github.sabob.guard.constraints.TrueOnly;

public class mustBe {

    public static NullOnly Null() {
        return new NullOnly();
    }

    public static TrueOnly True() {
        return new TrueOnly();
    }

    public static FalseOnly False() {
        return new FalseOnly();
    }
}
