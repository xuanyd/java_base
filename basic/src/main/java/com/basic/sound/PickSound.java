package com.basic.sound;

import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import com.basic.sound.util.SoundUtil;
import com.basic.sound.util.XFApi;

/**
 * 语音拾取
 * 
 * @author xuanyandong
 *
 */
public class PickSound {

	AudioFormat audioFormat;

	boolean continueRecorde;

	int number;
	byte bufferAll[];
	int bufferAllIndex;

	int vRate, hRate;

	int nByte = 0;

	boolean ifSoundContinue;
	long soundBeginTime;
	int soundBeginIndex;

	final int bufSize = 256;
	byte[] buffer = new byte[bufSize];

	TargetDataLine targetDataLine = null;

	public PickSound() {
		number = 600;
		bufferAll = new byte[130 * 1024 * 1024];
		bufferAllIndex = 0;
		vRate = 120;
		hRate = 20;// 1470
		continueRecorde = false;
		ifSoundContinue = false;
		soundBeginTime = 0;
		soundBeginIndex = 0;
		continueRecorde = false;
		audioFormat = SoundUtil.getAudioFormat();
		DataLine.Info info;
		info = new DataLine.Info(TargetDataLine.class, audioFormat);

		try {
			targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		info = new DataLine.Info(SourceDataLine.class, audioFormat);
	}

	public void startPick() {
		new Thread() {
			public void run() {
				while (true) {
					/*if (!continueRecorde) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException ie) {
						}
						continue;
					}*/
					synchronized (bufferAll) {
						if (600 * hRate * 2 < bufferAllIndex) {
							int beginIndex = bufferAllIndex - 600 * hRate * 2;
							for (int i = 0; i < 600; i++, beginIndex += 2 * hRate) {
								int hBit = bufferAll[beginIndex];
								int lBit = bufferAll[beginIndex + 1];
								int abs = Math.abs(hBit) + Math.abs(lBit);
								if (abs > 80) {
									if (!ifSoundContinue) {
										ifSoundContinue = true;
										soundBeginTime = System.currentTimeMillis();
										soundBeginIndex = beginIndex;
										//System.out.println("soundBeginIndex: " + soundBeginIndex);
									}
								} else {
									if (ifSoundContinue) {
										if (System.currentTimeMillis() - soundBeginTime > 800) {
											ifSoundContinue = false;
											soundBeginTime = System.currentTimeMillis();
											int longth = beginIndex - soundBeginIndex;
											System.out.println("区间：" + longth);
											if (longth < 0)
												continue;
											byte[] bt = new byte[beginIndex - soundBeginIndex];
											System.arraycopy(bufferAll, beginIndex, bt, 0,
													beginIndex - soundBeginIndex);
											String filePath = SoundUtil.saveSound(bt);
											try {
												String callRes = XFApi.audioCall(filePath);
												System.out.println(callRes);
											} catch (Throwable e) {
												e.printStackTrace();
											}
										}
									}
								}
							}
							number = 600;
						} else {
							System.out.println("-----------");
							int beginIndex = 0;
							number = bufferAllIndex / hRate / 2;
							for (int i = 0; i < number; i++, beginIndex += 2 * hRate) {
								int hBit = bufferAll[beginIndex];
								int lBit = bufferAll[beginIndex + 1];
								// System.out.println("2 hBit: " + hBit + " lBit: " + lBit);
							}
						}
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException ie) {
					}
				}
			}
		}.start();
	
		while (nByte != -1) {
			synchronized (bufferAll) {
				nByte = targetDataLine.read(buffer, 0, bufSize);
				System.arraycopy(buffer, 0, bufferAll, bufferAllIndex, nByte);
				bufferAllIndex += nByte;
				//sourceDataLine.write(buffer, 0, nByte);
			}
		}
	}
	
	public static void main(String[] args) {
		new PickSound().startPick();
	}

}
