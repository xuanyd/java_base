package com.dp.headfirst.decorator.coffee;

import com.dp.headfirst.decorator.Beverage;

/**
 * 深焙咖啡
 * @author xuanyandong
 *
 */
public class DarkRoast extends Beverage{
	
	public DarkRoast() {
		description = "Dark Roast Coffee";
	}

	@Override
	public double cost() {
		return .99;
	}

}
