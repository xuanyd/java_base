package com.basic.exception;

import org.junit.Test;

public class WithResourceTest {
    @Test
    public void testWithResource() {
        WithResource ex = new WithResource();
        ex.throwWithResource();
    }
}