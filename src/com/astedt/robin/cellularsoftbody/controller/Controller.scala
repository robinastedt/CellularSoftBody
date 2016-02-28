package com.astedt.robin.cellularsoftbody;
package controller;

import model.State;
import view.View;

class Controller {
  var view = new View;
  var state = new State;
  
  
  def run() {
    println("Controller started...");
    view.main(Array.empty);
  }
}