package com.dp.headfirst.strategy;

public class MiniDuckSimulator {

    public static void main(String[] args) {
        Duck mallard = new MullardDuck();
        mallard.performQuack();
        mallard.performFly();
    }

}