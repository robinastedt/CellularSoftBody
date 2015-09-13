
package com.astedt.robin.cellularsoftbody.render;

import com.astedt.robin.cellularsoftbody.world.organisms.Cell;
import com.astedt.robin.cellularsoftbody.Config;
import com.astedt.robin.cellularsoftbody.Main;
import com.astedt.robin.cellularsoftbody.physics.Physics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Font;
import java.awt.Point;
import java.awt.Polygon;

public class DrawingComponent extends JComponent{
    
    Font fontMain;
    
    //Initialize resources
    public DrawingComponent() {
        fontMain = new Font("monospaced", Font.PLAIN, 12);
    }
    
    //Is called automagically, draws a frame
    @Override
    public void paintComponent(Graphics g) {
        if (Physics.initialized) {
            Graphics2D g2 = (Graphics2D) g;

            g2.setFont(fontMain);
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
            
            synchronized (Main.monitor) {
                //Draw cells: Layer 0
                for (Cell c : Physics.cells) {
                    c.Draw(g2, 0);
                }

                //Draw cells: Layer 1
                for (Cell c : Physics.cells) {
                    c.Draw(g2, 1);
                }
            }
            

        }
    }
    
    public static Polygon pointsToPolygon(Point[] points) {
        int[] x = new int[points.length];
        int[] y = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            x[i] = points[i].x;
            y[i] = points[i].y;
        }
        return new Polygon(x, y, points.length);
    }
    
    //Method for drawing multi-line Strings
    //Thanks! aioobe @ Stackoverflow.com
    void drawString(Graphics g2, String text, int x, int y) {
        for (String line : text.split("\n"))
            g2.drawString(line, x, y += g2.getFontMetrics().getHeight());
    }
}
