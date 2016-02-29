package com.astedt.robin.cellularsoftbody.util

class Timer(interval: Int, repeats: Boolean = true, op : => Unit) {
  val timeOut = new javax.swing.AbstractAction() {
    def actionPerformed(e : java.awt.event.ActionEvent) = op
  }
  val t = new javax.swing.Timer(interval, timeOut)
  t.setRepeats(repeats)
  
  def stop = t.stop
  def start = t.start
}
