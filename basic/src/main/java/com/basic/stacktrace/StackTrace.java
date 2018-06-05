package com.basic.stacktrace;

public class StackTrace {
    
    public int factorial(int n) {
        System.out.println("factorial(" + n + "):");
        Throwable t = new Throwable();
        StackTraceElement[] frames = t.getStackTrace();

        for (StackTraceElement f : frames)
            System.out.println(f);
        int r;
        if(n <=1)
            r = 1;
        else
            r = n * factorial(n-1);
        System.out.println("return " + r);
        return r;
    }

}