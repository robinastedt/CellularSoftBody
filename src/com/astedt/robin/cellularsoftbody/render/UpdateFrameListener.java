/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astedt.robin.cellularsoftbody.render;

import com.astedt.robin.cellularsoftbody.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Robin
 */
//Updates main window frame
public class UpdateFrameListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Main.window.repaint();
        Main.benchmarkFrameCount++;
    }
}
