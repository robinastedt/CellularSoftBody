package com.astedt.robin.cellularsoftbody;

import controller.Controller;

object Program {
  
  def main(args : Array[String]) {
    val controller = new Controller();
    val controllerThread = new Thread(controller);
    controllerThread.start();
  }
}
