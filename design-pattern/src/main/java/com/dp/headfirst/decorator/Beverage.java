package com.dp.headfirst.decorator;

/**
 * Beverage 饮料
 * @author xuanyandong
 *
 */
public abstract class Beverage {
	
	public String description = "Unknown Beverage";
	
	public static int TALL = 0; // 小杯
	public static int GRANDE = 1; // 中杯
	public static int VENTI = 2; // 大杯
	public int size;
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public String getDescription() {
		return description;
	}
	
	public abstract double cost();
}
