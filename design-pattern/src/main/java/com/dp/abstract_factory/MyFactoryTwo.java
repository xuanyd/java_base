package com.dp.abstract_factory;

import com.dp.factory.MyClassOne;
import com.dp.factory.MyClassTwo;
import com.dp.factory.MyInterface;

public class MyFactoryTwo implements Provider {

	@Override
	public MyInterface produce() {
		return new MyClassTwo();
	}

}
