package com.astedt.robin.cellularsoftbody
package view

import util._
import model.Model

import scalafx.Includes._
import scalafx.scene.canvas.Canvas
import scalafx.scene.canvas.GraphicsContext
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.{Group, Scene}
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.ScrollEvent
import scalafx.scene.input.MouseDragEvent
import scalafx.event.EventType
import scalafx.scene.input.KeyCode


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
      fill = Color.DarkOliveGreen
      root = rootPane
    }
    
  }
  
  
  
 
  
  canvas.onKeyPressed = (e: KeyEvent) => observer.handleKeyEvent(e)
  
  canvas.onScroll = (e : ScrollEvent) => observer.handleScrollEvent(e)
  
  canvas.onMouseDragged = (e : MouseEvent) => observer.handleMouseDraggedEvent(e)
  canvas.onMousePressed = (e : MouseEvent) => observer.handleMousePressedEvent(e)
  
  stage.width.onChange(updateCanvasWidth)
  stage.height.onChange(updateCanvasHeight)
  
  val drawing = new Drawing(model, canvas)
  
  val updateTimer = AnimationTimer(drawing.updateCanvas)
  updateTimer.start
  
  var lastStageWidth = _width.toDouble
  var lastStageHeight = _height.toDouble
  
  def updateCanvasWidth {
    val deltaStageWidth = stage.width.value - lastStageWidth
    translate(deltaStageWidth / 2, 0)
    lastStageWidth = stage.width.value
    canvas.width <== stage.width
  }
  
  def updateCanvasHeight {
    val deltaStageHeight = stage.height.value - lastStageHeight
    translate(0, deltaStageHeight / 2)
    lastStageHeight = stage.height.value
    canvas.height <== stage.height
  }
  
  override def run() {
    this.main(Array.empty)
    updateTimer.stop
    println("View: Stopped")
    observer.notifyViewClosed()
  }
  
  def zoom(amount: Double, xCenter : Double, yCenter : Double) {
    drawing.zoom(amount, xCenter, yCenter)
  }
  
  def translate(x : Double, y : Double) {
    drawing.translate(x, y)
  }
  
}