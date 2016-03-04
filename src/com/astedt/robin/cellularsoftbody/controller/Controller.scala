package com.astedt.robin.cellularsoftbody;
package controller;

import model.Model;
import view.View;
import view.Observer;
import util._;

import scalafx.Includes._
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.scene.input.ScrollEvent
import scalafx.scene.input.MouseEvent


object Controller {
  
  //SCROLL_ZOOM_SPEED_TWEAK:
  //A value of 0.02 means that zoomSpeed=1 will double objects size once per scroll event
  private val TWEAK_ZOOM_SPEED_SCROLL = 0.02
  
  //KEY_ZOOM_SPEED_TWEAK:
  //40 times as SCROLL_ZOOM_SPEED_TWEAK to make one key press do the same as one scroll event
  private val TWEAK_ZOOM_SPEED_KEY = TWEAK_ZOOM_SPEED_SCROLL * 40
  
  //KEY_TRANSLATE_SPEED_TWEAK: 
  //A value of 1.00 means that translateSpeed=1 will translate one screen length per key event
  private val TWEAK_TRANSLATE_SPEED_KEY = 1.00
  
  private val KEY_TRANSLATE_UP = KeyCode.Up
  private val KEY_TRANSLATE_DOWN = KeyCode.Down
  private val KEY_TRANSLATE_LEFT = KeyCode.Left
  private val KEY_TRANSLATE_RIGHT = KeyCode.Right
  private val KEY_ZOOM_IN = KeyCode.Add
  private val KEY_ZOOM_OUT = KeyCode.Subtract
  
}
class Controller extends Observer {
  var timeScale = 1
  var tps = 60
  var zoomSpeed = 0.1
  var translateSpeed = 0.1
  
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
    val amount = event.deltaY * zoomSpeed * Controller.TWEAK_ZOOM_SPEED_SCROLL
    val adjusted = if (amount > 0) amount else 1 / (1 - amount) - 1
    view.zoom(adjusted, event.x, event.y)
  }
  
  override def handleKeyEvent(event : KeyEvent) {
    event.code match {
      case Controller.KEY_TRANSLATE_UP => 
        view.translate(0, translateSpeed * Controller.TWEAK_TRANSLATE_SPEED_KEY * view.height)
      case Controller.KEY_TRANSLATE_DOWN => 
        view.translate(0, -translateSpeed * Controller.TWEAK_TRANSLATE_SPEED_KEY * view.height)
      case Controller.KEY_TRANSLATE_LEFT => 
        view.translate(translateSpeed * Controller.TWEAK_TRANSLATE_SPEED_KEY * view.height, 0)
      case Controller.KEY_TRANSLATE_RIGHT => 
        view.translate(-translateSpeed * Controller.TWEAK_TRANSLATE_SPEED_KEY * view.height, 0)
      case Controller.KEY_ZOOM_IN =>
        view.zoom(zoomSpeed * Controller.TWEAK_ZOOM_SPEED_KEY, view.width / 2, view.height / 2)
      case Controller.KEY_ZOOM_OUT =>
        view.zoom(1 / (1 + zoomSpeed * Controller.TWEAK_ZOOM_SPEED_KEY) - 1, view.width / 2, view.height / 2)
      case _ => ()
    }
  }
  
  override def notifyViewClosed() {
    controllerTimer.stop
    println("Controller: Stopped");
  }
}