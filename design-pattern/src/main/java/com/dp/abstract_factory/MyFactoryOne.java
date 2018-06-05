package com.dp.abstract_factory;

import com.dp.factory.MyClassOne;
import com.dp.factory.MyInterface;

public class MyFactoryOne implements Provider {

	@Override
	public MyInterface produce() {
		return new MyClassOne();
	}

}
