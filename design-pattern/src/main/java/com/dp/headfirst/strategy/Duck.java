package com.dp.headfirst.strategy;

public class Duck {

    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;
    
    public void performQuack() {
        quackBehavior.quack();
    }

    public void performFly() {
        flyBehavior.fly();
    }

    public void swim() {
        System.out.println("All Ducks float, even decoys!");
    }

    public void display() {
        
    }

    public void setFlyBehavior(FlyBehavior fb) {
        this.flyBehavior = fb;
    }

    public void setQuackBehavior(QuackBehavior qb) {
        this.quackBehavior = qb;
    }

}