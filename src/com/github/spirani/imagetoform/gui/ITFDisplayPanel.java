package com.github.spirani.imagetoform.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ITFDisplayPanel extends JPanel implements MouseListener {
    private BufferedImage form;
    private double scale;
    private boolean imageLoaded;
    private boolean addingBox;
    private boolean deletebox;
    private boolean outlined;
    private ITFTextBox selected;
    private CopyOnWriteArrayList<ITFTextBox> boxes = new CopyOnWriteArrayList<ITFTextBox>();

    public ITFDisplayPanel() {
        //loadForm();
        addMouseListener(this);
        scale = 1.0;
        imageLoaded = false;
        addingBox = false;
        deletebox = false;
        outlined = false;
        selected = null;
    }

    public void loadForm(URL u) {
        try {
            System.out.println(u.getPath());
            form = ImageIO.read(u);
        } catch (IOException e) {
            System.out.println("Could not open file");
        }
        imageLoaded = true;
        revalidate();
        repaint();
    }

    public void drawTextBoxes(Graphics2D g2, double scale) {
        if(imageLoaded) {
            for(ITFTextBox b : boxes) {
                if(b == selected) {
                    g2.setColor(Color.magenta);
                } else {
                    g2.setColor(Color.red);
                }
                int x, y;
                if(getWidth() <= form.getWidth()*scale) {
                    x = (int)(b.x*scale);
                } else {
                    x = (int)(b.x*scale) + (getWidth() - (int)(form.getWidth()*scale))/2;
                }
                if(getHeight() <= form.getHeight()*scale) {
                    y = (int)(b.y*scale);
                } else {
                    y = (int)(b.y*scale) + (getHeight() - (int)(form.getHeight()*scale))/2;
                }
                if(!outlined) {
                    g2.fillRect(x, y, (int) (b.width * scale), (int) (b.height * scale));
                } else {
                    g2.drawRect(x, y, (int) (b.width * scale), (int) (b.height * scale));
                }
                g2.setColor(Color.black);
                g2.drawString(b.title, x+(float)(5*scale), y+(float)(20*scale));
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int w = getWidth();
        int h = getHeight();
        int imageWidth = 1;
        int imageHeight = 1;
        if(imageLoaded) {
            imageWidth = form.getWidth();
            imageHeight = form.getHeight();
        }
        double x = (w - scale * imageWidth)/2;
        double y = (h - scale * imageHeight)/2;
        AffineTransform at = AffineTransform.getTranslateInstance(x,y);
        at.scale(scale, scale);
        g2.drawRenderedImage(form, at);
        drawTextBoxes(g2, scale);
    }

    public Dimension getPreferredSize()
    {
        if(imageLoaded) {
            int w = (int) (scale * form.getWidth());
            int h = (int) (scale * form.getHeight());
            return new Dimension(w, h);
        } else {
            return new Dimension(0, 0);
        }
    }

    public BufferedImage getImage() {
        return form;
    }

    public double getScale() {
        return scale;
    }

    public boolean isImageLoaded() {
        return imageLoaded;
    }

    public ITFTextBox getSelected() {
        return selected;
    }

    public boolean getOutlined() {
        return outlined;
    }

    public CopyOnWriteArrayList getBoxes() {
        return boxes;
    }

    public void setAddingBox(boolean a) {
        addingBox = a;
    }

    public void setDeletebox(boolean b) {
        deletebox = b;
    }

    public void setOutlined(boolean o) {
        outlined = o;
    }

    public void updateScale(double s)
    {
        scale = s;
        revalidate();
        repaint();
    }

    public boolean inBounds(int x, int y) {
        int width = getWidth();
        int height = getHeight();
        int cornerx = (width - (int)(scale * form.getWidth())) / 2;
        int cornery = (height - (int)(scale * form.getHeight())) / 2;
        if(x > cornerx && x < (cornerx + form.getWidth()*scale)) {
            if(y > cornery && y < (cornery + form.getHeight()*scale)) {
                return true;
            }
        }
        return false;
    }

    public void mousePressed(MouseEvent e) {
        int mx = 0;
        int my = 0;
        if(imageLoaded) {
            mx = (int) (e.getX() / scale) - (int) ((getWidth() - form.getWidth() * scale) / (2 * scale));
            my = (int) (e.getY() / scale) - (int) ((getHeight() - form.getHeight() * scale) / (2 * scale));
        }
        // Check if we have clicked any text box
        for(ITFTextBox box : boxes) {
            if(mx > box.x && mx < (box.x + box.width)) {
                if(my > box.y && my < (box.y + box.height)) {
                    selected = box;

                    // If we are in deletion mode, remove this box
                    if(deletebox) {
                        addingBox = false;
                        boxes.remove(box);
                        deletebox = false;
                    }

                    // If this box was right-clicked, change its title
                    if(e.getButton() == MouseEvent.BUTTON3) {
                        box.title = JOptionPane.showInputDialog("Enter new title: ");
                        if(box.title == null) {
                            box.title = "Default";
                        }
                        revalidate();
                        repaint();
                    }
                }
            }
        }

        // Handle repositioning
        if(e.getButton() == MouseEvent.BUTTON2 && selected != null && inBounds(e.getX(), e.getY())) {
            selected.x = mx;
            selected.y = my;
        }

        // Add a new text box
        if(addingBox && imageLoaded && inBounds(e.getX(), e.getY())) {
            deletebox = false;
            int x, y = 0;
            if(getWidth() <= form.getWidth()*scale) {
                x = (int)(e.getX()/scale);
            } else {
                x = (int)(e.getX()/scale) - (int)((getWidth() - form.getWidth()*scale)/(2*scale));
            }
            if(getHeight() <= form.getHeight()*scale) {
                y = (int)(e.getY()/scale);
            } else {
                y = (int)(e.getY()/scale) - (int)((getHeight() - form.getHeight()*scale)/(2*scale));
            }
            boxes.add(new ITFTextBox(x, y, 150, 30));
            addingBox = false;
        }

        // If the user "clicks out", make sure to disable add/delete modes
        if(!inBounds(e.getX(), e.getY())) {
            addingBox = false;
            deletebox = false;
        }
        revalidate();
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
