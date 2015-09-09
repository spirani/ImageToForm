package com.github.spirani.imagetoform.gui;

import javax.swing.*;

public class ITFDisplayScroll extends JScrollPane {
    private ITFDisplayPanel d;

    public ITFDisplayScroll() {
        d = new ITFDisplayPanel();
        this.add(d);
    }

}
