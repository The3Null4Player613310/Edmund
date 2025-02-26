/*
################################################################
#Edmund: GUI.java
#Copyright © 2017-2025 Allison Munn
#FULL COPYRIGHT NOTICE IS IN README
################################################################
*/

package com.thenullplayer.ai.edmund;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI
{
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JTextField inputField;
    public static GUI gui;

    public GUI()
    {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 1));

        headerLabel = new JLabel("",JLabel.CENTER );
        statusLabel = new JLabel("",JLabel.CENTER);
        statusLabel.setSize(350,100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        inputField = new JTextField();

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);

        controlPanel.add(inputField);

        mainFrame.setVisible(true);
    }

    public static void start()
    {
         gui = new GUI();
    }
}
