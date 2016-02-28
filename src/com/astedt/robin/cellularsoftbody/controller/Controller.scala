package com.astedt.robin.cellularsoftbody;
package controller;

import model.State;
import view.View;
import view.ViewObserver;
import view.InputEvent;

import scala.collection.mutable.Queue;

class Controller extends ViewObserver with Runnable {
  val state = new State;
  val view = new View(state, this);
  val viewThread = new Thread(view);
  
  val inputEventQueue = new Queue[InputEvent];
  
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
    while (!inputEventQueue.isEmpty) {
      handleInput(inputEventQueue.dequeue())
    }
  }
  
  /**
   * Called from Controller thread
   */
  def handleInput(event : InputEvent) {
    println("Controller Testing: Input handled");
  }
  
  
  /**
   * Called from View thread
   */
  def receiveInput(event : InputEvent) {
    inputEventQueue += event;
    println("View Testing: Input received and handed over to Controller thread");
  }
}