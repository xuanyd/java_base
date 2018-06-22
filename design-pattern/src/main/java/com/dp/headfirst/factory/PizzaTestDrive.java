package com.dp.headfirst.factory;

import com.dp.headfirst.factory.pizza.Pizza;
import com.dp.headfirst.factory.store.ChicagoPizzaStore;
import com.dp.headfirst.factory.store.NYPizzaStore;
import com.dp.headfirst.factory.store.PizzaStore;

public class PizzaTestDrive {
	
	public static void main(String[] args) {
		PizzaStore nyStyleStore = new NYPizzaStore();
		PizzaStore chicagoStore = new ChicagoPizzaStore();
		
		Pizza pizza = nyStyleStore.orderPizza("cheese");
		System.out.println("Ethan ordered a " + pizza.getName() + "\n");
		
		pizza = chicagoStore.orderPizza("cheese");
		System.out.println("Jone ordered a " + pizza.getName() + "\n");
	}
	
}
