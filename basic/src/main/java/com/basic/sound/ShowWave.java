package com.basic.sound;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 2011-6-23 0:53:40
 * 
 * @author Administrator
 */
public class ShowWave {
	JFrame frame;
	JPanel pan;
	final int panHeight = 400;
	JScrollBar timeLocationScrollBar;
	int point[];
	int number;
	byte bufferAll[];
	int bufferAllIndex;
	int vRate, hRate;
	JButton startButton, pauseButton;
	JButton replayButton, stopReplayButton;
	boolean continueRecorde;
	boolean continueReplay;
	JPanel centerPane, buttonPane;
	JSlider hSlider;
	boolean jsbActive;

	public ShowWave() {
		initData();
		frame = new JFrame("录音并显示波形");
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
		timeLocationScrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (jsbActive == false) {
					return;
				}
				synchronized (bufferAll) {
					int beginIndex = timeLocationScrollBar.getValue();
					beginIndex = beginIndex * 2 * hRate;
					if (beginIndex == 0) {
						number = bufferAllIndex / hRate / 2;
						if (number > 600) {
							number = 600;
						}
					} else {
						number = 600;
					}
					for (int i = 0; i < number; i++, beginIndex += 2 * hRate) {
						int hBit = bufferAll[beginIndex];
						int lBit = bufferAll[beginIndex + 1];
						point[i] = hBit << 8 | lBit;
						point[i] /= vRate;
						point[i] += panHeight / 2;
					}
					pan.repaint();
				}
			}
		});
		centerPane = new JPanel();
		centerPane.setLayout(new BorderLayout());
		centerPane.add(pan);
		centerPane.add(timeLocationScrollBar, BorderLayout.SOUTH);
		frame.getContentPane().add(centerPane);
		startButton = new JButton("开始");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continueRecorde = true;
				jsbActive = false;
			}
		});
		pauseButton = new JButton("暂停");
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continueRecorde = false;
				jsbActive = true;
			}
		});
		hSlider = new JSlider();
		hSlider.setOrientation(JSlider.HORIZONTAL);
		hSlider.setMaximum(100);
		hSlider.setMinimum(1);
		hSlider.setValue(hRate);
		hSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				hRate = hSlider.getValue();
				int length = bufferAllIndex / hRate / 2;
				length -= 600;
				int value = (int) ((double) timeLocationScrollBar.getValue()
						/ timeLocationScrollBar.getMaximum() * length);
				jsbActive = false;
				timeLocationScrollBar.setMaximum(length);
				jsbActive = true;
				timeLocationScrollBar.setValue(value);
			}
		});
		replayButton = new JButton("回放");
		replayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (continueRecorde || continueReplay) {
					return;
				}
				new Thread() {
					public void run() {
						try {
							continueReplay = true;
							AudioFormat audioFormat = new AudioFormat(
									AudioFormat.Encoding.PCM_SIGNED, 44100F,
									16, 1, 2, 44100F, true);
							DataLine.Info info = new DataLine.Info(
									SourceDataLine.class, audioFormat);
							SourceDataLine sourceDataLine;
							sourceDataLine = (SourceDataLine) AudioSystem
									.getLine(info);
							sourceDataLine.open(audioFormat);
							sourceDataLine.start();
							FloatControl fc = (FloatControl) sourceDataLine
									.getControl(FloatControl.Type.MASTER_GAIN);
							double value = 2;
							float dB = (float) (Math.log(value == 0.0 ? 0.0001
									: value)
									/ Math.log(10.0) * 20.0);
							fc.setValue(dB);
							int beginIndex = timeLocationScrollBar.getValue();
							beginIndex = beginIndex * 2 * hRate;
							int bufSize = 1024;
							byte buffer[] = new byte[bufSize];
							while (beginIndex < bufferAllIndex
									&& continueReplay) {
								synchronized (bufferAll) {
									int nByte = bufferAllIndex - beginIndex > bufSize ? bufSize
											: bufferAllIndex - beginIndex;
									System.arraycopy(bufferAll, beginIndex,
											buffer, 0, nByte);
									sourceDataLine.write(buffer, 0, nByte);
									// System.out.println(beginIndex+"  "+bufferAllIndex);
									beginIndex += nByte;
									if (beginIndex / 2 / hRate <= timeLocationScrollBar
											.getMaximum()) {
										timeLocationScrollBar
												.setValue(beginIndex / 2
														/ hRate);
									}
								}
							}
							sourceDataLine.flush();
							sourceDataLine.stop();
							sourceDataLine.close();
							continueReplay = false;
						} catch (Exception ee) {
							ee.printStackTrace();
						}
					}
				}.start();
			}
		});
		stopReplayButton = new JButton("停止回放");
		stopReplayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continueReplay = false;
			}
		});
		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		box.add(startButton);
		box.add(Box.createHorizontalStrut(10));
		box.add(pauseButton);
		box.add(Box.createHorizontalStrut(10));
		box.add(hSlider);
		box.add(Box.createHorizontalStrut(10));
		box.add(replayButton);
		box.add(Box.createHorizontalStrut(10));
		box.add(stopReplayButton);
		box.add(Box.createHorizontalGlue());
		box.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.getContentPane().add(box, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		play();
	}

	public void initData() {
		point = new int[600];
		Arrays.fill(point, 0);
		number = 600;
		bufferAll = new byte[130 * 1024 * 1024];
		bufferAllIndex = 0;
		vRate = 120;
		hRate = 20;// 1470
		continueRecorde = false;
		continueReplay = false;
		jsbActive = true;
	}

	public void play() {
		try {
			AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100F, 16, 1, 2, 44100F, true);
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
								for (int i = 0; i < 600; i++, beginIndex += 2 * hRate) {
									int hBit = bufferAll[beginIndex];
									int lBit = bufferAll[beginIndex + 1];
									point[i] = hBit << 8 | lBit;
									point[i] /= vRate;
									point[i] += panHeight / 2;
								}
								number = 600;
								pan.repaint();
							} else {
								int beginIndex = 0;
								number = bufferAllIndex / hRate / 2;
								for (int i = 0; i < number; i++, beginIndex += 2 * hRate) {
									int hBit = bufferAll[beginIndex];
									int lBit = bufferAll[beginIndex + 1];
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
					System.arraycopy(buffer, 0, bufferAll, bufferAllIndex,
							nByte);
					bufferAllIndex += nByte;
					sourceDataLine.write(buffer, 0, nByte);
				}
				// try{
				// Thread.sleep(10);
				// }catch(InterruptedException ie){}
			}
			sourceDataLine.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new ShowWave();
	}
}
