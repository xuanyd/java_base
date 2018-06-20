package com.basic.sound;


/**
 * 监听数据，判断是否有指令汇入
 * @author xuanyandong
 *
 */
public class CheckSound implements Runnable {

	public CheckSound() {
		
	}
	
	@Override
	public void run() {
		while(true) {
			System.out.println("检测是否有语音录入");
		}
	}
	
}
