package com.basic.lambda;

import java.util.Arrays;

public class LambdaDemo {
    
    public void testLambda() {
        String[] planets = new String[] {"Mecury", "Venus", 
            "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted in dictionary order:");
        Arrays.sort(planets);
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted by length:");
        Arrays.sort(planets, 
            (first, second) -> first.length() - second.length()
            );
        System.out.println(Arrays.toString(planets));

    }

}