package com.dp.abstract_factory;

import com.dp.factory.MyInterface;

public class FactoryTest {
	public static void main() {
        Provider provider = new MyFactoryOne();
        MyInterface myi = provider.produce();
        myi.print();
	}
}
