package com.dp.headfirst.strategy;

public class MullardDuck extends Duck {
    public MullardDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }
}