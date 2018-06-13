package com.dp.headfirst.decorator;

import org.junit.Test;

import com.dp.headfirst.decorator.coffee.DarkRoast;
import com.dp.headfirst.decorator.coffee.Espresso;
import com.dp.headfirst.decorator.coffee.HouseBlend;
import com.dp.headfirst.decorator.condiment.Soy;
import com.dp.headfirst.decorator.condiment.Whip;

public class StarbuzzCoffee {
	
	@Test
	public void testEspresso() {
		Beverage beverage = new Espresso();
		System.out.println(beverage.getDescription() + " $:" + beverage.cost());
	}
	
	@Test
	public void testDarkRoast() {
		Beverage beverage = new DarkRoast();
		beverage = new Mocha(beverage);
		beverage = new Mocha(beverage);
		beverage = new Whip(beverage);
		System.out.println(beverage.getDescription() + " $:" + beverage.cost());
	}
	
	@Test
	public void testHouseBlend() {
		Beverage beverage = new HouseBlend();
		beverage.setSize(Beverage.TALL);
		beverage = new Soy(beverage);
		beverage = new Mocha(beverage);
		beverage = new Whip(beverage);
		System.out.println(beverage.getDescription() + " $:" + beverage.cost());
	}
}
