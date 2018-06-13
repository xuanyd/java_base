package com.dp.headfirst.decorator.coffee;

import com.dp.headfirst.decorator.Beverage;

/**
 * Espresso 浓咖啡
 * @author xuanyandong
 *
 */
public class Espresso  extends Beverage{

	public Espresso() {
		description = "Espresso";
	}
	
	@Override
	public double cost() {
		return 1.99;
	}

}
