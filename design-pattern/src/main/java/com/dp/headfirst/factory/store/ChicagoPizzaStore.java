package com.dp.headfirst.factory.store;

import com.dp.headfirst.factory.pizza.ChicagoStylePizza;
import com.dp.headfirst.factory.pizza.NYStyleCheesePizza;
import com.dp.headfirst.factory.pizza.Pizza;

public class ChicagoPizzaStore extends PizzaStore{

	@Override
	protected Pizza createPizza(String type) {
		return new ChicagoStylePizza();
	}

}
