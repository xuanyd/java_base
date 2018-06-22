package com.dp.headfirst.factory.store;

import com.dp.headfirst.factory.pizza.NYStyleCheesePizza;
import com.dp.headfirst.factory.pizza.Pizza;

public class NYPizzaStore extends PizzaStore{

	@Override
	protected Pizza createPizza(String type) {
		return new NYStyleCheesePizza();
	}

}
