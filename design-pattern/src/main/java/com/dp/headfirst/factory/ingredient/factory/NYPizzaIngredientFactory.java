package com.dp.headfirst.factory.ingredient.factory;

import com.dp.headfirst.factory.ingredient.ingredients.Cheese;
import com.dp.headfirst.factory.ingredient.ingredients.Clams;
import com.dp.headfirst.factory.ingredient.ingredients.Dough;
import com.dp.headfirst.factory.ingredient.ingredients.Pepperoni;
import com.dp.headfirst.factory.ingredient.ingredients.Sauce;
import com.dp.headfirst.factory.ingredient.ingredients.Veggies;

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {

	@Override
	public Dough createDough() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sauce createSauce() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cheese createCheese() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Veggies[] createVeggies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pepperoni createPepperoni() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clams createClam() {
		// TODO Auto-generated method stub
		return null;
	}

}
