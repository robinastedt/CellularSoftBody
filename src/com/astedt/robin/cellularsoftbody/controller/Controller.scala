package com.astedt.robin.cellularsoftbody;
package controller;

import model.State;
import view.View;

class Controller extends Runnable {
  var view = new View;
  var state = new State;
  val viewThread = new Thread(view);
  
  def run() {
    println("Controller starting...");
    viewThread.start();
    println("Controller initializing finished! Starting controller logic...");
  }
}