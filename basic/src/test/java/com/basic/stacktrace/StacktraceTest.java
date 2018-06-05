package com.basic.stacktrace;

import org.junit.Test;

public class StacktraceTest {
    
    @Test
    public void factorialTest() {
        StackTrace trace = new StackTrace();
        trace.factorial(3);
    }
}