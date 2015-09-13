
package com.astedt.robin.cellularsoftbody.render;

import com.astedt.robin.cellularsoftbody.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Robin
 */
//Updates measured fps
public class BenchmarkFrameTimerListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Main.fps = Main.benchmarkFrameCount;
        Main.benchmarkFrameCount = 0;
    }
}
