/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.astedt.robin.cellularsoftbody.console;

import com.astedt.robin.cellularsoftbody.Main;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Robin
 */
public class ConsoleKeyListener extends KeyAdapter {
    public boolean keyUp;
    public boolean keyDown;
    
    public void KeyAdapter() {
        keyUp = false;
        keyDown = false;
    }
    
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                Main.console.previousCommand(-1);
                keyUp = true;
                break;
            case KeyEvent.VK_DOWN:
                Main.console.previousCommand(1);
                keyDown = true;
                break;
            default:
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                keyUp = false;
                break;
            case KeyEvent.VK_DOWN:
                keyDown = false;
                break;
            default:
                break;
        }
    }
}
