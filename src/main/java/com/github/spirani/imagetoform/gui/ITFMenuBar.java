package com.github.spirani.imagetoform.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class ITFMenuBar extends JMenuBar implements ActionListener {
    ITFDisplayPanel panel;
    JMenu file;
    JMenuItem openImage;
    JMenuItem exportForm;
    JMenuItem exit;

    JMenu help;
    JMenuItem about;

    public ITFMenuBar(ITFDisplayPanel p) {
        panel = p;
        file = new JMenu("File");
        openImage = new JMenuItem("Open Image");
        exportForm = new JMenuItem("Export to PDF");
        exit = new JMenuItem("Exit");
        help = new JMenu("Help");
        about = new JMenuItem("About");

        openImage.addActionListener(this);
        exportForm.addActionListener(this);
        exit.addActionListener(this);
        file.add(openImage);
        file.add(exportForm);
        file.add(exit);
        this.add(file);
        about.addActionListener(this);
        help.add(about);
        this.add(help);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit) {
            System.exit(0);
        }
        if(e.getSource() == about) {
            JOptionPane.showMessageDialog(null,
                    "Image to Form v. 0.1\nwww.github.com/spirani",
                    "About Image to Form",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getSource() == openImage) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("PNG and JPG "
                    + "images", "png", "jpg"));
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    panel.loadForm(chooser.getSelectedFile().toURI().toURL());
                } catch (MalformedURLException m) {
                    System.out.println("Could not find file");
                }
            }
        }
        if(e.getSource() == exportForm) {
            new ITFExportDialog(panel);
        }
    }
}
