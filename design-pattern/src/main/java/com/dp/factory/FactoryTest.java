package com.dp.factory;

public class FactoryTest {
	public static void main(String[] args){
        MyFactory factory = new MyFactory();  
        MyInterface myi = factory.produce("One");  
        myi.print();
    }
}
