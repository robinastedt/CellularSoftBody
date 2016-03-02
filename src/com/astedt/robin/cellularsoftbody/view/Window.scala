package com.astedt.robin.cellularsoftbody
package view

import util._
import model.Model

import scalafx.Includes._
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler;
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.Includes._
import scalafx.application.JFXApp
import javafx.embed.swing.JFXPanel
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.{Group, Scene}
import scalafx.animation.AnimationTimer

class Window(
    _width : Int, _height : Int, 
    observer : Observer, 
    model : Model) 
    extends Runnable with JFXApp {
  
  val canvas = new Canvas(_width, _height)
  
  val rootPane = new Group
  rootPane.children = List(canvas)
  
  stage = new JFXApp.PrimaryStage {
    title.value = "Cellular Soft Body"
    scene = new Scene(_width, _height) {
      fill = Color.LightGreen
      root = rootPane
    }
  }
  
  val drawing = new Drawing(model, canvas)
  
  val updateTimer = AnimationTimer(drawing.updateCanvas)
  updateTimer.start
  
  
  override def run() {
    this.main(Array.empty)
    updateTimer.stop
    println("View: Stopped")
    observer.notifyViewClosed()
  }
  
  
}