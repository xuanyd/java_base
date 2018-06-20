package com.dp.headfirst.factory.store;

import com.dp.headfirst.factory.pizza.Pizza;

public abstract class PizzaStore {
	
	public Pizza orderPizza(String type) {
		Pizza pizza;
		pizza = createPizza(type);
		return pizza;
	}
	protected abstract Pizza createPizza(String type);
}
