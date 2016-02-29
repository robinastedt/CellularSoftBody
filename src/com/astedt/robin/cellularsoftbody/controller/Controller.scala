package com.astedt.robin.cellularsoftbody;
package controller;

import model.Model;
import view.View;
import view.Observer;
import util._;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent

class Controller extends Observer {
  private val model = new Model();
  private val view = new View(model, this : Observer);
  private val controllerTimer = new Timer(1000 / 60, true, step);
  
  println("Controller: Initialized")
  
  def start() {
    controllerTimer.start;
    view.start();
    println("Controller: Started");
  }
  
  def step() = {
    model.step(1.0 / 60.0);
    //Thread.sleep(1);
  }
  
  override def notifyViewClosed() {
    controllerTimer.stop
    println("Controller: Stopped");
  }
  
  
}