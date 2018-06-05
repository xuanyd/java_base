package com.dp.headfirst.strategy;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DuckTest {

    @Test
    public void testMullardDuck() {
        Duck mullard = new MullardDuck();
        mullard.performQuack();
        mullard.performFly();
    }

    @Test
    public void testModelDuck() {
        ModelDuck duck = new ModelDuck();
        duck.performFly();
        duck.setFlyBehavior(new FlyRocketPowered());
        duck.performFly();
    }
}