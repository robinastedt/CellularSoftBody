package com.astedt.robin.cellularsoftbody;
package controller;

import model.Model;
import view.View;
import view.Observer;
import util._;

import scalafx.Includes._
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.ScrollEvent
import scalafx.scene.input.MouseEvent



class Controller extends Observer {
  var timeScale = 1
  var tps = 60
  var zoomSpeed = 4
  
  private val model = new Model();
  private val view = new View(model, this : Observer);
  private val controllerTimer = new Timer(1000 / tps, true, step);
  
  println("Controller: Initialized")
  
  def start() {
    view.start();
    Thread.sleep(1000)
    controllerTimer.start;
    println("Controller: Started");
  }
  
  def step() = {
    model.step(1.0 / tps * timeScale);
  }
  
  private var mousePressedX = 0.0;
  private var mousePressedY = 0.0;
  
  override def handleMousePressedEvent(event : MouseEvent) {
    mousePressedX = event.x
    mousePressedY = event.y
  }
  
  override def handleMouseDraggedEvent(event : MouseEvent) {
    val deltaX = mousePressedX - event.x
    val deltaY = mousePressedY - event.y
    mousePressedX = event.x
    mousePressedY = event.y
    view.translate(-deltaX, -deltaY)
  }
  
  override def handleScrollEvent(event : ScrollEvent) {
    view.zoom(event.deltaY * zoomSpeed / 1000, event.x, event.y)
  }
  
  override def handleKeyEvent(event : KeyEvent) {
    println("Test: Key")
  }
  
  override def notifyViewClosed() {
    controllerTimer.stop
    println("Controller: Stopped");
  }
}