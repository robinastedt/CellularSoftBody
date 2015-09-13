/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astedt.robin.cellularsoftbody.physics;

import com.astedt.robin.cellularsoftbody.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Robin
 */
//Updates measured tps
public class BenchmarkTickTimerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Main.tps = Main.benchmarkTickCount;
        Main.benchmarkTickCount = 0;
    }
}