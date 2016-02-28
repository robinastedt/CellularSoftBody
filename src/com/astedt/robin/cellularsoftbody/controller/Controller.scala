package com.astedt.robin.cellularsoftbody;
package controller;

import model.State;
import view.View;
import view.Observer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent

import scala.collection.mutable.Queue;

class Controller extends Observer with Runnable {
  val state = new State;
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
    
  }
  
  object TestRectangleMouseHandler extends EventHandler[MouseEvent] {
    override def handle(event : MouseEvent) {
      test += 1;
      println("Mouse event! " + test);
      
    }
  }
  
  object OtherTestHandler extends EventHandler[MouseEvent] {
    override def handle(event : MouseEvent) {
      //Do stuff
    }
  }
  
}