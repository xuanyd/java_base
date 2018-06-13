package com.dp.headfirst.decorator.coffee;

import com.dp.headfirst.decorator.Beverage;

/**
 * 综合咖啡
 * @author xuanyandong
 *
 */
public class HouseBlend extends Beverage{

	public HouseBlend() {
		description = "House Blend Coffee";
	}
	
	@Override
	public double cost() {
		return .89;
	}

}
