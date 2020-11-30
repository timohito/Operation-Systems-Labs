package com.company;

import javax.swing.*;
import java.awt.*;

public class DrawDisk extends JPanel {
    private Disk disk;

    public void paint(Graphics g) {
        int sizeX = 30;
        int x = 0;
        int y = 0;
        for (int i = 0; i < disk.getClSize(); i++) {
            if (x + sizeX >= 900 - sizeX) {
                x = 0;
                y += sizeX;
            }
            if (disk.getClIndex(i).getStatus() == ClusterStatus.IN_USE) {
                g.setColor(Color.RED);
            } else if (disk.getClIndex(i).getStatus() == ClusterStatus.EMPTY) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(Color.BLUE);
            }
            g.fillRect(x, y, sizeX, sizeX);
            g.setColor(Color.black);
            g.drawRect(x, y, sizeX, sizeX);
            x += sizeX;
        }
    }

    public DrawDisk(Disk physDisk) {
        this.disk = physDisk;
    }
} 