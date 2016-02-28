package com.astedt.robin.cellularsoftbody;
package view;

import javafx.scene.input.MouseEvent
import javafx.event.EventHandler;

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
  
  stage = new JFXApp.PrimaryStage {
    title.value = "Hello Stage"
    width = 600
    height = 450
    scene = new Scene {
      fill = Color.LightGreen
      content = new Rectangle {
        x = 25
        y = 40
        width = 100
        height = 100
        fill <== when (hover) choose Color.Green otherwise Color.Red
        onMouseClicked = observer.TestRectangleMouseHandler;
      }
      
    }
  }
  
}