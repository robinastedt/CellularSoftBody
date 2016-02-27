package com.astedt.robin.cellularsoftbody;

import com.astedt.robin.cellularsoftbody.controller.Controller;

object Program {
  def main(args : Array[String]) {
    val controller = new Controller();
    controller.run();
  }
}
