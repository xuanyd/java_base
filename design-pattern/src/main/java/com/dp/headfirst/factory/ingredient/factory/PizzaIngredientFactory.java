package com.dp.headfirst.factory.ingredient.factory;

import com.dp.headfirst.factory.ingredient.ingredients.Cheese;
import com.dp.headfirst.factory.ingredient.ingredients.Clams;
import com.dp.headfirst.factory.ingredient.ingredients.Dough;
import com.dp.headfirst.factory.ingredient.ingredients.Pepperoni;
import com.dp.headfirst.factory.ingredient.ingredients.Sauce;
import com.dp.headfirst.factory.ingredient.ingredients.Veggies;

public interface PizzaIngredientFactory {
	
	public Dough createDough();
	public Sauce createSauce();
	public Cheese createCheese();
	public Veggies[] createVeggies();
	public Pepperoni createPepperoni();
	public Clams createClam();
	
}
