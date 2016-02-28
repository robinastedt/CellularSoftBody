package com.astedt.robin.cellularsoftbody;
package controller;

import model.State;
import view.View;
import view.ViewObserver;
import view.InputEvent;

class Controller extends ViewObserver with Runnable {
  val state = new State;
  val view = new View(state, this);
  val viewThread = new Thread(view);
  
  def run() {
    println("Controller starting...");
    viewThread.start();
    println("Controller initializing finished! Starting controller logic...");
    while (viewThread.isAlive()) step;
  }
  
  def step() {
    
  }
  
  def handleInput(input : InputEvent) {
    println("Testing: Input handled");
  }
}