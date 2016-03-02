package com.astedt.robin.cellularsoftbody;
package controller;

import model.Model;
import view.View;
import view.Observer;
import util._;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent

object Controller {
  var timeScale = 1
  var tps = 60 * 4
}

class Controller extends Observer {
  private val model = new Model();
  private val view = new View(model, this : Observer);
  private val controllerTimer = new Timer(1000 / Controller.tps, true, step);
  
  println("Controller: Initialized")
  
  def start() {
    view.start();
    Thread.sleep(5000)
    controllerTimer.start;
    println("Controller: Started");
  }
  
  def step() = {
    model.step(1.0 / Controller.tps * Controller.timeScale);
    //Thread.sleep(1);
  }
  
  override def notifyViewClosed() {
    controllerTimer.stop
    println("Controller: Stopped");
  }
}