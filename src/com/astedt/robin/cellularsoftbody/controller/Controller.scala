package com.astedt.robin.cellularsoftbody;
package controller;

import model.State;
import view.View;
import view.ViewObserver;
import view.InputEvent;

class Controller extends Runnable with ViewObserver {
  val state = new State;
  val view = new View(state, this);
  val viewThread = new Thread(view);
  
  def run() {
    println("Controller starting...");
    viewThread.start();
    println("Controller initializing finished! Starting controller logic...");
    
    //TODO: Controller logic loop
  }
  
  def handleInput(input : InputEvent) {
    //Testing
    println("Observer: handled input");
  }
}