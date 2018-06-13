package com.dp.headfirst.decorator.coffee;

import com.dp.headfirst.decorator.Beverage;

/**
 * 低浓咖啡
 * @author xuanyandong
 *
 */
public class Decat extends Beverage{
	
	public Decat() {
		description = "Decat Coffee";
	}

	@Override
	public double cost() {
		return 1.99;
	}
	

}
