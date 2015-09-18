package com.github.spirani.imagetoform.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ITFSidePanel extends JPanel implements ActionListener {
    ITFDisplayPanel panel;
    JButton zoomOut;
    JButton zoomIn;
    JButton addBox;
    JButton delBox;
    JButton sizeUp;
    JButton sizeDown;
    JButton toggleOutline;

    public ITFSidePanel(ITFDisplayPanel p) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        panel = p;
        zoomOut = new JButton("Zoom Out");
        zoomOut.addActionListener(this);
        zoomOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomIn = new JButton("Zoom In");
        zoomIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        zoomIn.addActionListener(this);
        addBox = new JButton("Add Text Box");
        addBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBox.addActionListener(this);
        delBox = new JButton("Delete Text Box");
        delBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        delBox.addActionListener(this);
        sizeUp = new JButton("Increase Text Box Width");
        sizeUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeUp.addActionListener(this);
        sizeDown = new JButton("Decrease Text Box Width");
        sizeDown.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeDown.addActionListener(this);
        toggleOutline = new JButton("Toggle Outlines");
        toggleOutline.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleOutline.addActionListener(this);
        this.add(zoomOut);
        this.add(zoomIn);
        this.add(addBox);
        this.add(delBox);
        this.add(sizeUp);
        this.add(sizeDown);
        this.add(toggleOutline);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == zoomOut && panel.getScale() >= 0.2 && panel.isImageLoaded()) {
            panel.updateScale(panel.getScale() - 0.1);
        }
        if(e.getSource() == zoomIn && panel.isImageLoaded()) {
            panel.updateScale(panel.getScale() + 0.1);
        }
        if(e.getSource() == addBox && panel.isImageLoaded()) {
            panel.setAddingBox(true);
        }
        if(e.getSource() == delBox && panel.isImageLoaded()) {
            panel.setDeletebox(true);
        }
        if(e.getSource() == sizeUp && panel.getSelected() != null) {
            panel.getSelected().width += 15;
            panel.revalidate();
            panel.repaint();
        }
        if(e.getSource() == sizeDown && panel.getSelected() != null) {
            panel.getSelected().width -= 15;
            panel.revalidate();
            panel.repaint();
        }
        if(e.getSource() == toggleOutline) {
            panel.setOutlined(!panel.getOutlined());
            panel.revalidate();
            panel.repaint();
        }
    }
}
