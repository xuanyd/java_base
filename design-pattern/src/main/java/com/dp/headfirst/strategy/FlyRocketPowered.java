package com.dp.headfirst.strategy;

public class FlyRocketPowered implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("I'm Flying with a rocket!");
    }

}