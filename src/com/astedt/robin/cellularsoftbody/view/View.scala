package com.astedt.robin.cellularsoftbody;
package view;

import javafx.scene.input.MouseEvent
import javafx.event.EventHandler;
import scalafx.scene.canvas.Canvas

import model.State;

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class View(private val model : State, private val observer : Observer) extends JFXApp with Runnable {
  println("View initialized and started!");
  
  def run() {
    //Start up window and graphics
    this.main(Array.empty);
  }
  
  val canvas = new Canvas(200, 200) {
    onMouseClicked = observer.TestRectangleMouseHandler;
  };
  val gc = canvas.graphicsContext2D;
  
  stage = new JFXApp.PrimaryStage {
    title.value = "Hello Stage"
    width = 600
    height = 450
    scene = new Scene {
      fill = Color.LightGreen
      content = canvas
      /*content = new Rectangle {
        x = 25
        y = 40
        width = 100
        height = 100
        fill <== when (hover) choose Color.Green otherwise Color.Red
        onMouseClicked = observer.TestRectangleMouseHandler;
      }*/
      
      
    }
  }
  
  val thread = new Thread(DrawingComponent);
  thread.start();
  
  object DrawingComponent extends Runnable {
    override def run() {
      while (true) {
        gc.clearRect(0, 0, 600, 450)
        gc.fillRect(10, 10, 100 + 100*model.testing, 100)
        
        Thread.sleep(20)
      }
    }
  }
}