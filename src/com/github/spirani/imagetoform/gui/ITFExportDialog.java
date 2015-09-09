package com.github.spirani.imagetoform.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ITFExportDialog extends JDialog {
    private JComboBox<String> paperSize;
    private JPanel panel;
    private JPanel holder;
    private JPanel labels;
    private JButton save;
    private ArrayList<ITFTextBox> boxes;

    public ITFExportDialog(ITFDisplayPanel d) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] sizes = {"Letter", "Legal", "A4"};
        paperSize = new JComboBox<String>(sizes);
        paperSize.setMaximumSize(paperSize.getPreferredSize());

        holder = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        save = new JButton("Choose Save Location");
        holder.add(save);
        holder.setMaximumSize(holder.getPreferredSize());

        labels = new JPanel(new GridLayout(0, 2));
        boxes = new ArrayList<ITFTextBox>();
        populateBoxes(d);
        for(ITFTextBox b : boxes) {
            labels.add(new JLabel(b.title));
            labels.add(new JTextField());
        }
        labels.setMaximumSize(new Dimension(600, (int)labels.getPreferredSize().getHeight()));

        panel.add(paperSize);
        panel.add(holder);
        panel.add(labels);
        this.add(panel);

        this.setTitle("Export Form to PDF");
        this.setLayout(new CardLayout(5, 5));
        this.setSize(400, 400);
        //this.setResizable(false);
        this.setVisible(true);
    }

    private void populateBoxes(ITFDisplayPanel p) {
        for(int i = 0; i < p.getBoxes().size(); i++) {
            boxes.add((ITFTextBox)p.getBoxes().get(i));
        }
    }
}
