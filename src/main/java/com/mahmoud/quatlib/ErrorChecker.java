package com.mahmoud.quatlib;

public class ErrorChecker {
    private static final String TAG = ErrorChecker.class.getSimpleName();

    // Functions -----------------------------------------------------------------------------------
    static void assertCondition(boolean cond, String errMsg) {
        if(!cond) {
            throw new AssertionError(errMsg);
        }
    }
}
