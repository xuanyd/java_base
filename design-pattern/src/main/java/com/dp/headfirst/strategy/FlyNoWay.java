package com.dp.headfirst.strategy;

public class FlyNoWay implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("I Can't Fly!!");
    }

}