package com.dp.headfirst.factory.pizza;

public class ChicagoStylePizza extends Pizza {
	
	public ChicagoStylePizza() {
		name = "Chicago Style Sauce and Cheese Pizza";
		dough = "Extra Thick Crust Dough";
		sauce = "Plum Tomato Sauce";
		toppings.add("Shredded Mozzarella Cheese");
	}
	
	@Override
	public void cut() {
		System.out.println("Cutting the pizza into square slices");
	}
	
}
