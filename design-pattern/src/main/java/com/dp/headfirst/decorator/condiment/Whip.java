package com.dp.headfirst.decorator.condiment;

import com.dp.headfirst.decorator.Beverage;
import com.dp.headfirst.decorator.CondimentDecorator;

/**
 * 奶泡调味品
 * @author xuanyandong
 *
 */
public class Whip extends CondimentDecorator{
	
	Beverage beverage;

	public Whip(Beverage beverage) {
		this.beverage = beverage;
	}
		
	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Whip";
	}

	@Override
	public double cost() {
		return .10 + beverage.cost();
	}

}
