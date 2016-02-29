package com.astedt.robin.cellularsoftbody;
package view;

import javafx.scene.input.MouseEvent
import javafx.event.EventHandler;
import scalafx.scene.canvas.Canvas

import model.Model;
import util.Timer;

import scalafx.Includes._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle


class View(private val model : Model, private val observer : Observer) {
  
  val WIDTH = 600;
  val HEIGHT = 450;
  val window = new Window(WIDTH, HEIGHT, observer, model);
  val viewThread = new Thread(window);
  
  println("View: Initialized")
  
  def start() {
    viewThread.start();
    println("View: Started");
  }
  
  
  
  
}