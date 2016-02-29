package com.astedt.robin.cellularsoftbody;
package controller;

import model.Model;
import view.View;
import view.Observer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent

class Controller extends Observer with Runnable {
  val model = new Model;
  val view = new View(model, this : Observer);
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
    model.step(1.0 / 60.0);
    model.test = if (model.test == 0) 1 else 0
    println(model.cells(0).physicsBody.getPosition);
    Thread.sleep(1000 / 60);
  }
  
  override object TestRectangleMouseHandler extends EventHandler[MouseEvent] {
    override def handle(event : MouseEvent) {
      test += 1;
      println("Mouse event! " + test);
    }
  }
  
}