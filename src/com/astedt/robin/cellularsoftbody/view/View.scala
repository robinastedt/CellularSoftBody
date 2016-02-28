package com.astedt.robin.cellularsoftbody;
package view;


import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class View extends JFXApp {
  println("View initialized");
  
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
      }
    }
  }
}