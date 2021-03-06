package com.basic.sound.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class SoundUtil {
	
	//sound history
	public static byte[] history_sound = new byte[1024*1024];

	public static AudioFormat getAudioFormat() {
		AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
		float rate = 8000f;
		int sampleSize = 16;
		// String signedString = "signed";
		boolean bigEndian = true;
		int channels = 1;
		return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
	}

	public static String saveSound(byte[] bt) {
		ByteArrayInputStream bis = new ByteArrayInputStream(bt);
		AudioFormat af = SoundUtil.getAudioFormat();
		AudioInputStream ais = new AudioInputStream(bis, af, bt.length / af.getFrameSize());
		File file = null;
		File filePath = new File("D:/AudioFile");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		long time = System.currentTimeMillis();
		file = new File(filePath + "/" + time + ".wav");
		try {
			AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (ais != null) {
					ais.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file.getAbsolutePath();
	}
	
	public void checkSoundContinue() {
		
	}
	
	public int[] genPoint(int hRate, int vRate, int panHeight, int bufferAllIndex, byte[] bufferAll) {
		int[] point = new int[600];
		int beginIndex = bufferAllIndex - 600 * hRate * 2;
		for (int i = 0; i < 600; i++, beginIndex += 2 * hRate) {
			int hBit = bufferAll[beginIndex];
			int lBit = bufferAll[beginIndex + 1];
			point[i] = hBit << 8 | lBit;
			point[i] /= vRate;
			point[i] += panHeight / 2;
		}
		return point;
	}

}
