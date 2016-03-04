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
import scalafx.scene.input.InputEvent
import scalafx.event.EventHandler
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
      onKeyPressed = (e: KeyEvent) => observer.handleKeyEvent(e)
    }
    
  }
  
  canvas.onScroll = (e : ScrollEvent) => observer.handleScrollEvent(e)
  
  canvas.onMouseDragged = (e : MouseEvent) => observer.handleMouseDraggedEvent(e)
  canvas.onMousePressed = (e : MouseEvent) => observer.handleMousePressedEvent(e)
  
  val drawing = new Drawing(model, canvas)
  
  val updateTimer = AnimationTimer(drawing.updateCanvas)
  updateTimer.start
  
  stage.width.onChange(updateCanvasSize)
  stage.height.onChange(updateCanvasSize)
  
  var lastSceneWidth = sceneWidth
  var lastSceneHeight = sceneHeight
  
  def sceneWidth = stage.scene.value.width.value
  def sceneHeight = stage.scene.value.height.value
  
  def updateCanvasSize {
    val deltaSceneWidth = sceneWidth - lastSceneWidth
    val deltaSceneHeight = sceneHeight - lastSceneHeight
    translate(deltaSceneWidth / 2, deltaSceneHeight / 2)
    lastSceneWidth = sceneWidth
    lastSceneHeight = sceneHeight
    canvas.width <== sceneWidth
    canvas.height <== sceneHeight
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