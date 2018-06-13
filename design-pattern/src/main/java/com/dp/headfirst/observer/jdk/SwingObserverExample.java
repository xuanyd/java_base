package com.dp.headfirst.observer.jdk;

import javax.swing.JFrame; 
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SwingObserverExample {
    
    JFrame jFrame;

    public void go() {
        jFrame  = new JFrame();
        JButton button = new JButton();
        button.addActionListener(new AngenListener());
        button.addActionListener(new DevilListener());

        jFrame.getContentPane().add(BorderLayout.CENTER, button);
        jFrame.setSize(400, 400);
        jFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
    	SwingObserverExample ex = new SwingObserverExample();
    	ex.go();
    }

    class AngenListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("Dont do it, you might regret it!");
        }
    }

    class DevilListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("Come on, do it!");
        }
    }

}