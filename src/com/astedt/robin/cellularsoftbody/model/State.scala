package com.astedt.robin.cellularsoftbody;
package model;

import cells.Cell
import environment.Environment;

class State {
  println("Model initialized");
  
  val environment = new Environment();
  val cells : List[Cell] = List(Cell.testCell, Cell.testCell, Cell.testCell)
  
  var test = 0;
  def testing = test;
}
