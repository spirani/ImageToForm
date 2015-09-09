package com.github.spirani.imagetoform.gui;

import com.github.spirani.imagetoform.PDFTools;

import javax.swing.*;

public class MainGUI {
    private JFrame frame;
    private JSplitPane pane;
    private ITFMenuBar menuBar;
    private ITFSidePanel first;
    private JScrollPane second;
    private ITFDisplayPanel second_helper;

    public MainGUI() {
        frame = new JFrame("Image to Form");
        pane = new JSplitPane();
        second_helper = new ITFDisplayPanel();
        menuBar = new ITFMenuBar(second_helper);
        first = new ITFSidePanel(second_helper);
        second = new JScrollPane(second_helper);
        //PDFTools a = new PDFTools();
    }

    public void startGUI() {
        frame.setJMenuBar(menuBar);

        pane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        pane.setLeftComponent(first);
        pane.setRightComponent(second);
        //pane.setDividerLocation(250);
        frame.add(pane);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
