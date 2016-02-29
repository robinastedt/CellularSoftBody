package com.astedt.robin.cellularsoftbody
package view

import util._
import model.Model

import scalafx.Includes._
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler;
import scalafx.scene.canvas.Canvas
import scalafx.Includes._
import scalafx.application.JFXApp
import javafx.embed.swing.JFXPanel
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.{Group, Scene}

class Window(_width : Int, _height : Int, observer : Observer, model : Model) extends Runnable with JFXApp {
  
  val canvas = new Canvas(_width, _height)
  
  
  val rootPane = new Group
  rootPane.children = List(canvas)
  
  stage = new JFXApp.PrimaryStage {
    title.value = "Hello Stage"
    scene = new Scene(_width, _height) {
      fill = Color.LightGreen
      root = rootPane
    }
  }
  
  val gc = canvas.graphicsContext2D
  
  val updateTimer = new Timer(20, true, updateCanvas);
  updateTimer.start
  
  def updateCanvas() {
    gc.clearRect(0,0,stage.width.value,stage.height.value)
    model.cells.foreach { cell => 
      val pos = cell.getPosition
      val x = pos.x
      val y = pos.y
      gc.fillOval(x, y, 10, 10)
    }
  }
  
  override def run() {
    this.main(Array.empty);
    observer.notifyViewClosed();
    updateTimer.stop
    println("View: Stopped")
  }
  
  
}