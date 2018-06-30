package com.basic.sound;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;

import com.basic.sound.util.SoundUtil;
import com.basic.sound.util.XFApi;

public class SoundFrame {

	final int panHeight = 400;
	int vRate, hRate;

	JFrame jFrame;
	JPanel pan;
	JPanel centerPane, buttonPane;
	JScrollBar timeLocationScrollBar;
	JButton startButton, pauseButton;
	JButton replayButton, stopReplayButton;
	JSlider hSlider;

	int point[];
	int number;
	byte bufferAll[];
	int bufferAllIndex;
	boolean continueRecorde;
	boolean ifSoundContinue;
	long soundBeginTime;
	int soundBeginIndex;
	
	public void initData() {
		point = new int[600];
		Arrays.fill(point, 0);
		number = 600;
		bufferAll = new byte[130 * 1024 * 1024];
		bufferAllIndex = 0;
		vRate = 120;
		hRate = 20;// 1470
		continueRecorde = false;
		// continueReplay = false;
		// jsbActive = true;
		ifSoundContinue = false;
		soundBeginTime = 0;
		soundBeginIndex = 0;
	}

	public SoundFrame() {
		initData();
		jFrame = new JFrame("录音并显示波形");
		centerPane = new JPanel();
		centerPane.setLayout(new BorderLayout());

		pan = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.red);
				int x[] = new int[number];
				for (int i = 0; i < number; i++) {
					x[i] = i;
					point[i] = panHeight - point[i];
				}
				g.drawPolyline(x, point, number);
				g.setColor(Color.blue);
			}
		};

		pan.setPreferredSize(new Dimension(600, panHeight));

		timeLocationScrollBar = new JScrollBar();
		timeLocationScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		timeLocationScrollBar.setMaximum(0);
		timeLocationScrollBar.setMinimum(0);
		timeLocationScrollBar.setValue(0);

		centerPane.add(pan);
		centerPane.add(timeLocationScrollBar, BorderLayout.SOUTH);
		jFrame.getContentPane().add(centerPane);
		startButton = new JButton("开始");

		startButton = new JButton("开始");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continueRecorde = true;
				// jsbActive = false;
			}
		});
		pauseButton = new JButton("暂停");
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continueRecorde = false;
				// jsbActive = true;
			}
		});

		hSlider = new JSlider();
		hSlider.setOrientation(JSlider.HORIZONTAL);
		hSlider.setMaximum(100);
		hSlider.setMinimum(1);
		hSlider.setValue(hRate);

		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		box.add(startButton);
		box.add(Box.createHorizontalStrut(10));
		box.add(pauseButton);
		box.add(Box.createHorizontalStrut(10));
		box.add(hSlider);
		box.add(Box.createHorizontalStrut(10));
		/*
		 * box.add(replayButton); box.add(Box.createHorizontalStrut(10));
		 * box.add(stopReplayButton);
		 */
		box.add(Box.createHorizontalGlue());
		box.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		jFrame.getContentPane().add(box, BorderLayout.SOUTH);
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		play();
	}

	public void play() {
		try {
			// AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
			// 44100F, 16, 1, 2, 44100F, true);
			AudioFormat audioFormat = SoundUtil.getAudioFormat();
			DataLine.Info info;
			info = new DataLine.Info(TargetDataLine.class, audioFormat);
			TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
			targetDataLine.open(audioFormat);
			targetDataLine.start();

			info = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();

			FloatControl fc = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
			double value = 2;
			float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
			fc.setValue(dB);
			int nByte = 0;
			final int bufSize = 256;
			byte[] buffer = new byte[bufSize];

			// 起线程，重绘波形
			new Thread() {
				public void run() {
					while (true) {
						if (!continueRecorde) {
							try {
								Thread.sleep(50);
							} catch (InterruptedException ie) {
							}
							continue;
						}
						
						synchronized (bufferAll) {
							if (600 * hRate * 2 < bufferAllIndex) {
								int beginIndex = bufferAllIndex - 600 * hRate * 2;
								//System.out.println("beginIndex: " + beginIndex);
								for (int i = 0; i < 600; i++, beginIndex += 2 * hRate) {
									int hBit = bufferAll[beginIndex];
									int lBit = bufferAll[beginIndex + 1];
									int abs = Math.abs(hBit) + Math.abs(lBit);
									if(abs > 90) {
										if(!ifSoundContinue) {
											ifSoundContinue = true;
											soundBeginTime = System.currentTimeMillis();
											soundBeginIndex = beginIndex;
											//System.out.println("soundBeginIndex: " + soundBeginIndex);
										}
									} else {
										if(ifSoundContinue ) {
											if(System.currentTimeMillis() - soundBeginTime > 800) {
												ifSoundContinue = false;
												soundBeginTime = System.currentTimeMillis();
												int longth = beginIndex - soundBeginIndex;
												//System.out.println("区间：" + longth);
												if(longth < 0)
													continue;
												byte[] bt = new byte[beginIndex - soundBeginIndex];
												System.arraycopy(bufferAll, beginIndex, bt, 0, beginIndex - soundBeginIndex);
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
									
									point[i] = hBit << 8 | lBit;
									point[i] /= vRate;
									point[i] += panHeight / 2;
								}
								number = 600;
								pan.repaint();
							} else {
								System.out.println("-----------");
								int beginIndex = 0;
								number = bufferAllIndex / hRate / 2;
								for (int i = 0; i < number; i++, beginIndex += 2 * hRate) {
									int hBit = bufferAll[beginIndex];
									int lBit = bufferAll[beginIndex + 1];
//									System.out.println("2 hBit: " + hBit + " lBit: " + lBit);
									point[i] = hBit << 8 | lBit;
									point[i] /= vRate;
									point[i] += panHeight / 2;
								}
								pan.repaint();
							}
							int length = bufferAllIndex / hRate / 2;
							if (length > 600) {
								timeLocationScrollBar.setMaximum(length - 600);
								timeLocationScrollBar.setValue(length - 600);
							}
						}
						try {
							Thread.sleep(10);
						} catch (InterruptedException ie) {
						}
					}
				}
			}.start();
			// 起线程，监听数据，截取指令
			new Thread() {
				@Override
				public void run() {
					while (true) {
						if (!continueRecorde) {
							try {
								Thread.sleep(50);
							} catch (InterruptedException ie) {
							}
							continue;
						}

						try {
							Thread.sleep(1000 * 20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();

			while (nByte != -1) {
				if (!continueRecorde) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException ie) {
					}
					continue;
				}
				synchronized (bufferAll) {
					nByte = targetDataLine.read(buffer, 0, bufSize);
					System.arraycopy(buffer, 0, bufferAll, bufferAllIndex, nByte);
					bufferAllIndex += nByte;
					sourceDataLine.write(buffer, 0, nByte);
				}
			}

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

}
