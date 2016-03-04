package com.astedt.robin.cellularsoftbody;
package view;

import javafx.scene.input.MouseEvent
import javafx.event.EventHandler;
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext

import model.Model
import util._

import scalafx.Includes._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class View(private val model : Model, private val observer : Observer) {
  
  private val DEFAULT_WIDTH = 800
  private val DEFAULT_HEIGHT = 800
  
  val window = new Window(DEFAULT_WIDTH, DEFAULT_HEIGHT, observer, model);
  val viewThread = new Thread(window);
  
  
  
  println("View: Initialized")
  
  def start() {
    viewThread.start();
    println("View: Started");
  }
  
  def zoom(amount : Double, xCenter : Double, yCenter : Double) {
    window.zoom(amount, xCenter, yCenter)
  }
  
  def translate(x : Double, y : Double) {
    window.translate(x, y)
  }
  
  def width = window.sceneWidth
  def height = window.sceneHeight
  
}