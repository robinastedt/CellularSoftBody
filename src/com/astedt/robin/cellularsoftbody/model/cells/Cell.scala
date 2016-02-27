package com.astedt.robin.cellularsoftbody.model.cells

import com.astedt.robin.cellularsoftbody.model.cells.phenotypes.Photosynthesis;

class Velocity(var x : Double, var y : Double);

class Position(var x : Double, var y : Double) {
  def incr(vel : Velocity) {
    x += vel.x;
    y += vel.y;
  }
}

object Cell {
  def testCell = new Cell(new Position(0,0)) with Photosynthesis;
  
}

class Cell(private var position : Position) {
  private var velocity = new Velocity(0,0);
  
  def step(dt : Double) {
    position.incr(velocity);
  }
}
