package com.github.spirani.imagetoform.gui;

import com.github.spirani.imagetoform.PDFTools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ITFExportDialog extends JDialog implements ActionListener {
    private ITFDisplayPanel disp;
    private JComboBox<String> paperSize;
    private JFileChooser chooser;
    private JPanel panel;
    private JPanel topholder;
    private JPanel holder;
    private JPanel labels;
    private JButton save;
    private JButton close;
    private JButton export;
    private ArrayList<ITFTextBox> boxes;
    private ArrayList<JTextField> values;

    public ITFExportDialog(ITFDisplayPanel d) {
        disp = d;
        chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] sizes = {"Letter", "Legal", "A4"};
        topholder = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paperSize = new JComboBox<String>(sizes);
        paperSize.setMaximumSize(paperSize.getPreferredSize());
        topholder.add(paperSize);
        close = new JButton("Close Dialog");
        close.addActionListener(this);
        topholder.add(close);
        topholder.setMaximumSize(topholder.getPreferredSize());

        holder = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        save = new JButton("Choose Save Location");
        save.addActionListener(this);
        export = new JButton("Export to PDF");
        export.addActionListener(this);
        holder.add(save);
        holder.add(export);
        holder.setMaximumSize(holder.getPreferredSize());

        labels = new JPanel(new GridLayout(0, 2));
        boxes = new ArrayList<ITFTextBox>();
        values = new ArrayList<JTextField>();
        populateBoxes(d);
        for(ITFTextBox b : boxes) {
            labels.add(new JLabel(b.title));
            JTextField temp = new JTextField();
            values.add(temp);
            labels.add(temp);
        }
        labels.setMaximumSize(new Dimension(600, (int) labels.getPreferredSize().getHeight()));

        panel.add(topholder);
        panel.add(holder);
        panel.add(labels);
        this.add(panel);

        this.setTitle("Export Form to PDF");
        this.setLayout(new CardLayout(5, 5));
        this.setSize(400, Math.max(panel.getMaximumSize().height, 400));
        //this.setResizable(false);
        this.setVisible(true);
    }

    private void populateBoxes(ITFDisplayPanel p) {
        for(int i = 0; i < p.getBoxes().size(); i++) {
            boxes.add((ITFTextBox)p.getBoxes().get(i));
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == close) {
            this.dispose();
        }
        if(e.getSource() == save) {
            chooser.showSaveDialog(null);
        }
        if(e.getSource() == export) {
            if(chooser.getSelectedFile() == null) {
                JOptionPane.showMessageDialog(this, "Must select a save location first!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean r = PDFTools.generateAndSavePDF(disp.getImage().getWidth(), null,
                                            chooser.getSelectedFile().toString(), values, boxes);
                if(r) {
                    JOptionPane.showMessageDialog(this, "Export successful!", "Message",
                                                  JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "The form did not export properly.", "Error",
                                                  JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
