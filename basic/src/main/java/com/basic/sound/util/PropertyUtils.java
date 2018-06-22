package com.basic.sound.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
	
	private static Properties properties;
	
	static {
		try {
			String filePath =System.getProperty("user.dir") +
					"\\resources\\sound.properties";
			properties = new Properties();
			properties.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			System.out.println("Exception -----资源文件加载失败，文件找不到----");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception -----资源文件加载失败，IO异常----");
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		if(null == properties) {
			return "";
		}
		return properties.getProperty(key);
	}
	
	public static void main(String[] args) {
		System.out.println(get("xAppid"));
	}
}
