package com.astedt.robin.cellularsoftbody;
package controller;

import model.Model;
import view.View;
import view.Observer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent

class Controller extends Observer with Runnable {
  val state = new Model;
  val view = new View(state, this : Observer);
  val viewThread = new Thread(view);
  
  var test = 0;
  
  /**
   * Thread initialization
   */
  def run() {
    println("Controller starting...");
    viewThread.start();
    println("Controller initializing finished! Starting controller logic...");
    while (viewThread.isAlive()) step;
  }
  
  /**
   * Step function, called repeatedly as long as thread is alive
   */
  def step() {
    state.test = if (state.test == 0) 1 else 0
    Thread.sleep(1000);
  }
  
  override object TestRectangleMouseHandler extends EventHandler[MouseEvent] {
    override def handle(event : MouseEvent) {
      test += 1;
      println("Mouse event! " + test);
    }
  }
  
}