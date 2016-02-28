package com.astedt.robin.cellularsoftbody;

import controller.Controller;

import view.View;
import scalafx.application.JFXApp;

object Program {
  
  def main(args : Array[String]) {
    val controller = new Controller();
    controller.run();
  }
}
